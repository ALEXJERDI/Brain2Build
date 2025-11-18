package org.example.userservice.repositories;

import org.example.userservice.domain.entity.WorkerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerProfileRepository extends JpaRepository<WorkerProfile, Long> {
}
