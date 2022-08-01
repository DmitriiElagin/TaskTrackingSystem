package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository dao;

    public ProjectService(ProjectRepository dao) {
        this.dao = dao;
    }

    @Transactional
    public Iterable<Project> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Optional<Project> findById(int id) {
        return dao.findById(id);
    }

    @Transactional
    public Project save(Project project) {
        return dao.save(project);
    }

    @Transactional
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
