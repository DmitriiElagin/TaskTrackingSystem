package elagin.dmitry.tasktrackingservice.service;

import elagin.dmitry.tasktrackingservice.entities.Project;
import elagin.dmitry.tasktrackingservice.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private ProjectService service;

    @MockBean
    private ProjectRepository repository;

    @Test
    void findAll() {
        service.findAll();
        Mockito.verify(repository).findAll();
    }

    @Test
    void existsById() {
        service.existsById(1);
        Mockito.verify(repository).existsById(1);
    }

    @Test
    void findById() {
        service.findById(1);
        Mockito.verify(repository).findById(1);
    }

    @Test
    void save() {
        final var project = new Project();
        service.save(project);
        Mockito.verify(repository).save(project);
    }

    @Test
    void deleteByID() {
        service.deleteById(1);
        Mockito.verify(repository).deleteById(1);
    }
}