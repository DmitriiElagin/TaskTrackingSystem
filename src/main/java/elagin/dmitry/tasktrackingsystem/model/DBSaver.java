package elagin.dmitry.tasktrackingsystem.model;

import java.io.Serializable;

public class DBSaver implements Serializable {
    static final long serialVersionUID = 8757995545602358470L;

  private  Task[] tasks;
  private  Project[] projects;
  private  User[] users;
  private  int taskId, userId, projectId;

    public Task[] getTasks() {
        return tasks;
    }

    public Project[] getProjects() {
        return projects;
    }

    public User[] getUsers() {
        return users;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
