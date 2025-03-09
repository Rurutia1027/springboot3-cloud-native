package com.cloud.web.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BookshopAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshopAdminApplication.class, args);
    }

}
