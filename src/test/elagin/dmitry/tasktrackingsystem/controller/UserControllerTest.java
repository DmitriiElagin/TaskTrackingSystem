package elagin.dmitry.tasktrackingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elagin.dmitry.tasktrackingsystem.dto.UserRequest;
import elagin.dmitry.tasktrackingsystem.entities.User;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final String URL = "/api/v1/users/";
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void getAllUsers() throws Exception {
        final var User = new User("John", "Doe");
        final var Users = List.of(User);

        given(service.findAll()).willReturn(Users);

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].lastName", is(User.getLastName())))
                .andExpect(jsonPath("$[0].firstName", is(User.getFirstName())));
    }

    @Test
    void getUser() throws Exception {
        final var user = new User("John", "Doe");

        given(service.findById(1)).willReturn(Optional.of(user));

        mockMvc
                .perform(MockMvcRequestBuilders.get(URL + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())));
    }

    @Test
    void getUserShouldReturnErrors() throws Exception {
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
    public void saveUser() throws Exception {
        final var dto = new UserRequest("John", "Doe");
        final var content = mapper.writer().writeValueAsString(dto);

        given(service.save(ArgumentMatchers.any())).willReturn(dto.toUser());

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));


    }

    @Test
    public void saveUserShouldReturnErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(
                new UserRequest(-1, "asdasdasdasdasdasdasdasdasdaaaaaa", ""));

        mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("firstName")))
                .andExpect(jsonPath("$.message", containsString("lastName")))
                .andExpect(jsonPath("$.message", containsString("id")));
    }

    @Test
    public void updateUser() throws Exception {
        final var dto = new UserRequest(1, "John", "Doe");
        final var content = mapper.writer().writeValueAsString(dto);

        when(service.findById(anyInt())).thenReturn(Optional.of(dto.toUser()));
        when(service.save(ArgumentMatchers.any())).thenReturn(dto.toUser());

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(dto.getId())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())));

    }

    @Test
    public void updateUserShouldReturnErrors() throws Exception {
        var content = mapper.writer().writeValueAsString(
                new UserRequest(-1, "asdasdasdasdasdasdasdasdasdasdasd", ""));

        mockMvc
                .perform(MockMvcRequestBuilders.put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.message", containsString("firstName")))
                .andExpect(jsonPath("$.message", containsString("lastName")))
                .andExpect(jsonPath("$.message", containsString("id")));

        content = mapper.writer().writeValueAsString(new UserRequest(1, "John", "Doe"));

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
    public void deleteUser() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(URL + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void deleteUserShouldReturnErrors() throws Exception {
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