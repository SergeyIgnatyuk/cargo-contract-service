package by.cargocontractservice.client;

import by.logisticspec.api.OrganizationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "logistic-organizations", url = "http://logistic-gatewayserver:8072/logistic-organizations/organizations")
public interface OrganizationsClient extends OrganizationApi {
}
