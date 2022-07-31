package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.entities.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectDAO dao;

    public ProjectService(ProjectDAO dao) {
        this.dao = dao;
    }

    public Iterable<Project> findAll() {
        return dao.findAll();
    }

    public Optional<Project> findById(int id) {
        return dao.findById(id);
    }

    public Project save(Project project) {
        return dao.save(project);
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
