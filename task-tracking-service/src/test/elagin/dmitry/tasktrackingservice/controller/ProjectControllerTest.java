package elagin.dmitry.tasktrackingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elagin.dmitry.tasktrackingservice.dto.ProjectRequest;
import elagin.dmitry.tasktrackingservice.entities.Project;
import elagin.dmitry.tasktrackingservice.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    private static final String URL = "/api/v1/projects/";
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;

    @Test
    void getAllProjects() throws Exception {
        final var project = new Project("Test");
        final var projects = List.of(project);

        given(service.findAll()).willReturn(projects);

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].title", is(project.getTitle())));

    }

    @Test
    void getProject() throws Exception {
        final var project = new Project("Test");

        given(service.findById(1)).willReturn(Optional.of(project));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(project.getTitle())));
    }

    @Test
    void getProjectShouldReturnErrors() throws Exception {
        given(service.findById(1)).willReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL + 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", notNullValue()));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL + 0))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Test
    public void saveProject() throws Exception {
        final var dto = new ProjectRequest("Title");
        final var content = mapper.writer().writeValueAsString(dto);

        given(service.save(ArgumentMatchers.any())).willReturn(dto.toProject());

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));


    }

    @Test
    public void saveProjectShouldReturnErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(new ProjectRequest(-1, null));

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("title")))
                .andExpect(jsonPath("$.message", containsString("id")));
    }

    @Test
    public void updateProject() throws Exception {
        final var dto = new ProjectRequest(1, "Title");
        final var content = mapper.writer().writeValueAsString(dto);

        when(service.findById(anyInt())).thenReturn(Optional.of(dto.toProject()));
        when(service.save(ArgumentMatchers.any())).thenReturn(dto.toProject());

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(dto.getId())))
                .andExpect(jsonPath("$.title", is(dto.getTitle())));

    }

    @Test
    public void updateProjectShouldReturnErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(new ProjectRequest(-1, null));

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("title")))
                .andExpect(jsonPath("$.message", containsString("id")));

        content = mapper.writer().writeValueAsString(new ProjectRequest(1, "Test"));

        when(service.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Test
    public void deleteProject() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteProjectShouldReturnErrors() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 0))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()));

        Mockito.doThrow(EmptyResultDataAccessException.class).when(service).deleteById(anyInt());

        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()));
    }


}