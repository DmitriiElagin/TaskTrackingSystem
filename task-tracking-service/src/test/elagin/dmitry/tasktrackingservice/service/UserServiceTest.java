package elagin.dmitry.tasktrackingservice.service;

import elagin.dmitry.tasktrackingservice.entities.User;
import elagin.dmitry.tasktrackingservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

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
        final var user = new User();
        service.save(user);
        Mockito.verify(repository).save(user);
    }

    @Test
    void deleteByID() {
        service.deleteById(1);
        Mockito.verify(repository).deleteById(1);
    }
}