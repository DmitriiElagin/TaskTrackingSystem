package elagin.dmitrii.front.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import elagin.dmitrii.front.dto.TaskRequest;
import elagin.dmitrii.front.dto.TaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dmitrii Elagin
 * Date 18.05.2023 9:32
 */
@Service
public class TaskService {
  public static String PROJECT_FILTER = "project";
  public static String RESPONSIBLE_FILTER = "responsible";
  private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
  public static final String ENDPOINT = "/tasks";

  @Value("${service.url}")
  private String serviceURL;

  private final RestTemplate restTemplate;

  public TaskService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String getUrl() {
    return serviceURL + ENDPOINT;
  }

  public TaskResponse[] requestAll() {
    logger.info("GET Запрос всех задач REST-сервиса по адресу {}", getUrl());
    return restTemplate.getForObject(getUrl(), TaskResponse[].class);
  }

  public TaskResponse[] findByProjectId(int projectId) {
    logger.info("GET Запрос задач из REST-сервиса по адресу {} где projectId = {}", getUrl(), projectId);
    return find(PROJECT_FILTER, projectId);
  }

  public TaskResponse[] findByResponsibleId(int responsibleId) {
    logger.info("GET Запрос задач из REST-сервиса по адресу {} где responsibleId = {}", getUrl(), responsibleId);
    return find(RESPONSIBLE_FILTER, responsibleId);
  }

  public TaskResponse[] find(String filter, int id) {
    return restTemplate.getForObject(String.format("%s?filter=%s&id=%d", getUrl(), filter, id), TaskResponse[].class);
  }

  public TaskResponse save(TaskRequest task) {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request;
    headers.setContentType(MediaType.APPLICATION_JSON);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("theme", task.getTheme());
    jsonObject.put("type", task.getType());
    jsonObject.put("projectId", task.getProjectId());
    jsonObject.put("responsibleId", task.getResponsibleId());
    jsonObject.put("priority", task.getPriority());
    jsonObject.put("description", task.getDescription());

    if (task.getId() > 0) {
      jsonObject.put("id", task.getId());

      request = new HttpEntity<>(jsonObject.toString(), headers);

      logger.info("PUT-запрос к REST-сервису по адресу {} для обновления задачи {}", getUrl(), task);
      restTemplate.put(getUrl(), request);

      return null;
    }

    request = new HttpEntity<>(jsonObject.toString(), headers);

    logger.info("POST-запрос к REST-сервису по адресу {} для сохранения задачи {}", getUrl(), task);

    return restTemplate.postForObject(getUrl(), request, TaskResponse.class);
  }

  public void delete(int id) {
    logger.info("DELETE-запрос к REST-сервису по адресу {} для удаления задачи c id = {}", getUrl(), id);

    restTemplate.delete(getUrl() + "/" + id);
  }
}
