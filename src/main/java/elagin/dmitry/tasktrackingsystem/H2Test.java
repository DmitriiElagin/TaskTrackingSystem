package elagin.dmitry.tasktrackingsystem;

import elagin.dmitry.tasktrackingsystem.model.entities.Project;
import elagin.dmitry.tasktrackingsystem.model.entities.Task;
import elagin.dmitry.tasktrackingsystem.model.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class H2Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

//        Class.forName("org.h2.Driver").newInstance();
//        Connection conn = DriverManager.getConnection("jdbc:h2:~/task_tracking_system",
//                "sa", "");
//        conn.close();

        SessionFactory factory=new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Project.class)
                .buildSessionFactory();
         User user=new User("Дмитрий","Елагин");
         Project project=new Project("Немезида");
         Task task=new Task();

         task.setDescription("Описание задачи");
         task.setTheme("Тема задачи");
         task.setType("тип задачи");
         task.setPriority(Task.TaskPriority.MEDIUM);
         task.setProject(project);
         task.setResponsible(user);

         try(Session session=factory.getCurrentSession()) {
             session.beginTransaction();
             System.out.println("Сохранение объекта Task...");
             session.save(task);
//             System.out.println("Сохранение объекта User...");
//             user.getTasks().add(task);
             session.save(user);
//             System.out.println("Сохранение объекта Project");
//             project.getTasks().add(task);
//             session.save(project);
             session.getTransaction().commit();
             System.out.println("Успешно!");



         }

        try(Session session=factory.getCurrentSession()) {
            session.beginTransaction();

            int id=1;
            System.out.println("Запрос проекта с id = "+id);
            project=session.get(Project.class,id);
            System.out.println(project);
            session.getTransaction().commit();
            System.out.println(project.getTasks().get(0).getTheme());
            System.out.println("Успешно!");
        }

    }
}
