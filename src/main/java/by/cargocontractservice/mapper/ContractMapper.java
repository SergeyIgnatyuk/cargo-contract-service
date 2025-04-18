package by.cargocontractservice.mapper;

import by.cargocontractservice.dto.ContractDto;
import by.cargocontractservice.entity.Contract;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractDto toContractDto(Contract contract);

    List<ContractDto> toContractDtos(List<Contract> contracts);

    Contract toContract(ContractDto contractDto);
}
