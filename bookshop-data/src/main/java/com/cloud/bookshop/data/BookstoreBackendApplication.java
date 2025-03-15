package com.cloud.bookshop.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.cloud.bookshop.data.repository", "com.cloud.bookshop.data.impl"})
@EntityScan(basePackages = "com.cloud.bookshop.data.domain")
@EnableCaching
public class BookstoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreBackendApplication.class, args);
    }

}
