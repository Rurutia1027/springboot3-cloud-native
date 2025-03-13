package com.cloud.bookshop.web.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cloud.bookshop")
@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "1.0", description = "Spring Boot 3 API Docs"))
public class BookshopAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopAdminApplication.class, args);
    }

}
