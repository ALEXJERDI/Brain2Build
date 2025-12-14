package org.example.userservice.repositories;

import org.example.userservice.domain.entity.IdeatorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeatorProfileRepository extends JpaRepository<IdeatorProfile, Long> {
}
