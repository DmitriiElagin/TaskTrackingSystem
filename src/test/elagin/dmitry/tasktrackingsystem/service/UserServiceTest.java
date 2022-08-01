package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void findAll() {
        final var users = service.findAll();
        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void findById() {
        final var optional = service.findById(1);
        assertTrue(optional.isPresent());
        assertEquals(1, optional.get().getId());
    }

    @Test
    public void save() {
        final var user = service.save(new User("John", "Snow"));
        assertNotNull(user);
        assertNotEquals(0, user.getId());
    }

    @Test
    public void deleteById() {
        service.deleteById(1);
        final var optional = service.findById(1);
        assertFalse(optional.isPresent());
    }
}