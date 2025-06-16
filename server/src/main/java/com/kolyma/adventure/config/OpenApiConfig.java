package com.kolyma.adventure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI kolymaAdventureOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Kolyma Adventure App API")
                        .description("Документация REST API для сервера настольной игры Колыма")
                        .version("v1.0")
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub репозиторий проекта")
                        .url("https://github.com/yourusername/kolyma-adventure-app"));
    }
}