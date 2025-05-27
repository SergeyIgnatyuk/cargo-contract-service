package by.cargocontractservice.utils;

import lombok.Data;

@Data
public class UserContext {
    public static final String CORRELATION_ID = "X-Correlation-ID";
    private String correlationId;
}
