package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.domain.entity.Worker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@Rollback(false) // keep data after test
class UserRepositoryBulkTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve multiple Ideators and Workers")
    void shouldSaveMultipleUsers() {
        // 1️⃣ Create 5 Ideators
        List<Ideator> ideators = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> {
                    Ideator ideator = new Ideator();
                    ideator.setEmail("ideator" + i + "@test.com");
                    ideator.setPassword("pass" + i);
                    ideator.setNom("Ideator" + i);
                    ideator.setPrenom("Test" + i);
                    ideator.setTelephone("060000000" + i);
                    ideator.setBio("Bio for ideator " + i);
                    ideator.setIdeaCount(i);
                    return ideator;
                })
                .toList();

        // 2️⃣ Create 5 Workers
        List<Worker> workers = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> {
                    Worker worker = new Worker();
                    worker.setEmail("worker" + i + "@test.com");
                    worker.setPassword("pwd" + i);
                    worker.setNom("Worker" + i);
                    worker.setPrenom("Test" + i);
                    worker.setTelephone("070000000" + i);
                    worker.setDomaine("IT");
                    worker.setSpecialite("Backend");
                    worker.setExperience(3);
                    worker.setPortfolioUrl("https://portfolio" + i + ".com");
                    return worker;
                })
                .toList();

        // 3️⃣ Save them all
        userRepository.saveAll(ideators);
        userRepository.saveAll(workers);

        // 4️⃣ Retrieve all users
        var allUsers = userRepository.findAll();

        // 5️⃣ Assertions
        assertThat(allUsers)
                .as("We should have 10 total users")
                .hasSize(10);

        long ideatorCount = allUsers.stream().filter(u -> u instanceof Ideator).count();
        long workerCount = allUsers.stream().filter(u -> u instanceof Worker).count();

        assertThat(ideatorCount).isEqualTo(5);
        assertThat(workerCount).isEqualTo(5);
    }
}
