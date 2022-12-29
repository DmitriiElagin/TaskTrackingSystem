package elagin.dmitrii.front.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ProjectDTO {
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

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDTO that = (ProjectDTO) o;
        return id == that.id && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
