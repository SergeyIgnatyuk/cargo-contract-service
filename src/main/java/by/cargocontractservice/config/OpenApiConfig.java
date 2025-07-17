package by.cargocontractservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Scopes scopes = new Scopes();
        scopes.put("openid", "openid scope");
        scopes.put("profile", "profile scope");
        scopes.put("email", "email scope");
        return new OpenAPI()
                .info(new Info().title("Cargo Contract Service").version("1.0").description("API Documentation"))
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://localhost:8080/realms/logistic-realm/protocol/openid-connect/auth")
                                                .tokenUrl("http://localhost:8080/realms/logistic-realm/protocol/openid-connect/token")
                                                .scopes(scopes)
                                        )
                                )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
    }
}
