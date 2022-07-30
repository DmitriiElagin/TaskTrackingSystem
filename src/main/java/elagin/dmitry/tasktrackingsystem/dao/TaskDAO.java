package elagin.dmitry.tasktrackingsystem.dao;

import elagin.dmitry.tasktrackingsystem.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for a data access object that performs operations with an entity  {@link Task}
 *
 * @author Dmitry Elagin
 */
@Repository
public interface TaskDAO extends CrudRepository<Task, Integer> {

    /**
     * Searches for and returns a list of tasks associated with the specified user
     *
     * @param userId user id referenced by tasks to find
     */
    List<Task> findTasksByUserId(int userId);

    /**
     * Searches for and returns a list of tasks associated with the specified project
     *
     * @param projectId project id referenced by tasks to find
     */
    List<Task> findTasksByProjectId(int projectId);

}
