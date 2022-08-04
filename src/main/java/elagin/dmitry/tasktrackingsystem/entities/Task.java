package elagin.dmitry.tasktrackingsystem.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task implements Serializable {
    static final long serialVersionUID = 7687240393807798354L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String theme;

    @Column(nullable = false)
    private String type;
    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User responsible;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    @Column(nullable = false)
    private String description;

    public Task() {
        priority = TaskPriority.MEDIUM;
    }

    public Task(int id, String theme, String description, String type, Project project, User responsible, TaskPriority priority) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.project = project;
        this.responsible = responsible;
        this.priority = priority;
        this.description = description;
    }

    public Task(String theme, String description, String type, Project project, User responsible) {
        this(0, theme, description, type, project, responsible, TaskPriority.MEDIUM);
    }

    public enum TaskPriority {
        LOW,
        MEDIUM,
        HIGH
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", type='" + type + '\'' +
                ", project=" + project.getTitle() +
                ", responsible=" + responsible.getLastName() +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && theme.equals(task.theme) && type.equals(task.type) && project.equals(task.project) && responsible.equals(task.responsible) && priority == task.priority && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, type, project, responsible, priority, description);
    }
}
