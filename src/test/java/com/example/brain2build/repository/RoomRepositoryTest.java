package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // 👉 pour utiliser ton application-test.properties
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;
    private Room room;

    @BeforeEach
    void setUp() {
        // --- Création du projet ---
        project = new Project();
        project.setProjectName("AI Design Tool");
        projectRepository.save(project);

        // --- Création de la salle (Room) liée au projet ---
        room = new Room();
        room.setRoomName("AI Brainstorm Room");
        room.setMaxMembers(10);
        room.setProject(project);

        roomRepository.save(room);
    }

    @Test
    @DisplayName("✅ findByProject_Id() doit retourner la salle associée au projet")
    void findByProjectId_shouldReturnRoom() {
        Optional<Room> found = roomRepository.findByProject_Id(project.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getRoomName()).isEqualTo("AI Brainstorm Room");
        assertThat(found.get().getProject().getProjectName()).isEqualTo("AI Design Tool");
    }

    @Test
    @DisplayName("❌ findByProject_Id() doit retourner vide si aucun projet n'existe")
    void findByProjectId_shouldReturnEmptyWhenNoRoom() {
        Optional<Room> found = roomRepository.findByProject_Id(999L);
        assertThat(found).isEmpty();
    }
}