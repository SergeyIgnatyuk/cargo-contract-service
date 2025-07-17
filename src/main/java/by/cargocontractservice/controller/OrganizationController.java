package by.cargocontractservice.controller;

import by.cargocontractservice.dto.OrganizationDto;
import by.cargocontractservice.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@Tag(name = "Контроллер для получения информации по организациям")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    @Operation(summary = "Получить информацию обо всех организациях")
    public Iterable<OrganizationDto> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию об организации по ID")
    public OrganizationDto getOrganizationById(@PathVariable UUID id) {
        return organizationService.getOrganizationById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новую организацию")
    public ResponseEntity<String> createContract(@RequestBody OrganizationDto organization) {
        organizationService.createOrganization(organization);
        return new ResponseEntity<>("Organization has been created", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить организацию по ID")
    public void deleteContract(@PathVariable UUID id) {
        organizationService.deleteOrganizationById(id);
    }
}
