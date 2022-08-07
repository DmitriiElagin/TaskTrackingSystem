package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    @Transactional
    public Iterable<Project> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<Project> findById(int id) {
        return repository.findById(id);
    }

    @Transactional
    public Project save(Project project) {
        return repository.save(project);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
