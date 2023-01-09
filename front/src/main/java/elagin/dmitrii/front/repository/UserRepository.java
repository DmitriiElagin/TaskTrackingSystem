package elagin.dmitrii.front.repository;

import elagin.dmitrii.front.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    boolean existsByUsername(String username);

    boolean existsById(Integer id);
}
