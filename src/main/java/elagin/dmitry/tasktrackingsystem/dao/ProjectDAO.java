package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.model.Project;
import elagin.dmitry.tasktrackingsystem.model.User;
import javafx.collections.ObservableList;



/**
 * Interface for a data access object that performs operations with an entity  {@link Project}
 * @author Dmitry Elagin
 */
public interface ProjectDAO {

    /**
     * Returns all projects stored in a data source
     */
    ObservableList<Project> getAllProjects();

    /**
     * Searches for and returns a project with the specified id
     * @param id project id to find
     * @return {@link Project} object, if a project with the specified id was found, null otherwise
     */
    Project getProjectById(int id);
    /**
     * Saves a project to the data source
     * @param project {@link Project} object to be saved to the data source
     */
    void save(Project project);

    /**
     * Removes a project from a data source
     * @param project {@link Project} object to be removed from data source
     */
    void delete(Project project);

}
