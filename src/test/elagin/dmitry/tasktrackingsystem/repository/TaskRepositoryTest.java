package elagin.dmitry.tasktrackingsystem.repository;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.entities.Task;
import elagin.dmitry.tasktrackingsystem.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TaskRepository repository;
    private Task task;
    private Project project;
    private User user;

    @BeforeEach
    void setUp() {
        project = entityManager.persist(new Project("Сириус"));

        user = entityManager.persist(new User("Юрий", "Белозеров"));

        task = entityManager.persist(new Task("Тема",
                "Описаание",
                "Тип",
                project,
                user));
    }

    @Test
    void findAll() {
        final var tasks = repository.findAll();
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
    }

    @Test
    void findById() {
        var optional = repository.findById(task.getId());
        assertTrue(optional.isPresent());
        assertEquals(task.getId(), optional.get().getId());
    }

    @Test
    void findByResponsibleId() {
        final var tasks = repository.findByResponsibleId(user.getId());
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
        assertEquals(user.getId(), tasks.iterator().next().getResponsible().getId());
    }

    @Test
    void findByProjectId() {
        final var tasks = repository.findByProjectId(project.getId());
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
        assertEquals(project.getId(), tasks.iterator().next().getProject().getId());
    }

    @Test
    void save() {
        repository.findById(task.getId()).ifPresentOrElse(task -> {
            var newTask = repository.save(new Task("Theme",
                    "Deskripiton",
                    "Type",
                    task.getProject(),
                    task.getResponsible()));

            assertNotNull(newTask);
            assertNotEquals(0, newTask.getId());
        }, () -> fail("Задача не найдена!"));
    }

    @Test
    void saveShouldUpdateTask() {
        repository.findById(task.getId())
                .ifPresentOrElse(task -> {
                    task.setTheme("New Theme");
                    repository.save(task);
                }, () -> fail("Задача не найдена!"));

        repository.findById(task.getId())
                .ifPresentOrElse(task -> assertEquals("New Theme", task.getTheme()),
                        () -> fail("Задача не найдена!"));
    }

    @Test
    void deleteById() {
        repository.deleteById(task.getId());
        final var optional = repository.findById(1);
        assertFalse(optional.isPresent());
    }

    @Test
    void deleteByIdShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(0));
    }
}