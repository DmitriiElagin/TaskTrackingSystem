package elagin.dmitry.tasktrackingsystem.controller;

import elagin.dmitry.tasktrackingsystem.dto.UserDTO;
import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.exception.EntityNotFoundException;
import elagin.dmitry.tasktrackingsystem.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable @Min(1) int id) {
        return service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", id)));
    }

    @PostMapping("/")
    public User saveUser(@Valid @RequestBody UserDTO dto) {
        return service.save(dto.toUser());
    }

    @PutMapping("/")
    public User updateUser(@Valid @RequestBody UserDTO dto) {
        User user;

        final var optional = service.findById(dto.getId());

        if (optional.isPresent()) {
            user = optional.get();

            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());

            user = service.save(user);

        } else {
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", dto.getId()));
        }
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Min(1) int id) {
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", id));
        }
    }
}
