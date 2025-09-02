package com.company.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gerenciamento de Projetos e Tarefas")
                .description("Desafio técnico Java Backend - API para projetos e tarefas, com validação, tratamento de erros e testes.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Marcos Bontempo dos Santos")
                    .email("marcos.bontempo@gmail.com")
                )
            );
    }
}
