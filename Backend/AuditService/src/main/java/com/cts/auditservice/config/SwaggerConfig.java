package com.cts.auditservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 * <p>
 * Defines API metadata and enables JWT-based authentication
 * support for testing secured APIs in Swagger UI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI specification.
     *
     * @return the configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
            .info(new Info()
                .title("My Service API")
                .version("1.0")
                .description("API documentation with JWT authentication"))

            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))

            .components(new Components()
                .addSecuritySchemes("BearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
