package elagin.dmitry.tasktrackingservice.repository;

import elagin.dmitry.tasktrackingservice.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface for a data access object that performs operations with an entity  {@link User}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
