package by.cargocontractservice.client;

import by.cargocontractservice.dto.CreateContractDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "logistic-bp", url = "http://localhost:8072/logistic-bp/logistic/contract")
public interface LogisticBpClient {
    @PostMapping
    void createContract(@RequestBody CreateContractDto createContractDto);
}
