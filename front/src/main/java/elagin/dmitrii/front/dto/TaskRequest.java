package elagin.dmitrii.front.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TaskRequest {
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
    private TaskPriority priority;

    @NotBlank
    private String description;

    public TaskRequest() {
        priority = TaskPriority.MEDIUM;
    }

    public TaskRequest(int id, String theme,
                       String description,
                       String type,
                       int projectId,
                       int responsibleId,
                       TaskPriority priority) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.projectId = projectId;
        this.responsibleId = responsibleId;
        this.priority = priority;
        this.description = description;
    }

    public TaskRequest(String theme, String description, String type, int projectId, int responsibleId) {
        this(0, theme, description, type, projectId, responsibleId, TaskPriority.MEDIUM);
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
        return "TaskDTO{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", type='" + type + '\'' +
                ", projectId=" + projectId +
                ", responsibleId=" + responsibleId +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRequest taskRequest = (TaskRequest) o;
        return id == taskRequest.id && projectId == taskRequest.projectId && responsibleId == taskRequest.responsibleId && theme.equals(taskRequest.theme) && type.equals(taskRequest.type) && priority == taskRequest.priority && Objects.equals(description, taskRequest.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, type, projectId, responsibleId, priority, description);
    }
}
