package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface for a data access object that performs operations with an entity  {@link User}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface UserDAO extends CrudRepository<User, Integer> {

}
