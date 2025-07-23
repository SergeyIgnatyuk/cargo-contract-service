package by.cargocontractservice.service;

import by.cargocontractservice.client.LogisticBpClient;
import by.cargocontractservice.client.OrganizationsClient;
import by.cargocontractservice.dto.ContractDto;
import by.cargocontractservice.dto.OrganizationDto;
import by.cargocontractservice.entity.Contract;
import by.cargocontractservice.enums.Status;
import by.cargocontractservice.event.KafkaMessageProducer;
import by.cargocontractservice.mapper.ContractMapper;
import by.cargocontractservice.repository.ContractRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractMapper contractMapper;
    private final ContractRepository contractRepository;
    private final OrganizationsClient organizationsClient;
    private final LogisticBpClient logisticBpClient;
    private final KafkaMessageProducer producer;

    public List<ContractDto> getAllContracts() {
        return contractMapper.toContractDtos(contractRepository.findAll());
    }

    @CircuitBreaker(name = "cargoContractService")
    public ContractDto getContractById(UUID id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The contract has been not found"));
        return contractMapper.toContractDto(contract);
    }

    @CircuitBreaker(name = "logisticBp")
    @Transactional
    public void createContract(ContractDto contractDto) {
        Contract contract = contractMapper.toContract(contractDto);
        String customerName = contractDto.getCustomerName();

        OrganizationDto organizationDto = organizationsClient.getOrganizationByName(customerName);
        if (organizationDto == null) {
            organizationsClient.createOrganization(OrganizationDto.builder()
                    .name(customerName)
                    .build());
        }

        Contract result = contractRepository.save(contract);
        logisticBpClient.createContract(contractMapper.toCreateContractDto(result));
    }

    @Transactional
    public ContractDto updateContractStatus(UUID id, Status status) {
        Contract contract = contractRepository.findById(id).orElseThrow();
        contract.setStatus(status);
        producer.sendObjectMessage(status.name());
        return contractMapper.toContractDto(contractRepository.save(contract));
    }

    public void deleteContractById(UUID id) {
        contractRepository.deleteById(id);
    }
}
