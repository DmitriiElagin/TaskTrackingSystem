package elagin.dmitry.tasktrackingsystem.model;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.dao.TaskDAO;
import elagin.dmitry.tasktrackingsystem.dao.UserDAO;

import java.io.File;

/**
 * The interface for the application data source.
 * Provides data access objects.
 * Provides methods for reading and writing data source data to a file
 * @author Dmitry Elagin
 */
public interface DataSource {

    /**
     *Returns a data access object for  {@link Project}
     * @see ProjectDAO
     */
    ProjectDAO getProjectDAO();

    /**
     *Returns a data access object for {@link User}
     * @see UserDAO
     */
    UserDAO getUserDAO();

    /**
     *Returns a data access object for {@link Task}
     * @see TaskDAO
     */
    TaskDAO getTaskDAO();

    /**
     * Saves data source data to the specified file
     * @param file <b>File</b> object referencing the file to which the data should be saved
     * @throws Exception I/O exception thrown during file output
     */
    void saveToFile(File file) throws Exception;

    /**
     * Reads data from the specified file and stores it in the application data source
     * @param file <b>File</b> object referring to the source file from which data should be read
     * @throws Exception I/O exception thrown while reading data from a file
     */
    void readFromFile(File file) throws Exception;
}
