package com.biblioteca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API REST para gerenciamento de biblioteca", version = "v1", description = "API para trabalho de faculdade do grupo Bravo."))
public class OpenApiConfiguration {

    //Para acessar a doc: http://localhost:8080/swagger-ui/index.html#/

    @Bean
    public OpenAPI customerOpenAPI() {
        return new OpenAPI().components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Biblioteca Service  API")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
