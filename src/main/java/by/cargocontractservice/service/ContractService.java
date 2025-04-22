package by.cargocontractservice.service;

import by.cargocontractservice.client.LogisticBpClient;
import by.cargocontractservice.dto.ContractDto;
import by.cargocontractservice.entity.Contract;
import by.cargocontractservice.enums.Status;
import by.cargocontractservice.mapper.ContractMapper;
import by.cargocontractservice.repository.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;
    private final LogisticBpClient logisticBpClient;

    public List<ContractDto> getAllContracts() {
        return contractMapper.toContractDtos(contractRepository.findAll());
    }

    public ContractDto getContractById(UUID id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The contract has been not found"));
        return contractMapper.toContractDto(contract);
    }

    public void createContract(ContractDto contractDto) {
        Contract contract = contractRepository.save(contractMapper.toContract(contractDto));
        logisticBpClient.createContract(contractMapper.toCreateContractDto(contract));
    }

    @Transactional
    public ContractDto updateContractStatus(UUID id, Status status) {
        Contract contract = contractRepository.findById(id).orElseThrow();
        contract.setStatus(status);
        return contractMapper.toContractDto(contractRepository.save(contract));
    }

    public void deleteContractById(UUID id) {
        contractRepository.deleteById(id);
    }
}
