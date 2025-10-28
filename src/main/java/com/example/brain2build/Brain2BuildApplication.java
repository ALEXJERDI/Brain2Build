package com.example.brain2build;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.brain2build.domain.entity") // Correct the package name here
@EnableJpaRepositories("com.example.brain2build.repository") // If your repositories are in this package

public class Brain2BuildApplication {
    public static void main(String[] args) {
        SpringApplication.run(Brain2BuildApplication.class, args);
    }
}
