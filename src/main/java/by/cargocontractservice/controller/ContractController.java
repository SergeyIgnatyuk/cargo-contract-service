package by.cargocontractservice.controller;

import by.cargocontractservice.dto.ContractDto;
import by.cargocontractservice.enums.Status;
import by.cargocontractservice.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contracts")
@Tag(name = "Контроллер для получения информации по контрактам")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    @Operation(summary = "Получить информацию обо всех контрактах")
    public Iterable<ContractDto> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию об контракте по ID")
    public ContractDto getContractByName(@PathVariable UUID id) {
        return contractService.getContractById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новый контракт")
    public ResponseEntity<String> createContract(@RequestBody ContractDto contract) {
        contractService.createContract(contract);
        return new ResponseEntity<>("Contract has been created", HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    @Operation(summary = "Обновить статус контракта")
    public ContractDto updateContractStatus(@PathVariable UUID id, @RequestParam Status status) {
        return contractService.updateContractStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить контракт по ID")
    public void deleteContract(@PathVariable UUID id) {
        contractService.deleteContractById(id);
    }
}
