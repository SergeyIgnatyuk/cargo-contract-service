package by.cargocontractservice.controller;

import by.cargocontractservice.model.Contract;
import by.cargocontractservice.model.Status;
import by.cargocontractservice.repository.ContractRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
@Tag(name = "Контроллер для получения информации по контрактам")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping
    @Operation(summary = "Получить информацию обо всех контрактах")
    public Iterable<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить информацию об контракте по имени")
    public Contract getContractByName(@PathVariable String name) {
        return contractRepository.findByName(name).orElseThrow();
    }

    @PostMapping
    @Operation(summary = "Создать новый контракт")
    public Contract createContract(@RequestBody Contract contract) {
        return contractRepository.save(contract);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "Обновить статус контракта")
    public Contract updateContractStatus(@PathVariable String name, @RequestParam String status) {
        Contract contract = contractRepository.findByName(name).orElseThrow();
        contract.setStatus(Status.valueOf(status));
        return contractRepository.save(contract);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить контракт по имени")
    public void deleteContract(@PathVariable String name) {
        contractRepository.deleteByName(name);
    }
}
