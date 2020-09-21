package elagin.dmitry.tasktrackingsystem.model;


import javafx.collections.ObservableList;
import java.io.File;


public class Repository {



    private static Repository instance;
    private Database database;



    private Repository () {
    }

    public static Repository getInstance() {
        if(instance==null) {
            instance=new Repository();
        }
        return instance;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Project findProjectById (int projectId) {
        return database.getProjectDAO().getProjectById(projectId);
    }

    public void saveProject(Project project) {
        database.getProjectDAO().save(project);
    }

    public void deleteProject(Project project) {
        database.getProjectDAO().delete(project);
    }

   public ObservableList<Project> findAllProjects() {
        return database.getProjectDAO().getAllProjects();
   }




   public void saveUser(User user) {
      database.getUserDAO().save(user);
   }

   public ObservableList<User> findAllUsers() {
        return database.getUserDAO().getAllUsers();
   }

   public User findUserById(int id) {
        return database.getUserDAO().getUserById(id);
    }

    public void deleteUser(User user) {
        database.getUserDAO().delete(user);
    }

public ObservableList<Task> findAllTasks() {
        return database.getTaskDAO().getAllTasks();
}

    public ObservableList<Task> findProjectTasks(int projectId) {
        return database.getTaskDAO().getProjectTasks(projectId);
    }

    public ObservableList<Task> findUserTasks(int userId) {
        return database.getTaskDAO().getUserTasks(userId);
    }

   public Task findTaskById(int id) {
        return database.getTaskDAO().getTaskById(id);
    }

    public void saveTask(Task task) {
        database.getTaskDAO().save(task);
    }

    public void deleteTask(Task task) {
        database.getTaskDAO().delete(task);
    }

    public void saveDBToFile(File file) throws Exception {
        database.saveToFile(file);
    }

    public void readDBFromFile(File file) throws Exception {
        database.readFromFile(file);
    }



}
