package by.cargocontractservice.mapper;

import by.cargocontractservice.dto.ContractDto;
import by.cargocontractservice.dto.CreateContractDto;
import by.cargocontractservice.entity.Contract;
import by.cargocontractservice.entity.Organization;
import by.cargocontractservice.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedSourcePolicy = IGNORE, unmappedTargetPolicy = IGNORE)
public interface ContractMapper {

    @Mapping(target = "customerName", source = "customer", qualifiedByName = "mapCustomerName")
    ContractDto toContractDto(Contract contract);

    List<ContractDto> toContractDtos(List<Contract> contracts);

    Contract toContract(ContractDto contractDto);

    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    CreateContractDto toCreateContractDto(Contract contract);

    @Named("mapStatus")
    default String mapStatus(Status status) {
        return status.name();
    }

    @Named("mapCustomerName")
    default String mapCustomerName(Organization customer) {
        return customer.getName();
    }
}

