package by.cargocontractservice.interceptor;

import by.cargocontractservice.client.KeycloakTokenClient;
import by.cargocontractservice.utils.UserContext;
import by.cargocontractservice.utils.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContextRequestInterceptor implements RequestInterceptor {

    private final KeycloakTokenClient tokenClient;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = tokenClient.getAccessToken();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        requestTemplate.header(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
    }
}
