package elagin.dmitry.tasktrackingservice.dto;

import elagin.dmitry.tasktrackingservice.entities.Project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ProjectRequest implements Serializable {
    @Min(0)
    private int id;
    @NotBlank
    private String title;

    public ProjectRequest() {
    }

    public ProjectRequest(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public ProjectRequest(String title) {
        this(0, title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project toProject() {
        return new Project(id, title);
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
            "id=" + id +
            ", title='" + title + '\'' +
            '}';
    }
}
