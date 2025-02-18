package com.seal.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Seal E-commerce API",
                version = "1.0",
                description = "E-commerce API documentation"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8030/api/v1",
                        description = "Local ENV"
                )
        }
)
@Configuration
public class OpenApiConfig {
        //                  /api/v1/v3/api-docs
        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI().components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                ).security(List.of(new SecurityRequirement().addList("bearerAuth")));
        }
}
