package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.entities.User;

import java.util.List;


/**
 * Interface for a data access object that performs operations with an entity  {@link User}
 * @author Dmitry Elagin
 */
public interface UserDAO {

    /**
     * Returns all users stored in a data source
     */
    List<User> getAllUsers();

    /**
     * Searches for and returns a user with the specified id
     * @param id user id to find
     * @return  {@link User} object, if a user with the specified id was found, null otherwise
     */
    User getUserById(int id);

    /**
     * Saves a user to the data source
     *
     * @param user {@link User} object to be saved to the data source
     */
    User save(User user);

    /**
     * Removes a user from a data source
     * @param user  {@link User} object to be removed from data source
     */
    void delete(User user);

}
