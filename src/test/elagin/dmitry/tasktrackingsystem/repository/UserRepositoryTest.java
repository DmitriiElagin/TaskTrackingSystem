package elagin.dmitry.tasktrackingsystem.repository;

import elagin.dmitry.tasktrackingsystem.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;
    private User user;

    @BeforeEach
    void setUp() {
        user = entityManager.persist(new User("Дмитрий", "Елагин"));
    }

    @Test
    public void findAll() {
        final var users = repository.findAll();
        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void findById() {
        final var optional = repository.findById(user.getId());
        assertTrue(optional.isPresent());
        assertEquals(user.getId(), optional.get().getId());
    }

    @Test
    public void save() {
        final var user = repository.save(new User("John", "Snow"));
        assertNotNull(user);
        assertNotEquals(0, user.getId());
    }

    @Test
    void saveShouldUpdateUser() {
        repository.findById(user.getId())
                .ifPresentOrElse(user -> {
                    user.setFirstName("Иван");
                    repository.save(user);
                }, () -> fail("Пользователь не найден!"));

        repository.findById(user.getId())
                .ifPresentOrElse(user -> assertEquals("Иван", user.getFirstName()),
                        () -> fail("Пользователь не найден!"));
    }

    @Test
    public void deleteById() {
        repository.deleteById(user.getId());
        final var optional = repository.findById(1);
        assertFalse(optional.isPresent());
    }

    @Test
    void deleteByIdShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(0));
    }
}