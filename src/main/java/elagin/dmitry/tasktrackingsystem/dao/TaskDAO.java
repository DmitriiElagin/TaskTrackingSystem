package elagin.dmitry.tasktrackingsystem.dao;



import elagin.dmitry.tasktrackingsystem.model.entities.Task;
import javafx.collections.ObservableList;

public interface TaskDAO {



    ObservableList<Task> findAllTasks();

    ObservableList<Task> findUserTasks(int userId);

    ObservableList<Task> findProjectTasks(int projectId);

    Task findTaskById(int id);

    void save(Task task);

    void saveAll(Task[] tasks);

    void delete(Task task);


}
