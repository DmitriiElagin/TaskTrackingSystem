package elagin.dmitry.tasktrackingsystem.model;

import java.io.Serializable;


public class Project implements Serializable {

    private int id;

    private String title;


    public Project(int id,String title) {
        this.title = title;
        this.id=id;
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
