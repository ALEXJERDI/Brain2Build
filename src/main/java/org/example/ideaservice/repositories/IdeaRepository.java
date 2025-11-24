package org.example.ideaservice.repositories;

import org.example.ideaservice.entities.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    // Find all ideas with a specific status
    List<Idea> findByStatus(Idea.Status status);

    // Find idea by its title
    Optional<Idea> findByTitre(String titre);
}