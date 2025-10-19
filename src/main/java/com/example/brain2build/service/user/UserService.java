package com.example.brain2build.service.user;

import com.example.brain2build.domain.dto.User.*;
import java.util.Set;

public interface UserService {

    UserReadDto createUser(UserCreateUpdateDto dto);

    UserReadDto updateUser(Long id, UserCreateUpdateDto dto);

    Set<UserReadDto> getAllUsers();

    void deleteUser(Long id);
}
