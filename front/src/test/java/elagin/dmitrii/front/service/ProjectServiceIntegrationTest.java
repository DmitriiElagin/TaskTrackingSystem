package elagin.dmitrii.front.service;

import elagin.dmitrii.front.dto.ProjectDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceIntegrationTest {
    @Autowired
    ProjectService service;

    @Autowired
    RestTemplate template;

    @Test
    public void findAll() {
        final ProjectDTO[] projects = service.findAll();

        assertNotNull(projects);
        assertTrue(projects.length > 0);

    }

    @Test
    public void save() {
        String title = "New Project";
        ProjectDTO result = service.save(new ProjectDTO(title));

        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals(title, result.getTitle());
    }

    @Test
    public void saveShouldUpdateProject() {
        ProjectDTO[] projects = getProjects();
        projects[0].setTitle("Updated project");

        ProjectDTO result = service.save(projects[0]);

        assertNull(result);

        result = template.getForObject(service.getUrl() + "/" + projects[0].getId(), ProjectDTO.class);

        assertEquals(projects[0], result);
    }

    @Test
    public void delete() {
        ProjectDTO[] projects = getProjects();

        service.delete(projects[0].getId());

        assertThrows(HttpClientErrorException.class, () ->
                template.getForObject(service.getUrl() + "/" + projects[0].getId(), ProjectDTO.class));

    }

    private ProjectDTO[] getProjects() {
        ProjectDTO[] projects = template.getForObject(service.getUrl(), ProjectDTO[].class);

        if (projects == null || projects.length == 0) {
            fail("Сервис вернул пустой массив!");
        }

        return projects;
    }

}