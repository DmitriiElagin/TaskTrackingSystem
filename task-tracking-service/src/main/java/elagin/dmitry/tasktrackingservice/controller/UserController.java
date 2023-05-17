package elagin.dmitry.tasktrackingservice.controller;

import elagin.dmitry.tasktrackingservice.dto.UserRequest;
import elagin.dmitry.tasktrackingservice.entities.User;
import elagin.dmitry.tasktrackingservice.exception.EntityNotFoundException;
import elagin.dmitry.tasktrackingservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        logger.info("GET-запрос всех пользователей");
        return service.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable @Min(1) int id) {
        logger.info("GET-запрос пользователя с id = {}", id);
        return service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", id)));
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody UserRequest dto) {
        logger.info("POST-запрос сохранения пользователя {}", dto);
        return service.save(dto.toUser());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody UserRequest dto) {
        logger.info("PUT-запрос обновления пользователя {}", dto);

        User user;

        final var optional = service.findById(dto.getId());

        if (optional.isPresent()) {
            user = optional.get();

            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());

            user = service.save(user);

        } else {
            logger.info("Пользователь c id = {} не найден!", dto.getId());
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", dto.getId()));
        }
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Min(1) int id) {
        logger.info("DELETE-запрос удаления пользователя c id = {}", id);
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Пользователь c id = {} не найден!", id);
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", id));
        }
    }
}
