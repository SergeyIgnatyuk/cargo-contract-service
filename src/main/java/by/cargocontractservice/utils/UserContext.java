package by.cargocontractservice.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserContext {
    public static final String CORRELATION_ID = "X-Correlation-ID";
    private String correlationId;

    public void setCorrelationId(String correlationId) {
        if (correlationId != null) {
            this.correlationId = correlationId;
        } else {
            this.correlationId = UUID.randomUUID().toString();
        }
    }
}
