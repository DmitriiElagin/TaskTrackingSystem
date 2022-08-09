package elagin.dmitry.tasktrackingservice.repository;

import elagin.dmitry.tasktrackingservice.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for a data access object that performs operations with an entity  {@link Task}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    /**
     * Searches for and returns a list of tasks associated with the specified user
     *
     * @param id user id referenced by tasks to find
     */
    List<Task> findByResponsibleId(int id);

    /**
     * Searches for and returns a list of tasks associated with the specified project
     *
     * @param id project id referenced by tasks to find
     */
    List<Task> findByProjectId(int id);

}
