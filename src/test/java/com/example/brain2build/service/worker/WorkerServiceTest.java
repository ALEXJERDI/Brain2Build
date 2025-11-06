package com.example.brain2build.service.Worker;

import com.example.brain2build.domain.dto.Worker.WorkerCreateUpdateDto;
import com.example.brain2build.domain.dto.Worker.WorkerReadDto;
import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.repository.WorkerRepository;
import com.example.brain2build.service.worker.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class WorkerServiceTest {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerRepository workerRepository;

    @BeforeEach
    void setup() {
        workerRepository.deleteAll();
    }



    @Test
    void testUpdateWorker() {
        // GIVEN
        Worker worker = new Worker();
        worker.setEmail("jane@example.com");
        worker.setPassword("oldpass");
        worker.setNom("Jane");
        worker.setPrenom("Doe");
        worker.setTelephone("+33600000000");
        worker.setDomaine("Design");
        worker.setSpecialite("UI/UX");
        worker.setExperience(4);
        worker.setPortfolioUrl("https://portfolio.com/jane");
        workerRepository.save(worker);

        WorkerCreateUpdateDto dto = new WorkerCreateUpdateDto(
                "jane@example.com",
                "newpass123",
                "Jane Updated",
                "Doe",
                "+33699999999",
                "Design",
                "Senior UI/UX",
                5,
                "https://portfolio.com/jane-updated"
        );

        // WHEN
        WorkerReadDto result = workerService.updateWorker(worker.getId(), dto);

        // THEN
        assertThat(result.getNom()).isEqualTo("Jane Updated");
        assertThat(result.getSpecialite()).isEqualTo("Senior UI/UX");
        assertThat(result.getExperience()).isEqualTo(5);
    }

    @Test
    void testGetAllWorkers() {
        // GIVEN
        Worker w1 = new Worker();
        w1.setEmail("a@a.com");
        w1.setPassword("pass");
        w1.setNom("A");
        w1.setPrenom("Alpha");
        w1.setTelephone("+33611111111");
        w1.setDomaine("IT");
        w1.setSpecialite("Dev");
        w1.setExperience(2);
        workerRepository.save(w1);

        Worker w2 = new Worker();
        w2.setEmail("b@b.com");
        w2.setPassword("pass");
        w2.setNom("B");
        w2.setPrenom("Beta");
        w2.setTelephone("+33622222222");
        w2.setDomaine("Design");
        w2.setSpecialite("UI");
        w2.setExperience(1);
        workerRepository.save(w2);

        // WHEN
        var result = workerService.getAllWorkers();

        // THEN
        assertThat(result).hasSize(2);
    }
}