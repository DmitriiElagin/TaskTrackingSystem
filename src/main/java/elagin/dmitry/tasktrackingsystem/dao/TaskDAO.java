package elagin.dmitry.tasktrackingsystem.dao;


import elagin.dmitry.tasktrackingsystem.model.entities.Task;
import javafx.collections.ObservableList;

/**
 * Interface for a data access object that performs operations with an entity  {@link Task}
 * @author Dmitry Elagin
 */
public interface TaskDAO {


    /**
     * Returns all tasks stored in a data source
     */
    ObservableList<Task> getAllTasks();

    /**
     * Searches for and returns a list of tasks associated with the specified user
     * @param userId user id referenced by tasks to find
     */
    ObservableList<Task> getUserTasks(int userId);

    /**
     * Searches for and returns a list of tasks associated with the specified project
     * @param projectId project id referenced by tasks to find
     */
    ObservableList<Task> getProjectTasks(int projectId);

    /**
     * Searches for and returns a task with the specified id
     * @param id task id to find
     * @return  {@link Task} object, if a task with the specified id was found, null otherwise
     */
    Task getTaskById(int id);

    /**
     * Saves the task to the data source
     * @param task {@link Task} object to be saved to the data source
     */
    void save(Task task);

    void saveAll(Task[] tasks);

    /**
     * Removes a task from a data source
     * @param task {@link Task} object to be removed from data source
     */
    void delete(Task task);

}
