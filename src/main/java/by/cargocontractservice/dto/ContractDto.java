package by.cargocontractservice.dto;

import by.cargocontractservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ContractDto {
    private String customerName;
    private String loadingInfo;
    private String deliveryInfo;
    private String cargoType;
    private String cargoWeight;
    private Double price;
    private Status status;
}
