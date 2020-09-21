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
import javafx.stage.Stage;

/**
 * Controller class for the save task dialog
 * @author Dmitry Elagin
 * @see DialogController
 */
public class TaskController extends DialogController<Task> {



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
    Button btnSave;


    private ObservableList<Project> projects;
    private ObservableList<User> users;



    @FXML
    public void initialize() {
        projects = Repository.getInstance().findAllProjects();
        users = Repository.getInstance().findAllUsers();
        initUI();
    }

    private void initUI() {
        InvalidationListener listener = observable ->
                btnSave.setDisable(tfTheme.getText().isEmpty()
                        || tfType.getText().isEmpty()
                        || taDescription.getText().isEmpty());


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
    @Override
    public void onSaveAction() {

        model.setType(tfType.getText());
        model.setPriority(cbPriority.getValue());
        model.setTheme(tfTheme.getText());
        model.setDescription(taDescription.getText());
        model.setResponsible(cbResp.getValue());
        model.setProject(cbProject.getValue());

        Repository.getInstance().saveTask(model);
        result=true;
        closeWindow();
    }

    @FXML
    @Override
    public void onCancelAction() {
        result=false;
        closeWindow();

    }


    @Override
    public void setModel(Task model) {
        this.model = model;
        tfTheme.setText(model.getTheme());
        tfType.setText(model.getType());
        taDescription.setText(model.getDescription());
        cbPriority.getSelectionModel().select(model.getPriority());
        if (model.getId() != 0) {
            cbResp.getSelectionModel().select(model.getResponsible());
            cbProject.getSelectionModel().select(model.getProject());
        }


    }

    private void closeWindow() {
        ((Stage) btnSave.getScene().getWindow()).close();
    }


}
