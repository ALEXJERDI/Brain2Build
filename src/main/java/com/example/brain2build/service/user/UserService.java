package com.example.brain2build.service.user;

import com.example.brain2build.domain.dto.User.*;
import java.util.Set;

public interface UserService {



    Set<UserReadDto> getAllUsers();

    UserReadDto updateUser(Long id, UserCreateUpdateDto dto);

    void deleteUser(Long id);
}
