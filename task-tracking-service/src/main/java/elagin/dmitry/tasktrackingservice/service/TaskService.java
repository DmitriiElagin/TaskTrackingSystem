package elagin.dmitry.tasktrackingservice.service;

import elagin.dmitry.tasktrackingservice.entities.Task;
import elagin.dmitry.tasktrackingservice.repository.TaskRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    @Transactional
    public Iterable<Task> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    @Transactional
    public Iterable<Task> findByProjectId(int projectId) {
        return repository.findByProjectId(projectId);
    }

    @Transactional
    public Iterable<Task> findByResponsibleId(int responsibleId) {
        return repository.findByResponsibleId(responsibleId);
    }

    @Transactional
    public Task save(Task task) {
        return repository.save(task);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
