package elagin.dmitry.tasktrackingsystem.dao;



import elagin.dmitry.tasktrackingsystem.model.Task;
import javafx.collections.ObservableList;

public interface TaskDAO {



    ObservableList<Task> getAllTasks();

    ObservableList<Task> getUserTasks(int userId);

    ObservableList<Task> getProjectTasks(int projectId);

    Task getTaskById(int id);

    void save(Task task);

    void delete(Task task);

}
