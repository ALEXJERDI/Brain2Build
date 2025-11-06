package com.example.brain2build.service.user;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.dto.User.*;
import com.example.brain2build.domain.entity.User;
import com.example.brain2build.repository.UserRepository;
import jakarta.transaction.Transactional;
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
                                .map(role -> new RoleDto(role.getId(), role.getNom()))  // Correctly using RoleDto constructor
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public UserReadDto updateUser(Long id, UserCreateUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getNom() != null) user.setNom(dto.getNom());
        if (dto.getPrenom() != null) user.setPrenom(dto.getPrenom());
        if (dto.getTelephone() != null) user.setTelephone(dto.getTelephone());

        userRepository.save(user); // ensure persistence

        return new UserReadDto(
                user.getId(),
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                user.getTelephone(),
                user.getRoles().stream()
                        .map(role -> new RoleDto(role.getId(), role.getNom()))  // Correctly using RoleDto constructor
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
