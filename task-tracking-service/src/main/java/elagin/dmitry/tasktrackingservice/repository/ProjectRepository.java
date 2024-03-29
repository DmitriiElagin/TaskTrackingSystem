package elagin.dmitry.tasktrackingservice.repository;

import elagin.dmitry.tasktrackingservice.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for a data access object that performs operations with an entity  {@link Project}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
