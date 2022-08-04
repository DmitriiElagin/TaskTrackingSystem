package elagin.dmitry.tasktrackingsystem.dto;

import elagin.dmitry.tasktrackingsystem.entities.Project;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ProjectDTO implements Serializable {
    @Min(0)
    private int id;
    @NotBlank
    private String title;

    public ProjectDTO() {
    }

    public ProjectDTO(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public ProjectDTO(String title) {
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
        return "ProjectDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
