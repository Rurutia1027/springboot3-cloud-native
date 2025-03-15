package com.cloud.bookshop.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cloud.bookshop.web", "com.cloud.bookshop"})
@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "1.0", description = "Spring Boot 3 API Docs"))
public class BookshopAdminApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BookshopAdminApplication.class);
        // application.setAdditionalProfiles("dev");
        application.run(args);
    }

}