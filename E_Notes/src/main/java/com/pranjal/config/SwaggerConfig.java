package com.pranjal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Set API info
        OpenAPI openAPI = new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Enotes API")
                        .version("1.0")
                        .description("API documentation for the Enotes application")
                        .termsOfService("http://enotes.com/terms")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@enotes.com")
                                .url("http://enotes.com/support"))
                        .license(new License()
                                .name("Enotes License 1.0")
                                .url("https://github.com/enotes")));

        // Set up servers
        List<Server> serverList = List.of(
                new Server().description("Development Server").url("http://localhost:8080"),
                new Server().description("Testing Server").url("http://localhost:8081"),
                new Server().description("Production Server").url("https://localhost:8082")
        );
        openAPI.servers(serverList);

        // ✅ Ensure Components are initialized
        if (openAPI.getComponents() == null) {
            openAPI.setComponents(new Components());
        }

        // Define Security Scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Add security scheme to OpenAPI
        openAPI.getComponents().addSecuritySchemes("BearerAuth", securityScheme);

        // Apply global security requirement
        openAPI.addSecurityItem(new SecurityRequirement().addList("BearerAuth"));

        return openAPI;
    }
}