package org.example.authservice.feight;

import org.example.authservice.feight.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// ⚠️ name = spring.application.name du microservice userservice
@FeignClient(name = "userservice")
public interface UserClient {

    // Cette route doit exister dans userservice
    @GetMapping("/internal/users/email/{email}")
    UserResponse getUserByEmail(@PathVariable("email") String email);

    @PostMapping("/internal/users")
    UserResponse createUserProfile(@RequestBody CreateUserProfileRequest request);
}
