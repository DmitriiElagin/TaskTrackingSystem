package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.repository.UserRepository;
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