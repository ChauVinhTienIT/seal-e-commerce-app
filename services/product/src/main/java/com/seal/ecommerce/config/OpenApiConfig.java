package com.seal.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Seal E-commerce API",
                version = "1.0",
                description = "E-commerce API documentation"
        ),
        servers = {
                @Server(
                    url = "http://localhost:8050/api/v1",
                    description = "Local ENV"
                )
        }
)
public class OpenApiConfig {

}
