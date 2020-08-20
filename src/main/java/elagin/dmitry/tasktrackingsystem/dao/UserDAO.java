package elagin.dmitry.tasktrackingsystem.dao;



import elagin.dmitry.tasktrackingsystem.model.User;
import javafx.collections.ObservableList;



public interface UserDAO {

    ObservableList<User> getAllUsers();
    User getUserById(int id);
    void save(User user);
    void delete(User user);

    
}
