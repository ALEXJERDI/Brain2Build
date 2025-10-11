package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Ideator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeatorRepository extends JpaRepository<Ideator, Long> {
    Optional<Ideator> findByEmail(String email);
    List<Ideator> findByBioContainingIgnoreCase(String keyword);
    List<Ideator> findByIdeaCountGreaterThan(int minCount);
}

