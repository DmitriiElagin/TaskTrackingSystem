package elagin.dmitrii.front.service;

import com.nimbusds.jose.shaded.json.JSONObject;
import elagin.dmitrii.front.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {
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

            restTemplate.put(getUrl(), request);

            return null;
        }

        request = new HttpEntity<>(jsonObject.toString(), headers);

        return restTemplate.postForObject(getUrl(), request, ProjectDTO.class);
    }

    public void delete(ProjectDTO project) {
        restTemplate.delete(getUrl() + "/" + project.getId());
    }


}
