package elagin.dmitry.tasktrackingsystem.model;

import java.io.Serializable;

public class Task implements Serializable {
    static final long serialVersionUID = 7687240393807798354L;

    private int id;
    private String theme;
    private String type;
    private Project project;
    private User responsible;
    private TaskPriority priority;
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
