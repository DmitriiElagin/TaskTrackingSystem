package elagin.dmitry.tasktrackingsystem.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="project")
public class Project implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title",nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "project",
            cascade = {CascadeType.ALL}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Project() {

    }

    public Project(int id, String title) {
        this.title = title;
        this.id=id;
        tasks=new ArrayList<>();
    }

    public Project(String title) {
      this(0,title);
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


}
