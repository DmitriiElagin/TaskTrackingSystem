package elagin.dmitry.tasktrackingservice.controller;

import elagin.dmitry.tasktrackingservice.dto.ProjectRequest;
import elagin.dmitry.tasktrackingservice.entities.Project;
import elagin.dmitry.tasktrackingservice.exception.EntityNotFoundException;
import elagin.dmitry.tasktrackingservice.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/v1/projects")
public class ProjectController {
  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
  private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Project> getAllProjects() {
      logger.info("GET-запрос всех проектов");
        return service.findAll();
    }

    @GetMapping("/{id}")
    Project getProject(@PathVariable @Min(1) int id) {
      logger.info("GET-запрос проекта с id = {}", id);
        return service.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Проект с id = %d не найден!", id)));
    }

    @PostMapping
    public Project saveProject(@Valid @RequestBody ProjectRequest dto) {
      logger.info("POST-запрос сохранения проекта {}", dto);
        return service.save(dto.toProject());
    }

    @PutMapping
    public Project updateProject(@Valid @RequestBody ProjectRequest dto) {
      logger.info("PUT-запрос обновления проекта {}", dto);
        Project project;

        final var optional = service.findById(dto.getId());

        if (optional.isPresent()) {
            project = optional.get();

            project.setTitle(dto.getTitle());

            project = service.save(project);

        } else {
          logger.info("Проект с id = {} не найден!", dto.getId());
            throw new EntityNotFoundException(String.format("Сущность с id = %d не найдена!", dto.getId()));
        }
        return project;
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable @Min(1) int id) {
      logger.info("DELETE-запрос удаления проекта с id = {}", id);
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
          logger.info("Проект с id = {} не найден!", id);
            throw new EntityNotFoundException(String.format("Сущность с id = %d не найдена!", id));
        }

    }


}
