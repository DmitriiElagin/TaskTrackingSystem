package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {
    @Autowired
    private ProjectService service;

    @Test
    void findAll() {
        Iterable<Project> projects = service.findAll();
        assertNotNull(projects);
        assertTrue(projects.iterator().hasNext());
    }

    @Test
    void findById() {
        Optional<Project> optional = service.findById(1);
        assertTrue(optional.isPresent());
        assertEquals(1, optional.get().getId());
    }

    @Test
    void save() {
        Project project = service.save(new Project("Test"));
        assertNotNull(project);
        assertNotEquals(0, project.getId());
    }

    @Test
    void deleteByID() {
        service.deleteById(1);
        Optional<Project> optional = service.findById(1);
        assertFalse(optional.isPresent());
    }


}