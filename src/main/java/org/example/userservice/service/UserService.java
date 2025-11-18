package org.example.userservice.service;

import org.example.userservice.domain.dto.User.*;
import org.example.userservice.domain.dto.WorkerProfile.*;
import org.example.userservice.domain.dto.IdeatorProfile.*;

import java.util.List;

public interface UserService {

    // EXISTANT
    List<UserReadDto> getAllUsers();
    UserReadDto getUserById(Long id);
    UserReadDto updateUser(Long id, UserUpdateDto dto);
    void deleteUser(Long id);

    IdeatorProfileReadDto getIdeatorProfile(Long userId);
    IdeatorProfileReadDto updateIdeatorProfile(Long userId, IdeatorProfileUpdateDto dto);

    WorkerProfileReadDto getWorkerProfile(Long userId);
    WorkerProfileReadDto updateWorkerProfile(Long userId, WorkerProfileUpdateDto dto);

    // 🔵 NOUVEAU : utilisé par authservice via Feign
    UserReadDto createUserFromAuth(UserCreateFromAuthDto dto);
    UserReadDto getUserByEmail(String email);
}
