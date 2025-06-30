package com.Edutech.Evaluaciones.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API 2025 Evaluaciones")
                        .version("1.0")
                        .description("Documentacion de la API para el microservicio de Evaluaciones"));
    }
}
