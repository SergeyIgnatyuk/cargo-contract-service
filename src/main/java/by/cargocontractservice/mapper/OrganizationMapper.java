package by.cargocontractservice.mapper;

import by.cargocontractservice.dto.OrganizationDto;
import by.cargocontractservice.entity.Organization;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedSourcePolicy = IGNORE, unmappedTargetPolicy = IGNORE)
public interface OrganizationMapper {

    OrganizationDto toOrganizationDto(Organization organization);

    List<OrganizationDto> toOrganizationDtos(List<Organization> organizations);

    Organization toOrganization(OrganizationDto organizationDto);
}
