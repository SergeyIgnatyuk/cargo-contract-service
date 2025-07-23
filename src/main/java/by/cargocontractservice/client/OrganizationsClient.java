package by.cargocontractservice.client;

import by.cargocontractservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "logistic-organizations", url = "http://logistic-gatewayserver:8072/logistic-organizations/organizations")
public interface OrganizationsClient {

    @GetMapping("/{name}")
    OrganizationDto getOrganizationByName(@PathVariable String name);

    @PostMapping
    void createOrganization(@RequestBody OrganizationDto organizationDto);
}
