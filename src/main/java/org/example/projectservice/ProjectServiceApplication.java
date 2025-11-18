package org.example.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.example.projectservice.client")
public class ProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }
}
