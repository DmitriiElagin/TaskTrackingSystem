package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.entities.User;
import org.springframework.data.repository.CrudRepository;


/**
 * Interface for a data access object that performs operations with an entity  {@link User}
 *
 * @author Dmitry Elagin
 */
public interface UserDAO extends CrudRepository<User, Integer> {

}
