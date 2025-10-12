package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByEmail(String email);
    List<Worker> findByDomaineContainingIgnoreCase(String domaine);
    List<Worker> findBySpecialiteContainingIgnoreCase(String specialite);
}

