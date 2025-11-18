package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.domain.dto.User.*;
import org.example.userservice.domain.dto.WorkerProfile.*;
import org.example.userservice.domain.dto.IdeatorProfile.*;
import org.example.userservice.domain.entity.IdeatorProfile;
import org.example.userservice.domain.entity.User;
import org.example.userservice.domain.entity.WorkerProfile;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.mapper.IdeatorMapper;
import org.example.userservice.mapper.WorkerMapper;
import org.example.userservice.repositories.IdeatorProfileRepository;
import org.example.userservice.repositories.UserRepository;
import org.example.userservice.repositories.WorkerProfileRepository;
import org.example.userservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final IdeatorProfileRepository ideatorProfileRepository;
    private final WorkerProfileRepository workerProfileRepository;

    private final UserMapper userMapper;
    private final IdeatorMapper ideatorMapper;
    private final WorkerMapper workerMapper;

    // ============================================================
    //                      USERS
    // ============================================================

    @Override
    public List<UserReadDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toReadDto)
                .toList();
    }

    @Override
    public UserReadDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toReadDto(user);
    }

    @Override
    public UserReadDto updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUserFromDto(dto, user);   // <-- MAPSTRUCT MAGIC
        userRepository.save(user);

        return userMapper.toReadDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // ============================================================
    //                    IDEATOR PROFILE
    // ============================================================

    @Override
    public IdeatorProfileReadDto getIdeatorProfile(Long userId) {
        IdeatorProfile profile = ideatorProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Ideator profile not found"));

        return ideatorMapper.toReadDto(profile);
    }

    @Override
    public IdeatorProfileReadDto updateIdeatorProfile(Long userId, IdeatorProfileUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        IdeatorProfile profile = ideatorProfileRepository.findById(userId)
                .orElse(
                        IdeatorProfile.builder()
                                .user(user)
                                .ideaCount(0)
                                .build()
                );

        ideatorMapper.updateProfile(dto, profile);   // <-- MAPSTRUCT MAGIC
        ideatorProfileRepository.save(profile);

        return ideatorMapper.toReadDto(profile);
    }

    // ============================================================
    //                    WORKER PROFILE
    // ============================================================

    @Override
    public WorkerProfileReadDto getWorkerProfile(Long userId) {
        WorkerProfile profile = workerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Worker profile not found"));

        return workerMapper.toReadDto(profile);
    }

    @Override
    public WorkerProfileReadDto updateWorkerProfile(Long userId, WorkerProfileUpdateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkerProfile profile = workerProfileRepository.findById(userId)
                .orElse(
                        WorkerProfile.builder()
                                .user(user)
                                .build()
                );

        workerMapper.updateProfile(dto, profile);   // <-- MAPSTRUCT MAGIC
        workerProfileRepository.save(profile);

        return workerMapper.toReadDto(profile);
    }

    // ============================================================
    //             APPELS DE AUTHSERVICE (FEIGN)
    // ============================================================

    @Override
    public UserReadDto createUserFromAuth(UserCreateFromAuthDto dto) {

        // 1) Créer le User de base
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setTelephone(dto.getTelephone());
        user.setUserType(dto.getRole().toUpperCase()); // "WORKER" ou "IDEATOR"

        userRepository.save(user);

        // 2) Créer le profil spécialisé selon le rôle
        if ("IDEATOR".equalsIgnoreCase(dto.getRole())) {

            IdeatorProfile profile = IdeatorProfile.builder()
                    .user(user)
                    .bio(dto.getBio())
                    .ideaCount(0)
                    .build();

            ideatorProfileRepository.save(profile);
        }
        else if ("WORKER".equalsIgnoreCase(dto.getRole())) {

            WorkerProfile profile = WorkerProfile.builder()
                    .user(user)
                    .domaine(dto.getDomaine())
                    .specialite(dto.getSpecialite())
                    .experience(dto.getExperience())
                    .portfolioUrl(dto.getPortfolioUrl())
                    .build();

            workerProfileRepository.save(profile);
        }

        // 3) Retourner un DTO normal
        return userMapper.toReadDto(user);
    }

    @Override
    public UserReadDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toReadDto(user);
    }

}
