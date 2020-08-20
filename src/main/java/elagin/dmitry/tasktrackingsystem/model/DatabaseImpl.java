package elagin.dmitry.tasktrackingsystem.model;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.dao.TaskDAO;
import elagin.dmitry.tasktrackingsystem.dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;


public class DatabaseImpl implements Database, Serializable {

    private static DatabaseImpl instance;

    private final ObservableList<User> users;
    private final ObservableList<Project> projects;
    private final ObservableList<Task> tasks;

    private int taskId;
    private int userId;
    private int projectId;

    private DatabaseImpl() {
        projects= FXCollections.observableList(new ArrayList<>());
        users=FXCollections.observableList(new ArrayList<>());
        tasks=FXCollections.observableList(new ArrayList<>());
    }

    public static DatabaseImpl getInstance() {
        if(instance==null) {
            instance=new DatabaseImpl();
        }

        return instance;
    }


    @Override
    public ProjectDAO getProjectDAO() {
        return new ProjectDAOImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOImpl();
    }

    @Override
    public void saveToFile(File file) throws IOException {
        DBSaver saver=new DBSaver();
        saver.setProjectId(projectId);
        saver.setUserId(userId);
        saver.setTaskId(taskId);
        saver.setProjects(projects.toArray(new Project[0]));
        saver.setTasks(tasks.toArray(new Task[0]));
        saver.setUsers(users.toArray(new User[0]));
        ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(saver);
        outputStream.close();
    }

    @Override
    public void readFromFile(File file) throws Exception {
        ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(file));
        DBSaver saver= (DBSaver) inputStream.readObject();
        inputStream.close();
        projectId=saver.getProjectId();
        taskId=saver.getTaskId();
        userId=saver.getUserId();
        projects.clear();
        projects.addAll(saver.getProjects());
        users.clear();
        users.addAll(saver.getUsers());
        tasks.clear();
        tasks.addAll(saver.getTasks());



    }

    private class ProjectDAOImpl implements ProjectDAO{

        @Override
        public ObservableList<Project> getAllProjects() {
            return projects;
        }

        @Override
        public Project getProjectById(int id) {

            for (Project project:projects) {
                if(project.getId()==id) {
                    return project;
                }
            }
            return null;
        }

        @Override
        public void save(Project project) {
            int id = project.getId();
            if(id>0) {
                Project tmpProject=getProjectById(id);
                id = projects.indexOf(tmpProject);
                projects.set(id,project);
            }
            else {
                project.setId(++projectId);
                projects.add(project);
            }

        }

        @Override
        public void delete(Project project) {
            tasks.removeAll(getTaskDAO().getProjectTasks(project.getId()));
            projects.remove(project);

        }
    }

    private class UserDAOImpl implements UserDAO {

        @Override
        public ObservableList<User> getAllUsers() {
            return users;
        }

        @Override
        public User getUserById(int id) {

            for (User user:users) {

                if(user.getId()==id) {
                    return user;
                }
            }

            return null;
        }

        @Override
        public void save(User user) {
            int id = user.getId();
            if(id>0) {
                User tmpUser=getUserById(id);
                id = users.indexOf(tmpUser);
                users.set(id,user);
            }
            else {
                user.setId(++userId);
                users.add(user);
            }

        }

        @Override
        public void delete(User user) {
            tasks.removeAll(getTaskDAO().getUserTasks(user.getId()));
            users.remove(user);

        }
    }

    private class TaskDAOImpl implements TaskDAO {

        @Override
        public ObservableList<Task> getAllTasks() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getUserTasks(int userId) {
            ObservableList<Task> result=FXCollections.observableList(new ArrayList<>());

            for(Task task:tasks) {
                if(task.getResponsible().getId()==userId) {
                    result.add(task);
                }
            }
            return result;
        }

        @Override
        public ObservableList<Task> getProjectTasks(int projectId) {
            ObservableList<Task> result=FXCollections.observableList(new ArrayList<>());

            for(Task task:tasks) {
                if(task.getProject().getId()==projectId) {
                    result.add(task);
                }
            }


            return result;
        }

        @Override
        public Task getTaskById(int id) {

            for (Task task:tasks) {
                if(task.getId()==id) {
                    return task;
                }
            }

            return null;
        }

        @Override
        public void save(Task task) {
            int id = task.getId();
            if(id>0) {
                Task tmpTask=getTaskById(id);
                id = tasks.indexOf(tmpTask);
                tasks.set(id,task);
            }
            else {
                task.setId(++taskId);
                tasks.add(task);
            }

        }

        @Override
        public void delete(Task task) {
          tasks.remove(task);

        }
    }




}
