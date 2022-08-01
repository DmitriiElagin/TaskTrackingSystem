package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Test
    void findAll() {
        final var tasks = service.findAll();
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
    }

    @Test
    void findById() {
        var optional = service.findById(1);
        assertTrue(optional.isPresent());
        assertEquals(1, optional.get().getId());
    }

    @Test
    void findByResponsibleId() {
        final var tasks = service.findByResponsibleId(1);
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
        assertEquals(1, tasks.iterator().next().getResponsible().getId());
    }

    @Test
    void findByProjectId() {
        final var tasks = service.findByProjectId(1);
        assertNotNull(tasks);
        assertTrue(tasks.iterator().hasNext());
        assertEquals(1, tasks.iterator().next().getProject().getId());
    }

    @Test
    void save() {
        service.findById(1).ifPresentOrElse(task -> {
            var newTask = service.save(new Task("Theme",
                    "Deskripiton",
                    "Type",
                    task.getProject(),
                    task.getResponsible()));

            assertNotNull(newTask);
            assertNotEquals(0, newTask.getId());
        }, () -> fail("Задача не найдена!"));
    }

    @Test
    void deleteById() {
        service.deleteById(1);
        final var optional = service.findById(1);
        assertFalse(optional.isPresent());
    }
}