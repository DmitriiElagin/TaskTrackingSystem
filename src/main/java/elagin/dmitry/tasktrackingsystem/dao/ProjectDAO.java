package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for a data access object that performs operations with an entity  {@link Project}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface ProjectDAO extends CrudRepository<Project, Integer> {
}
