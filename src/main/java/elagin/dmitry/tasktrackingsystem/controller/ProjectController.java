package elagin.dmitry.tasktrackingsystem.controller;

import elagin.dmitry.tasktrackingsystem.dto.ProjectDTO;
import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.exception.EntityNotFoundException;
import elagin.dmitry.tasktrackingsystem.service.ProjectService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Project> getAllProjects() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Project getProject(@PathVariable @Min(1) int id) {
        return service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Проект с id = %d не найден!", id)));
    }

    @PostMapping("/")
    public Project saveProject(@Valid @RequestBody ProjectDTO dto) {
        return service.save(dto.toProject());
    }

    @PutMapping("/")
    public Project updateProject(@Valid @RequestBody ProjectDTO dto) {
        Project project;

        final var optional = service.findById(dto.getId());

        if (optional.isPresent()) {
            project = optional.get();

            project.setTitle(dto.getTitle());

            project = service.save(project);

        } else {
            throw new EntityNotFoundException(String.format("Сущность с id = %d не найдена!", dto.getId()));
        }
        return project;
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable @Min(1) int id) {
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("Сущность с id = %d не найдена!", id));
        }

    }


}
