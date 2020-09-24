package elagin.dmitry.tasktrackingsystem.model;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.dao.TaskDAO;
import elagin.dmitry.tasktrackingsystem.dao.UserDAO;
import elagin.dmitry.tasktrackingsystem.model.entities.Project;
import elagin.dmitry.tasktrackingsystem.model.entities.Task;
import elagin.dmitry.tasktrackingsystem.model.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.util.List;


public class H2DataSource implements DataSource {

private final SessionFactory factory;
private static H2DataSource instance;


private final ObservableList<Project> projects;
private final ObservableList<User> users;


    private H2DataSource() {
        factory=new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();


        projects=FXCollections.observableArrayList();
        users=FXCollections.observableArrayList();


    }

    public static H2DataSource getInstance () {
        if(instance==null) {
            instance=new H2DataSource();
        }

        return instance;
    }



    @Override
    public ProjectDAO getProjectDAO() {
        return new H2ProjectDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new H2UserDAO();
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new H2TaskDAO();
    }



    @Override
    public void saveToFile(File file) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            DBSaver saver = new DBSaver();
            ObservableList<Task> tasks = getTaskDAO().findAllTasks();

            saver.setProjects(projects.toArray(new Project[0]));
            saver.setTasks(tasks.toArray(new Task[0]));
            saver.setUsers(users.toArray(new User[0]));
            outputStream.writeObject(saver);
        }
    }


        @Override
    public void readFromFile(File file) throws Exception {
            try(ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(file))) {
                DBSaver saver= (DBSaver) inputStream.readObject();

                getUserDAO().saveAll(saver.getUsers());
                getProjectDAO().saveAll(saver.getProjects());
                getTaskDAO().saveAll(saver.getTasks());

                getProjectDAO().findAllProjects();
                getUserDAO().getAllUsers();

            }

    }

    private class H2ProjectDAO implements ProjectDAO {

        @Override
        public ObservableList<Project> findAllProjects() {

            try(Session session = factory.getCurrentSession()) {
               session.beginTransaction();
               projects.clear();
               projects.addAll(session.createQuery("from Project").getResultList());
                session.getTransaction().commit();
               return projects;
           }

        }

        @Override
        public Project findProjectById(int id) {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                Project project = session.get(Project.class,id);
                session.getTransaction().commit();
                return project;
            }
        }

        @Override
        public void save(Project project) {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.saveOrUpdate(project);
                session.getTransaction().commit();
            }
           findAllProjects();

        }

        @Override
        public void delete(Project project) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.delete(project);
                session.getTransaction().commit();
            }
            findAllProjects();

        }

        @Override
        public void saveAll(Project[] projects) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();

                

                for (Project project:projects) {
                    session.save(project);
                }

                session.getTransaction().commit();
            }
        }
    }

    private class H2UserDAO implements UserDAO {
        @Override
        public ObservableList<User> getAllUsers() {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                users.clear();
                users.addAll(session.createQuery("from User").getResultList());
                session.getTransaction().commit();
                return users;
            }
        }

        @Override
        public User findUserById(int id) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                User user=session.get(User.class,id);
                session.getTransaction().commit();
                return user;
            }
        }

        @Override
        public void delete(User user) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
            }

           getAllUsers();

        }

        @Override
        public void save(User user) {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.saveOrUpdate(user);
                session.getTransaction().commit();
            }
            getAllUsers();


        }

        @Override
        public void saveAll(User[] users) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();

                for (User user:users) {
                    session.save(user);
                }

                session.getTransaction().commit();
            }
        }
    }

    private class H2TaskDAO implements TaskDAO {
        @Override
        public ObservableList<Task> findAllTasks() {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                List<Task> tasks = session.createQuery("from Task").getResultList();
                session.getTransaction().commit();
                return FXCollections.observableList(tasks);
            }
        }

        @Override
        public ObservableList<Task> findUserTasks(int userId) {

            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                User user = session.get(User.class, userId);
                session.getTransaction().commit();
                return FXCollections.observableList(user.getTasks());
            }
        }

        @Override
        public ObservableList<Task> findProjectTasks(int projectId) {

            try (Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                Project project = session.get(Project.class, projectId);
                session.getTransaction().commit();
                return FXCollections.observableList(project.getTasks());
            }
        }

        @Override
        public Task findTaskById(int id) {
            Task task;
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                task = session.get(Task.class,id);
                session.getTransaction().commit();
                return task;
            }

        }

        @Override
        public void save(Task task) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();



                session.saveOrUpdate(task);
                session.getTransaction().commit();
            }



        }

        @Override
        public void saveAll(Task[] tasks) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();

                for (Task task:tasks) {
                    session.save(task);
                }

                session.getTransaction().commit();
            }
        }

        @Override
        public void delete(Task task) {
            try(Session session = factory.getCurrentSession()) {
                session.beginTransaction();
                session.delete(task);
                session.getTransaction().commit();
            }

        }
    }
}
