package com.zinkwork.Atm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info().title("Spring Rest API Bootstrap")
                        .description("Bootstrap to Restful API with Spring Boot.")
                        .version("0.0.1"));
    }
}
