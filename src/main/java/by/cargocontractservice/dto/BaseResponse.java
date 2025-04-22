package by.cargocontractservice.dto;

import lombok.Data;

@Data
public class BaseResponse<T> {
  private String timestamp;
  private Integer code;
  private Boolean success;
  private String message;
  private T payload;
}
