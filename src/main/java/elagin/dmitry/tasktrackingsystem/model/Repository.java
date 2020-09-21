package elagin.dmitry.tasktrackingsystem.model;


import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import javafx.collections.ObservableList;

import java.io.File;

/**
 * Initializes and stores a data source, delegates methods to data access objects
 *
 * @author Dmitry Elagin
 */
public class Repository {

    private static Repository instance;
    private DataSource dataSource;

    private Repository() {
    }

    /**
     * Returns an instance of the Repository
     */
    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    /**
     * Sets a data source
     *
     * @param dataSource An object that implements the DataSource interface
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Searches for and returns a project with the specified id
     *
     * @param projectId project id to find
     * @return {@link Project} object, if a project with the specified id was found, null otherwise
     */
    public Project findProjectById(int projectId) {
        return dataSource.getProjectDAO().getProjectById(projectId);
    }

    /**
     * Saves a project to the data source
     *
     * @param project {@link Project} object to be saved to the data source
     */
    public void saveProject(Project project) {
        dataSource.getProjectDAO().save(project);
    }

    /**
     * Removes a project from a data source
     *
     * @param project {@link Project} object to be removed from data source
     */
    public void deleteProject(Project project) {
        dataSource.getProjectDAO().delete(project);
    }

    /**
     * Returns all projects stored in a data source
     */
    public ObservableList<Project> findAllProjects() {
        return dataSource.getProjectDAO().getAllProjects();
    }


    /**
     * Saves a user to the data source
     *
     * @param user {@link User} object to be saved to the data source
     */
    public void saveUser(User user) {
        dataSource.getUserDAO().save(user);
    }

    /**
     * Returns all users stored in a data source
     */
    public ObservableList<User> findAllUsers() {
        return dataSource.getUserDAO().getAllUsers();
    }

    /**
     * Searches for and returns a user with the specified id
     *
     * @param id user id to find
     * @return {@link User} object, if a user with the specified id was found, null otherwise
     */
    public User findUserById(int id) {
        return dataSource.getUserDAO().getUserById(id);
    }

    /**
     * Removes a user from a data source
     *
     * @param user {@link User} object to be removed from data source
     */
    public void deleteUser(User user) {
        dataSource.getUserDAO().delete(user);
    }

    /**
     * Returns all tasks stored in a data source
     */
    public ObservableList<Task> findAllTasks() {
        return dataSource.getTaskDAO().getAllTasks();
    }

    /**
     * Searches for and returns a list of tasks associated with the specified project
     * @param projectId project id referenced by tasks to find
     */
    public ObservableList<Task> findProjectTasks(int projectId) {
        return dataSource.getTaskDAO().getProjectTasks(projectId);
    }

    /**
     * Searches for and returns a list of tasks associated with the specified user
     *
     * @param userId user id referenced by tasks to find
     */
    public ObservableList<Task> findUserTasks(int userId) {
        return dataSource.getTaskDAO().getUserTasks(userId);
    }

    /**
     * Searches for and returns a task with the specified id
     * @param id task id to find
     * @return  {@link Task} object, if a task with the specified id was found, null otherwise
     */
    public Task findTaskById(int id) {
        return dataSource.getTaskDAO().getTaskById(id);
    }

    /**
     * Saves the task to the data source
     * @param task {@link Task} object to be saved to the data source
     */
    public void saveTask(Task task) {
        dataSource.getTaskDAO().save(task);
    }

    /**
     * Removes a task from a data source
     * @param task {@link Task} object to be removed from data source
     */
    public void deleteTask(Task task) {
        dataSource.getTaskDAO().delete(task);
    }

    /**
     * Saves data source data to the specified file
     * @param file <b>File</b> object referencing the file to which the data should be saved
     * @throws Exception I/O exception thrown during file output
     */
    public void saveDataToFile(File file) throws Exception {
        dataSource.saveToFile(file);
    }

    /**
     * Reads data from the specified file and stores it in the application data source
     * @param file <b>File</b> object referring to the source file from which data should be read
     * @throws Exception I/O exception thrown while reading data from a file
     */
    public void readDataFromFile(File file) throws Exception {
        dataSource.readFromFile(file);
    }


}
