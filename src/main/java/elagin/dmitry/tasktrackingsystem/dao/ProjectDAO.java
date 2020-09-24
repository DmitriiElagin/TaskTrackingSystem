package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.model.entities.Project;
import javafx.collections.ObservableList;



public interface ProjectDAO {

    ObservableList<Project> findAllProjects();
    Project findProjectById(int id);
    void save(Project project);
    void delete(Project project);
    void saveAll(Project[] projects);

}
