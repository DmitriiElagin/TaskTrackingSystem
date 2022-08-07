package elagin.dmitry.tasktrackingsystem.repository;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProjectRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    private Project project;

    @Autowired
    private ProjectRepository repository;

    @BeforeEach
    public void setUp() {
        project = entityManager.persist(new Project("Самоинкасация юридических лиц"));
    }

    @Test
    void findAll() {
        Iterable<Project> projects = repository.findAll();
        assertNotNull(projects);
        assertTrue(projects.iterator().hasNext());
    }

    @Test
    void findById() {
        Optional<Project> optional = repository.findById(project.getId());
        assertTrue(optional.isPresent());
        assertEquals(project.getId(), optional.get().getId());
    }

    @Test
    void save() {
        Project project = repository.save(new Project("Test"));
        assertNotNull(project);
        assertNotEquals(0, project.getId());
    }

    @Test
    void saveShouldUpdateProject() {
        repository.findById(project.getId())
                .ifPresentOrElse(p -> {
                    p.setTitle("New Title");
                    repository.save(p);
                }, () -> fail("Проект не найден!"));

        repository.findById(project.getId())
                .ifPresentOrElse(p -> assertEquals("New Title", p.getTitle()),
                        () -> fail("Проект не найден!"));
    }

    @Test
    void deleteByID() {
        repository.deleteById(project.getId());
        Optional<Project> optional = repository.findById(project.getId());
        assertFalse(optional.isPresent());
    }

    @Test
    void deleteByIDShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(0));
    }

    @Test
    void existsById() {
        assertTrue(repository.existsById(project.getId()));
        assertFalse(repository.existsById(-1));
    }

}