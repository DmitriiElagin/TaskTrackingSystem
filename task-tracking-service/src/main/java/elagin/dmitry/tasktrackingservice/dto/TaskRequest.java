package elagin.dmitry.tasktrackingservice.dto;

import elagin.dmitry.tasktrackingservice.entities.Task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TaskRequest implements Serializable {
    @Min(0)
    private int id;

    @NotBlank
    private String theme;

    @NotBlank
    @Size(max = 64)
    private String type;

    @Min(1)
    private int projectId;

    @Min(1)
    private int responsibleId;
    @NotNull
    private Task.TaskPriority priority;

    @NotBlank
    private String description;

    public TaskRequest() {
        priority = Task.TaskPriority.MEDIUM;
    }

    public TaskRequest(int id, String theme,
                       String description,
                       String type,
                       int projectId,
                       int responsibleId,
                       Task.TaskPriority priority) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.projectId = projectId;
        this.responsibleId = responsibleId;
        this.priority = priority;
        this.description = description;
    }

    public TaskRequest(String theme, String description, String type, int projectId, int responsibleId) {
        this(0, theme, description, type, projectId, responsibleId, Task.TaskPriority.MEDIUM);
    }

    public Task toTask() {
        return new Task(id, theme, description, type, null, null, priority);
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Task.TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(Task.TaskPriority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
