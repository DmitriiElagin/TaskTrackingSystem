package elagin.dmitrii.front.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;


public class TaskResponse {
    private int id;

    private String theme;

    private String type;

    private ProjectDTO project;

    private UserDTO responsible;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    @Column(nullable = false)
    private String description;

    public TaskResponse() {
        priority = TaskPriority.MEDIUM;
    }

    public TaskResponse(int id, String theme, String description, String type, ProjectDTO project, UserDTO responsible, TaskPriority priority) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.project = project;
        this.responsible = responsible;
        this.priority = priority;
        this.description = description;
    }

    public TaskResponse(String theme, String description, String type, ProjectDTO project, UserDTO responsible) {
        this(0, theme, description, type, project, responsible, TaskPriority.MEDIUM);
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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public UserDTO getResponsible() {
        return responsible;
    }

    public void setResponsible(UserDTO responsible) {
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
        TaskResponse that = (TaskResponse) o;
        return id == that.id && theme.equals(that.theme) && type.equals(that.type) && project.equals(that.project) && responsible.equals(that.responsible) && priority == that.priority && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, type, project, responsible, priority, description);
    }
}
