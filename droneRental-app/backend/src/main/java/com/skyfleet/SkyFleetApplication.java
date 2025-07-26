package com.skyfleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkyFleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyFleetApplication.class, args);
    }
} 