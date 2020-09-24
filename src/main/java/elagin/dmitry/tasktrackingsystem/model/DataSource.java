package elagin.dmitry.tasktrackingsystem.model;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.dao.TaskDAO;
import elagin.dmitry.tasktrackingsystem.dao.UserDAO;

import java.io.File;

public interface DataSource {

    ProjectDAO getProjectDAO();
    UserDAO getUserDAO();
    TaskDAO getTaskDAO();

    void saveToFile(File file) throws Exception;
    void readFromFile(File file) throws Exception;
}
