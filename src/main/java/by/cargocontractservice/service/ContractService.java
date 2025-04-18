package by.cargocontractservice.service;

import by.cargocontractservice.model.Contract;
import by.cargocontractservice.model.Status;
import by.cargocontractservice.repository.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractByName(String name) {
        return contractRepository.findByName(name).orElseThrow(() -> new RuntimeException("The contract is not found"));
    }

    public void createContract(Contract contract) {
        contractRepository.save(contract);
    }

    @Transactional
    public Contract updateContractStatus(String name, Status status) {
        Contract contract = contractRepository.findByName(name).orElseThrow();
        contract.setStatus(status);
        return contractRepository.save(contract);
    }

    public void deleteContractByName(String name) {
        contractRepository.deleteByName(name);
    }
}
