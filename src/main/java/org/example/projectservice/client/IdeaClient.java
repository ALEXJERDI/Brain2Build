package org.example.projectservice.client;

import org.example.projectservice.client.dto.IdeaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "idea-service" = spring.application.name du microservice des idées
@FeignClient(name = "idea-service", path = "/api/ideas")
public interface IdeaClient {

    @GetMapping("/{id}")
    IdeaDto getIdeaById(@PathVariable("id") Long id);
}
