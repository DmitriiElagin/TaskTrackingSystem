package elagin.dmitry.tasktrackingsystem.model;

import elagin.dmitry.tasktrackingsystem.model.entities.Project;
import elagin.dmitry.tasktrackingsystem.model.entities.Task;
import elagin.dmitry.tasktrackingsystem.model.entities.User;
import javafx.collections.ObservableList;

import java.io.File;

public class Repository {



    private static Repository instance;
    private DataSource dataSource;



    private Repository() {

    }

    public static Repository getInstance() {
        if(instance==null) {
            instance=new Repository();
        }
        return instance;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Project findProjectById (int projectId) {
        return dataSource.getProjectDAO().findProjectById(projectId);
    }

    public void saveProject(Project project) {
        dataSource.getProjectDAO().save(project);
    }

    public void deleteProject(Project project) {
        dataSource.getProjectDAO().delete(project);
    }

   public ObservableList<Project> findAllProjects() {
        return dataSource.getProjectDAO().findAllProjects();
   }




   public void saveUser(User user) {
      dataSource.getUserDAO().save(user);
   }

   public ObservableList<User> findAllUsers() {
        return dataSource.getUserDAO().getAllUsers();
   }

   public User findUserById(int id) {
        return dataSource.getUserDAO().findUserById(id);
    }

    public void deleteUser(User user) {
        dataSource.getUserDAO().delete(user);
    }

public ObservableList<Task> findAllTasks() {
        return dataSource.getTaskDAO().findAllTasks();
}

    public ObservableList<Task> findProjectTasks(int projectId) {
        return dataSource.getTaskDAO().findProjectTasks(projectId);
    }

    public ObservableList<Task> findUserTasks(int userId) {
        return dataSource.getTaskDAO().findUserTasks(userId);
    }

   public Task findTaskById(int id) {
        return dataSource.getTaskDAO().findTaskById(id);
    }

    public void saveTask(Task task) {
        dataSource.getTaskDAO().save(task);
    }

    public void deleteTask(Task task) {
        dataSource.getTaskDAO().delete(task);
    }

    public void saveDBToFile(File file) throws Exception {
        dataSource.saveToFile(file);
    }

    public void readDBFromFile(File file) throws Exception {
        dataSource.readFromFile(file);
    }



}
