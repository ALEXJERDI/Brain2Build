package org.example.ideaservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// IMPORTANT:
// name = spring.application.name de user-service (dans son application.yml)
@FeignClient(
        name = "userservice",   // ou "USER-SERVICE" selon ton config Eureka
        path = "/users"          // prefix commun de ton UserController
)
public interface UserClient {

    @GetMapping("/{id}")
    UserReadDto getUserById(@PathVariable("id") Long id);
}

