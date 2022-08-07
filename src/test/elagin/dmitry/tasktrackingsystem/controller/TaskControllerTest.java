package elagin.dmitry.tasktrackingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elagin.dmitry.tasktrackingsystem.dto.TaskRequest;
import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.entities.Task;
import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.service.ProjectService;
import elagin.dmitry.tasktrackingsystem.service.TaskService;
import elagin.dmitry.tasktrackingsystem.service.UserService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TaskController.class)
class TaskControllerTest {
    private static final String URL = "/api/v1/tasks/";
    private static final String FILTER_PARAMETER = "filter";
    private static final String ID_PARAMETER = "id";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private UserService userService;

    @MockBean
    private TaskService taskService;

    @Test
    void findShouldInvokeFindAll() throws Exception {
        final var task = new Task();
        final var tasks = List.of(task);

        given(taskService.findAll()).willReturn(tasks);

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        verify(taskService).findAll();
    }

    @Test
    void findShouldInvokeFindByProjectId() throws Exception {
        final var task = new Task();
        final var tasks = List.of(task);

        given(taskService.findByProjectId(anyInt())).willReturn(tasks);

        mockMvc
                .perform(MockMvcRequestBuilders.get(createURL(TaskController.SearchFilter.PROJECT.name(), 3)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        verify(taskService).findByProjectId(3);
    }

    @Test
    void findShouldInvokeFindByResponsibleId() throws Exception {
        final var task = new Task();
        final var tasks = List.of(task);

        given(taskService.findByResponsibleId(anyInt())).willReturn(tasks);

        mockMvc
                .perform(MockMvcRequestBuilders.get(createURL(TaskController.SearchFilter.RESPONSIBLE.name(), 7)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        verify(taskService).findByResponsibleId(7);
    }

    @Test
    void findShouldReturnError() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(createURL("error", 1)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void getTask() throws Exception {
        final var task = new Task("Theme", "Description", "", null, null);

        given(taskService.findById(1)).willReturn(Optional.of(task));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.theme", is(task.getTheme())))
                .andExpect(jsonPath("$.description", is(task.getDescription())));
    }


    @Test
    void getTaskShouldReturnErrors() throws Exception {
        given(taskService.findById(ArgumentMatchers.anyInt())).willReturn(Optional.empty());

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
    public void saveTask() throws Exception {
        final var request = new TaskRequest("Theme", "Description", "Type", 1, 1);

        final var content = mapper.writer().writeValueAsString(request);

        when(projectService.findById(request.getProjectId())).thenReturn(Optional.of(new Project()));
        when(userService.findById(request.getResponsibleId())).thenReturn(Optional.of(new User()));

        when(taskService.save(ArgumentMatchers.any())).thenReturn(request.toTask());

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void saveTaskShouldReturnNotFound() throws Exception {
        final var request = new TaskRequest("Theme", "Description", "Type", 1, 1);

        final var content = mapper.writer().writeValueAsString(request);

        when(projectService.findById(request.getProjectId())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("Проект")));


        when(projectService.findById(request.getProjectId())).thenReturn(Optional.of(new Project()));
        when(userService.findById(request.getResponsibleId())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("Пользователь")));
    }

    @Test
    public void saveTaskShouldReturnValidationErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(
                new TaskRequest(-1, "", "",
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        0, 0, null));

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("7 errors")));
    }

    @Test
    public void updateTask() throws Exception {
        final var request = new TaskRequest("Theme", "Description", "Type", 1, 1);

        final var content = mapper.writer().writeValueAsString(request);

        when(taskService.findById(anyInt())).thenReturn(Optional.of(request.toTask()));
        when(taskService.save(ArgumentMatchers.any())).thenReturn(request.toTask());

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

    }

    @Test
    public void updateTaskShouldReturnNotFound() throws Exception {
        final var request = new TaskRequest("Theme", "Description", "Type", 1, 1);

        final var content = mapper.writer().writeValueAsString(request);

        when(taskService.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()));
    }

    @Test
    public void updateTaskShouldReturnValidationErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(
                new TaskRequest(-1, "", "",
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                        0, 0, null));

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("7 errors")));
    }

    @Test
    public void deleteTask() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteTaskShouldReturnErrors() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 0))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()));

        Mockito.doThrow(EmptyResultDataAccessException.class).when(taskService).deleteById(anyInt());

        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", notNullValue()));
    }

    private String createURL(String filter, int id) {
        return String.format("%s?%s=%s&%s=%d", URL, FILTER_PARAMETER, filter, ID_PARAMETER, id);
    }
}