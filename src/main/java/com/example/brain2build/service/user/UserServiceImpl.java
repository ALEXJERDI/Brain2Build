package com.example.brain2build.service.user;

import com.example.brain2build.domain.dto.User.*;
import com.example.brain2build.domain.entity.User;
import com.example.brain2build.mappers.UserMapper;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserReadDto createUser(UserCreateUpdateDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow()));
        return userMapper.toReadDto(userRepository.save(user));
    }

    @Override
    public UserReadDto updateUser(Long id, UserCreateUpdateDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.partialUpdate(dto, existingUser);
        return userMapper.toReadDto(userRepository.save(existingUser));
    }

    @Override
    public Set<UserReadDto> getAllUsers() {
        return userMapper.toReadDtoSet(
                userRepository.findAll().stream().collect(Collectors.toSet())
        );
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
