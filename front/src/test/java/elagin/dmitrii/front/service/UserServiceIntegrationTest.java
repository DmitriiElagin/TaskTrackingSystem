package elagin.dmitrii.front.service;

import elagin.dmitrii.front.dto.UserDTO;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    UserService service;

    @Autowired
    RestTemplate template;

    @Autowired
    UserRepository repository;


    @Test
    void requestALL() {
        UserDTO[] userDTOS = service.requestALL();

        assertNotNull(userDTOS);
        assertTrue(userDTOS.length > 0);

    }

    @Test
    void loadUserByUsername() {
        User user = getUser();
        UserDetails result = service.loadUserByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void loadUserByUsernameShouldThrowException() {
        assertThrows(Exception.class, () -> service.loadUserByUsername("qwerty"));
    }

    @Rollback
    @Test
    void save() {
        String username = "JohnShow";
        String password = "Pa$$word123";
        User newUser = new User(0, username, password, User.UserRole.ROLE_USER,
                "test", "John", "Snow");

        UserDTO result = service.save(newUser);

        Optional<User> optionalUser = repository.findByUsername(username);

        assertNotNull(result);
        assertTrue(result.getId() > 0);

        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();

        assertTrue(user.getId() > 0);
        assertNotEquals(password, user.getPassword());
    }

    @Rollback
    @Test
    void saveShouldUpdateUser() {
        User user = getUser();
        String name = "Игорь";
        user.setFirstName(name);

        UserDTO result = service.save(user);

        assertEquals(name, result.getFirstName());


        Optional<User> optUser = repository.findByUsername(user.getUsername());
        if (optUser.isPresent()) {
            assertEquals(name, optUser.get().getFirstName());
        } else {
            fail("Пользователь не найден!");
        }
    }

    @Rollback
    @Test
    void delete() {
        User user = getUser();
        service.delete(user);

        assertThrows(HttpClientErrorException.class, () ->
                template.getForObject(service.getUrl() + "/" + user.getId(), User.class));
        assertFalse(repository.existsByUsername(user.getUsername()));
    }

    User getUser() {
        System.out.println(repository.findAll());
        User user;
        UserDTO[] users = template.getForObject(service.getUrl(), UserDTO[].class);
        if (users == null || users.length == 0) {
            fail("Сервис вернул пустой список пользователей!");
        }

        Optional<User> optionalUser = repository.findById(users[0].getId());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User(users[0].getId(), users[0].getFirstName(), "123456",
                    User.UserRole.ROLE_USER, "test", users[0].getFirstName(), users[0].getLastName());
            repository.save(user);
        }
        return user;
    }
}