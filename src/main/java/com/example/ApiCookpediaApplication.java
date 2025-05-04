package com.example;

import com.example.util.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ApiCookpediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCookpediaApplication.class, args);
    }
}
