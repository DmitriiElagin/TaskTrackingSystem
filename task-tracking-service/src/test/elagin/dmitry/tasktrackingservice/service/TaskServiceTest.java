package elagin.dmitry.tasktrackingservice.service;

import elagin.dmitry.tasktrackingservice.entities.Task;
import elagin.dmitry.tasktrackingservice.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService service;

    @MockBean
    private TaskRepository repository;

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
    void findByResponsibleId() {
        service.findByResponsibleId(1);
        Mockito.verify(repository).findByResponsibleId(1);
    }

    @Test
    void findByProjectId() {
        service.findByProjectId(1);
        Mockito.verify(repository).findByProjectId(1);
    }

    @Test
    void save() {
        final var task = new Task();
        service.save(task);
        Mockito.verify(repository).save(task);
    }

    @Test
    void deleteById() {
        service.deleteById(1);
        Mockito.verify(repository).deleteById(1);
    }
}