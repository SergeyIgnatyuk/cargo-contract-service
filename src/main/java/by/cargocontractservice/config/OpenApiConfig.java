package by.cargocontractservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String TOKEN_URL;

    @Value("${spring.security.oauth2.client.provider.keycloak.authorization-uri")
    private String AUTH_URL;

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
                                                .authorizationUrl(AUTH_URL)
                                                .tokenUrl(TOKEN_URL)
                                                .scopes(scopes)
                                        )
                                )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("oauth2"));
    }
}
