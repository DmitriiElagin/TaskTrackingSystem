package elagin.dmitry.tasktrackingsystem.controller;

import elagin.dmitry.tasktrackingsystem.model.Project;
import elagin.dmitry.tasktrackingsystem.model.Repository;
import elagin.dmitry.tasktrackingsystem.model.Task;
import elagin.dmitry.tasktrackingsystem.model.User;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class TaskController implements DialogController<Task> {

    private Task task;

    @FXML
    TextField tfTheme, tfType;

    @FXML
    ComboBox<User> cbResp;
    @FXML
    ComboBox<Project> cbProject;
    @FXML
    ComboBox<Task.TaskPriority> cbPriority;

    @FXML
    TextArea taDescription;

    @FXML
    Button btnCancel, btnSave;



    private ObservableList<Project> projects;
    private ObservableList<User> users;
    private boolean result;



    public void setResult(boolean result) {
        this.result = result;
    }

    @FXML
    public void initialize() {
        projects= Repository.getInstance().findAllProjects();
        users=Repository.getInstance().findAllUsers();
        initUI();
    }

    private void initUI() {
        InvalidationListener listener=observable ->
                btnSave.setDisable(tfTheme.getText().isEmpty()
                        ||tfType.getText().isEmpty()
                        ||taDescription.getText().isEmpty());


        tfType.textProperty().addListener(listener);
        tfTheme.textProperty().addListener(listener);
        taDescription.textProperty().addListener(listener);

        cbProject.setItems(projects);
        cbProject.getSelectionModel().selectFirst();
        cbResp.setItems(users);
        cbResp.getSelectionModel().selectFirst();
        cbPriority.setItems(FXCollections.observableArrayList(Task.TaskPriority.LOW, Task.TaskPriority.MEDIUM, Task.TaskPriority.HIGH));
        cbPriority.getSelectionModel().selectFirst();
    }

    @FXML
    public void onSaveAction() {

        task.setType(tfType.getText());
        task.setPriority(cbPriority.getValue());
        task.setTheme(tfTheme.getText());
        task.setDescription(taDescription.getText());
        task.setResponsible(cbResp.getValue());
        task.setProject(cbProject.getValue());

        Repository.getInstance().saveTask(task);
        setResult(true);
        closeWindow();
    }

    @FXML
    @Override
    public void onCancelAction() {
        setResult(false);
        closeWindow();
    }

    @Override
    public Button getOKButton() {
        return btnSave ;
    }

    @Override
    public Button getCancelButton() {
        return btnCancel;
    }


    @Override
    public void setModel(Task model) {
        this.task=model;
        tfTheme.setText(task.getTheme());
        tfType.setText(task.getType());
        taDescription.setText(task.getDescription());
        cbPriority.getSelectionModel().select(task.getPriority());
        if(task.getId()!=0) {
            cbResp.getSelectionModel().select(task.getResponsible());
            cbProject.getSelectionModel().select(task.getProject());
        }


    }

    @Override
    public boolean getResult() {

        return result;
    }


}
