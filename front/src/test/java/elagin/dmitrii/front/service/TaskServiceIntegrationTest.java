package elagin.dmitrii.front.service;

import elagin.dmitrii.front.dto.TaskRequest;
import elagin.dmitrii.front.dto.TaskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Dmitrii Elagin
 * Date 18.05.2023 9:46
 */
@SpringBootTest
class TaskServiceIntegrationTest {

  @Autowired
  TaskService service;

  @Autowired
  RestTemplate template;

  @Test
  void requestAll() {
    TaskResponse[] tasks = service.requestAll();
    assertNotNull(tasks);
    assertTrue(tasks.length > 0);
  }

  @Test
  void findByProjectId() {
    TaskResponse[] tasks = service.findByProjectId(10);
    assertNotNull(tasks);
    assertTrue(tasks.length > 0);
    tasks = service.findByProjectId(0);
    assertEquals(0, tasks.length);
  }

  @Test
  void findByResponsibleId() {
    TaskResponse[] tasks = service.findByResponsibleId(20);
    assertNotNull(tasks);
    assertTrue(tasks.length > 0);
    tasks = service.findByResponsibleId(0);
    assertEquals(0, tasks.length);
  }

  @Test
  void save() {
    TaskRequest dto = new TaskRequest("Theme", "Desc", "Type", 10, 10);
    TaskResponse saved = service.save(dto);

    assertNotNull(saved);
    assertTrue(saved.getId() > 0);
    assertEquals(dto.getTheme(), saved.getTheme());
  }

  @Test
  public void saveShouldUpdateTask() {
    TaskResponse[] tasks = getTasks();
    TaskResponse response = tasks[0];
    TaskRequest request = new TaskRequest();

    request.setId(response.getId());
    request.setTheme("Updated theme");
    request.setType(response.getType());
    request.setProjectId(response.getProject().getId());
    request.setResponsibleId(response.getResponsible().getId());
    request.setPriority(response.getPriority());
    request.setDescription(response.getDescription());

    TaskResponse result = service.save(request);

    assertNull(result);

    result = template.getForObject(service.getUrl() + "/" + request.getId(), TaskResponse.class);
    assert result != null;
    assertEquals(request.getTheme(), result.getTheme());
  }

  @Test
  public void delete() {

    int id = getTasks()[0].getId();
    service.delete(id);

    assertThrows(HttpClientErrorException.class, () ->
        template.getForObject(service.getUrl() + "/" + id, TaskResponse.class));
  }

  private TaskResponse[] getTasks() {
    TaskResponse[] tasks = template.getForObject(service.getUrl(), TaskResponse[].class);

    if (tasks == null || tasks.length == 0) {
      fail("Сервис вернул пустой массив!");
    }

    return tasks;
  }
}