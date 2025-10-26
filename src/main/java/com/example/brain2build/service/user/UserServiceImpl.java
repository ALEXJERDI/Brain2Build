package com.example.brain2build.service.user;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.dto.User.*;
import com.example.brain2build.domain.entity.User;
import com.example.brain2build.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Set<UserReadDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserReadDto(
                        user.getId(),
                        user.getEmail(),
                        user.getNom(),
                        user.getPrenom(),
                        user.getTelephone(),
                        user.getRoles().stream()
                                .map(role -> new RoleDto(role.getId(), role.getNom()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toSet());
    }

    @Override
    public UserReadDto updateUser(Long id, UserCreateUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(dto.getEmail());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setTelephone(dto.getTelephone());

        return new UserReadDto(
                user.getId(),
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                user.getTelephone(),
                user.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getNom()))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
