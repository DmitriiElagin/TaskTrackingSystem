package elagin.dmitry.tasktrackingsystem.dao;



import elagin.dmitry.tasktrackingsystem.model.entities.User;
import javafx.collections.ObservableList;



public interface UserDAO {

    ObservableList<User> getAllUsers();
    User findUserById(int id);
    void save(User user);
    void saveAll(User[] users);
    void delete(User user);

    
}
