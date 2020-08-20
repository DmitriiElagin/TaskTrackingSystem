package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.model.Project;
import javafx.collections.ObservableList;



public interface ProjectDAO {

    ObservableList<Project> getAllProjects();
    Project getProjectById(int id);
    void save(Project project);
    void delete(Project project);

}
