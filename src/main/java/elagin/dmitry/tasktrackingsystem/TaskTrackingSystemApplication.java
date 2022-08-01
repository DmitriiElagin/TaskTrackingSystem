package elagin.dmitry.tasktrackingsystem;

import elagin.dmitry.tasktrackingsystem.entities.Project;
import elagin.dmitry.tasktrackingsystem.entities.Task;
import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.repository.ProjectRepository;
import elagin.dmitry.tasktrackingsystem.repository.TaskRepository;
import elagin.dmitry.tasktrackingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;

@SpringBootApplication
public class TaskTrackingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ProjectRepository projectRepository,
                                               TaskRepository taskRepository,
                                               UserRepository userRepository) {
        return args -> createTestData(projectRepository, userRepository, taskRepository);

    }

    @Transactional
    public void createTestData(ProjectRepository projectRepository,
                               UserRepository userRepository,
                               TaskRepository taskRepository) {
        final var sirius = projectRepository.save(new Project("Сириус"));

        final var yuri = userRepository.save(new User("Юрий", "Белозеров"));

        taskRepository.save(new Task("Разработка фонового процесса №6",
                "Разработать функционал фонового процесса репликации заявления",
                "Разработка нового функционала",
                sirius,
                yuri));

        final var selfCollection = projectRepository.save(new Project("Самоинкасация юридических лиц"));

        final var dmitrii = userRepository.save(new User("Дмитрий", "Елагин"));

        taskRepository.save(new Task(0,
                "Логирование ФП",
                "Добавить в логи фоновых процессов id сущностей",
                "Разработка нового функционала",
                selfCollection,
                dmitrii,
                Task.TaskPriority.LOW));
    }
}
