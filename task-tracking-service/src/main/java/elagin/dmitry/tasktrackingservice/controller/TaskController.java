package elagin.dmitry.tasktrackingservice.controller;

import elagin.dmitry.tasktrackingservice.dto.TaskRequest;
import elagin.dmitry.tasktrackingservice.entities.Task;
import elagin.dmitry.tasktrackingservice.exception.EntityNotFoundException;
import elagin.dmitry.tasktrackingservice.service.ProjectService;
import elagin.dmitry.tasktrackingservice.service.TaskService;
import elagin.dmitry.tasktrackingservice.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1/tasks")
public class TaskController {

    public enum SearchFilter {
        PROJECT, RESPONSIBLE
    }

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<Task> find(@RequestParam(required = false) String filter,
                               @RequestParam(required = false, defaultValue = "0") int id) {

        if (filter != null) {
            SearchFilter searchFilter;

            try {
                searchFilter = SearchFilter.valueOf(filter.toUpperCase());

                switch (searchFilter) {
                    case PROJECT:
                        return taskService.findByProjectId(id);
                    case RESPONSIBLE:
                        return taskService.findByResponsibleId(id);
                }
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException(String.format("Параметр filter не поддерживает аргумент '%s'", filter));
            }
        }

        return taskService.findAll();
    }

    @GetMapping("/{id}")
    Task getTask(@PathVariable @Min(1) int id) {
        return taskService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Задача с id = %d не найдена!", id)));
    }

    @PostMapping
    public Task saveTask(@Valid @RequestBody TaskRequest dto) {
        final var optionalProject = projectService.findById(dto.getProjectId());
        final var optionalUser = userService.findById(dto.getResponsibleId());

        if (optionalProject.isPresent()) {
            if (optionalUser.isPresent()) {
                final var task = dto.toTask();
                task.setProject(optionalProject.get());
                task.setResponsible(optionalUser.get());

                return taskService.save(task);
            } else {
                throw new EntityNotFoundException(String.format("Пользователь с id = %d не найден!", dto.getResponsibleId()));
            }
        } else {
            throw new EntityNotFoundException(String.format("Проект с id = %d не найден!", dto.getProjectId()));
        }
    }

    @PutMapping
    public Task updateTask(@Valid @RequestBody TaskRequest dto) {
        Task task;

        final var optional = taskService.findById(dto.getId());

        if (optional.isPresent()) {
            task = optional.get();

            task.setTheme(dto.getTheme());
            task.setDescription(dto.getDescription());
            task.setPriority(dto.getPriority());
            task.setType(dto.getType());

            task = taskService.save(task);

        } else {
            throw new EntityNotFoundException(String.format("Задача с id = %d не найдена!", dto.getId()));
        }
        return task;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable @Min(1) int id) {
        try {
            taskService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("Задача с id = %d не найдена!", id));
        }
    }
}
