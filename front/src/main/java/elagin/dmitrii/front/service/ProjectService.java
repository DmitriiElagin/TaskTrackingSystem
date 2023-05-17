package elagin.dmitrii.front.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import elagin.dmitrii.front.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    public static final String ENDPOINT = "/projects";
    @Value("${service.url}")
    private String serviceURL;

    private final RestTemplate restTemplate;

    public ProjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUrl() {
        return serviceURL + ENDPOINT;
    }

    public ProjectDTO[] findAll() {
        logger.info("GET Запрос всех проектов REST-сервиса по адресу {}", getUrl());
        return restTemplate.getForObject(getUrl(), ProjectDTO[].class);
    }

    public ProjectDTO save(ProjectDTO project) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request;
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", project.getTitle());

        if (project.getId() > 0) {
            jsonObject.put("id", project.getId());

            request = new HttpEntity<>(jsonObject.toString(), headers);

            logger.info("PUT-запрос к REST-сервису по адресу {} для обновления проекта {}", getUrl(), project);
            restTemplate.put(getUrl(), request);

            return null;
        }

        request = new HttpEntity<>(jsonObject.toString(), headers);

        logger.info("POST-запрос к REST-сервису по адресу {} для сохранения проекта {}", getUrl(), project);

        return restTemplate.postForObject(getUrl(), request, ProjectDTO.class);
    }

    public void delete(ProjectDTO project) {
        logger.info("DELETE-запрос к REST-сервису по адресу {} для удаления проекта {}", getUrl(), project);

        restTemplate.delete(getUrl() + "/" + project.getId());
    }


}
