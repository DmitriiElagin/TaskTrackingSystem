package elagin.dmitry.tasktrackingsystem;

import elagin.dmitry.tasktrackingsystem.dao.ProjectDAO;
import elagin.dmitry.tasktrackingsystem.dao.TaskDAO;
import elagin.dmitry.tasktrackingsystem.dao.UserDAO;
import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.entities.Task;
import elagin.dmitry.tasktrackingsystem.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskTrackingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ProjectDAO projectDAO, TaskDAO taskDAO, UserDAO userDAO) {
        return args -> {
            Project sirius = new Project("Сириус");
            User yuri = new User("Юрий", "Белозеров");
            projectDAO.save(sirius);
            userDAO.save(yuri);
            taskDAO.save(new Task("Разработка фонового процесса №6",
                    "Разработать функционал фонового процесса репликации заявления",
                    "Разработка нового функционала",
                    sirius,
                    yuri));

            Project selfCollection = new Project("Самоинкасация юридических лиц");
            User dmitrii = new User("Дмитрий", "Елагин");
            projectDAO.save(selfCollection);
            userDAO.save(dmitrii);
            taskDAO.save(new Task(0,
                    "Логирование ФП",
                    "Добавить в логи фоновых процессов id сущностей",
                    "Разработка нового функционала",
                    selfCollection,
                    dmitrii,
                    Task.TaskPriority.LOW));
        };
    }
}
