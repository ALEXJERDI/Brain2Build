package com.example.brain2build.service.Auth.Worker;

import com.example.brain2build.config.JwtProvider;
import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.WorkerRegisterRequest;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkerAuthServiceImpl implements WorkerAuthService {
    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerWorker(WorkerRegisterRequest request) {
        Worker worker = new Worker();
        worker.setEmail(request.getEmail());
        worker.setPassword(passwordEncoder.encode(request.getPassword()));
        worker.setNom(request.getNom());
        worker.setPrenom(request.getPrenom());
        worker.setTelephone(request.getTelephone());
        worker.setDomaine(request.getDomaine());
        worker.setSpecialite(request.getSpecialite());
        worker.setExperience(request.getExperience());
        worker.setPortfolioUrl(request.getPortfolioUrl());
        worker.setRoles(Set.of(roleRepository.findByNom("ROLE_WORKER").orElseThrow()));

        workerRepository.save(worker);

        String token = jwtProvider.generateToken(worker);
        return new AuthResponse(token);
    }
}

