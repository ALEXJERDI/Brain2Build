package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Update this line to use 'nom' instead of 'name'
    Optional<Role> findByNom(String nom);

    boolean existsByNom(String nom);  // This is correct, as you're checking for 'nom'
}
