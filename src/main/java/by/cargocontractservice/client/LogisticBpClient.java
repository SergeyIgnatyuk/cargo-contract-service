package by.cargocontractservice.client;

import by.cargocontractservice.dto.CreateContractDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LogisticBpClient {
    @PostMapping
    void createContract(@RequestBody CreateContractDto createContractDto);
}
