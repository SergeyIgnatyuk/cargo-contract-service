package by.cargocontractservice.controller;

import by.cargocontractservice.model.Contract;
import by.cargocontractservice.model.Status;
import by.cargocontractservice.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
@Tag(name = "Контроллер для получения информации по контрактам")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    @Operation(summary = "Получить информацию обо всех контрактах")
    public Iterable<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить информацию об контракте по названию")
    public Contract getContractByName(@PathVariable String name) {
        return contractService.getContractByName(name);
    }

    @PostMapping
    @Operation(summary = "Создать новый контракт")
    public ResponseEntity<String> createContract(@RequestBody Contract contract) {
        contractService.createContract(contract);
        return new ResponseEntity<>("Contract has been created", HttpStatus.CREATED);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "Обновить статус контракта")
    public Contract updateContractStatus(@PathVariable String name, @RequestParam Status status) {
        return contractService.updateContractStatus(name, status);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить контракт по названию")
    public void deleteContract(@PathVariable String name) {
        contractService.deleteContractByName(name);
    }
}
