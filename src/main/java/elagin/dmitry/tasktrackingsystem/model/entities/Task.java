package elagin.dmitry.tasktrackingsystem.model.entities;

import javax.persistence.*;
import java.io.Serializable;

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
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(cascade ={CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User responsible;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    @Column(nullable = false)
    private String description;






    public Task () {

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
}
