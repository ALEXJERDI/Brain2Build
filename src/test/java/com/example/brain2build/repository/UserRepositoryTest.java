package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Ideator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve a user (Ideator subclass) by email")
    void shouldSaveAndFindUserByEmail() {
        // Arrange
        Ideator ideator = new Ideator();
        ideator.setEmail("test@brain2build.com");
        ideator.setPassword("123456");
        ideator.setNom("Doe");
        ideator.setPrenom("John");
        ideator.setTelephone("0600000000");
        ideator.setBio("Innovator with 5 years experience");

        // Act
        userRepository.save(ideator);
        Optional<?> found = userRepository.findByEmail("test@brain2build.com");

        // Assert
        assertThat(found)
                .as("User with given email should be found")
                .isPresent();

        assertThat(found.get())
                .isInstanceOf(Ideator.class);

        Ideator foundIdeator = (Ideator) found.get();
        assertThat(foundIdeator.getNom()).isEqualTo("Doe");
        assertThat(foundIdeator.getPrenom()).isEqualTo("John");
    }

    @Test
    @DisplayName("Should delete user (Ideator subclass) successfully")
    void shouldDeleteUser() {
        // Arrange
        Ideator ideator = new Ideator();
        ideator.setEmail("delete@brain2build.com");
        ideator.setPassword("123");
        ideator.setNom("ToDelete");
        ideator.setPrenom("User");
        userRepository.save(ideator);
        Long id = ideator.getId();

        // Act
        userRepository.deleteById(id);
        Optional<?> found = userRepository.findById(id);

        // Assert
        assertThat(found)
                .as("User should be deleted and not found by ID")
                .isEmpty();
    }
}
