package org.example.roomservice.client;



import org.example.roomservice.client.UserReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(
        name = "userservice",
        path = "/users"
)
public interface UserClient {

    @GetMapping("/{id}")
    UserReadDto getUserById(@PathVariable("id") Long id);
}

