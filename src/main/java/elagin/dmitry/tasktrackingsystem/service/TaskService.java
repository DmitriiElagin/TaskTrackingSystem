package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.Task;
import elagin.dmitry.tasktrackingsystem.repository.TaskRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TaskService {
    private final TaskRepository dao;

    public TaskService(TaskRepository dao) {
        this.dao = dao;
    }

    @Transactional
    public Iterable<Task> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Optional<Task> findById(int id) {
        return dao.findById(id);
    }

    @Transactional
    public Iterable<Task> findByProjectId(int projectId) {
        return dao.findByProjectId(projectId);
    }

    @Transactional
    public Iterable<Task> findByResponsibleId(int responsibleId) {
        return dao.findByResponsibleId(responsibleId);
    }

    @Transactional
    public Task save(Task task) {
        return dao.save(task);
    }

    @Transactional
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
