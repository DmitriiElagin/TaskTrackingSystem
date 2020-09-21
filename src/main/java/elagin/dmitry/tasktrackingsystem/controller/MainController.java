package elagin.dmitry.tasktrackingsystem.controller;


import elagin.dmitry.tasktrackingsystem.dialog.CustomDialog;
import elagin.dmitry.tasktrackingsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {


    @FXML
    private Button btnAdd, btnEdit, btnDel, btnFind;
    @FXML
    private Tab tabTasks, tabUsers, tabProjects;

    @FXML
    private TableView<Task> tableTasks;

    @FXML
    private TableView<Project> tableProjects;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<Task, String> colTheme, colType, colDescription;
    @FXML
    private TableColumn<Task, Integer> colId;
    @FXML
    private TableColumn<Task, Task.TaskPriority> colPriority;
    @FXML
    private TableColumn<Task, User> colResp;
    @FXML
    private TableColumn<Task, Project> colProject;
    @FXML
    private TableColumn<User, String> colFName, colLName;
    @FXML
    private TableColumn<User, Integer> colRespId;


    @FXML
    private TableColumn<Project, String> colTitle;

    @FXML
    private TableColumn<Project, Integer> colProjectId;

    @FXML
    private RadioButton rbAll, rbUser, rbProject;

    @FXML
    private ComboBox<Project> cmbProjects;
    @FXML
    private ComboBox<User> cmbUsers;

    private ObservableList<Project> projects;
    private ObservableList<User> users;
    private ObservableList<Task> tasks;
    private File file;


    @FXML
    public void initialize() {
        Repository.getInstance().setDatabase(DatabaseImpl.getInstance());
        projects = Repository.getInstance().findAllProjects();
        users = Repository.getInstance().findAllUsers();
        tasks = FXCollections.observableArrayList(new ArrayList<>());
        initUI();
    }


    private void initUI() {

        ToggleGroup toggleGroup = new ToggleGroup();
        rbAll.setToggleGroup(toggleGroup);
        rbProject.setToggleGroup(toggleGroup);
        rbUser.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(rbAll);
        btnAdd.setTooltip(new Tooltip("Add"));
        btnEdit.setTooltip(new Tooltip("Edit"));
        btnDel.setTooltip(new Tooltip("Delete"));

        tableTasks.setItems(tasks);
        cmbProjects.setItems(projects);
        tableProjects.setItems(projects);

        cmbUsers.setItems(users);
        tableUsers.setItems(users);

        colProjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        colRespId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTheme.setCellValueFactory(new PropertyValueFactory<>("theme"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colResp.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        colProject.setCellValueFactory(new PropertyValueFactory<>("project"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void onFindAction() {

        if (rbAll.isSelected()) {
            tasks.clear();
            tasks.addAll(Repository.getInstance().findAllTasks());
        }

        if (rbUser.isSelected()) {
            tasks.clear();
            if (cmbUsers.getValue() != null) {
                tasks.addAll(Repository.getInstance().findUserTasks(cmbUsers.getValue().getId()));
            }
        }

        if (rbProject.isSelected()) {
            if (cmbProjects.getValue() != null) {
                tasks.clear();
                tasks.addAll(Repository.getInstance().findProjectTasks(cmbProjects.getValue().getId()));
            }

        }
    }

    @FXML
    private void onAddAction() {
        if (tabProjects.isSelected()) {
            Project project = new Project("New project");
            showProjectDialog(project);
        }

        if (tabUsers.isSelected()) {
            User user = new User("FirstName", "LastName");
            showUserDialog(user);
        }

        if (tabTasks.isSelected()) {
            String title = "Adding a task";

            if (projects.isEmpty()) {
                showDialog(title, "No projects!", Alert.AlertType.WARNING, null);
            } else if (users.isEmpty()) {
                showDialog(title, "No users!", Alert.AlertType.WARNING, null);
            } else {
                Task task = new Task();
                task.setTheme("Task theme");
                task.setPriority(Task.TaskPriority.MEDIUM);
                task.setType("Task type");
                task.setDescription("Task description");
                showTaskDialog(task);
            }

        }
    }

    private void showDialog(String title, String header, Alert.AlertType type, String content) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showUserDialog(User user) {
        CustomDialog dialog = new CustomDialog(user, 280, 190, "/fxml/user.fxml", "Save User");
        if (dialog.show()) {
            cmbUsers.getSelectionModel().selectLast();
            onFindAction();
        }
    }

    private void showProjectDialog(Project project) {
        TextInputDialog dialog = new TextInputDialog(project.getTitle());
        dialog.setTitle("Save project");

        dialog.setHeaderText("Enter project title:");
        dialog.setContentText("Title:");
        Button btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField editor = dialog.getEditor();
        editor.textProperty().addListener(observable -> btnOk.setDisable(editor.getText().isEmpty()));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            project.setTitle(result.get());
            Repository.getInstance().saveProject(project);
            cmbProjects.getSelectionModel().selectLast();

            onFindAction();
        }

    }

    private void showTaskDialog(Task task) {
        CustomDialog dialog = new CustomDialog(task, 375, 400, "/fxml/task.fxml", "Save Task");
        if (dialog.show()) {
            onFindAction();
        }
    }


    @FXML
    private void onEditAction() {
        if (tabProjects.isSelected()) {
            Project project = tableProjects.getSelectionModel().getSelectedItem();
            if (project != null) {
                showProjectDialog(project);
            }

        }

        if (tabUsers.isSelected()) {
            User user = tableUsers.getSelectionModel().getSelectedItem();
            if (user != null) {
                showUserDialog(user);
            }
        }

        if (tabTasks.isSelected()) {
            Task task = tableTasks.getSelectionModel().getSelectedItem();
            if (task != null) {
                showTaskDialog(task);
            }
        }
    }

    @FXML
    private void onDeleteAction() {

        if (tabProjects.isSelected()) {
            Project project = tableProjects.getSelectionModel().getSelectedItem();
            if (project != null) {
                ObservableList<Task> tasks = Repository.getInstance().findProjectTasks(project.getId());

                if (!tasks.isEmpty()) {
                    showDialog("Delete project", "Project cannot be deleted", Alert.AlertType.WARNING,
                            "Can't delete project referenced by tasks!");
                } else {
                    if (showDeleteDialog()) {
                        Repository.getInstance().deleteProject(project);
                        cmbProjects.getSelectionModel().selectFirst();
                        onFindAction();
                    }
                }
            }
        }

        if (tabUsers.isSelected()) {
            User user = tableUsers.getSelectionModel().getSelectedItem();

            if (user != null) {
                ObservableList<Task> tasks = Repository.getInstance().findUserTasks(user.getId());
                if (!tasks.isEmpty()) {
                    showDialog("Delete user", "User cannot be deleted", Alert.AlertType.WARNING,
                            "Can't remove user referenced by tasks!");
                } else {
                    if (showDeleteDialog()) {
                        Repository.getInstance().deleteUser(user);
                        cmbUsers.getSelectionModel().selectFirst();
                        onFindAction();
                    }
                }
            }
        }

        if (tabTasks.isSelected()) {
            Task task = tableTasks.getSelectionModel().getSelectedItem();

            if (task != null) {
                if (showDeleteDialog()) {
                    Repository.getInstance().deleteTask(task);
                    onFindAction();
                }
            }
        }

    }

    private boolean showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete item");
        alert.setHeaderText("Are you sure want to remove this item?");
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.filter(type -> type == ButtonType.OK).isPresent();

    }




    @FXML
    public void onOpenAction() {

        File file = null;

        try {
            file = getFile(false);
            if (file != null) {
                Repository.getInstance().readDBFromFile(file);
                cmbUsers.getSelectionModel().selectFirst();
                cmbProjects.getSelectionModel().selectFirst();
                this.file = file;
                onFindAction();
            }

        } catch (Exception e) {
            showDialog("File read error", "Unable to open file due to I / O error", Alert.AlertType.ERROR, file.getPath());
            e.printStackTrace();
        }
    }


    private File getFile(boolean save) {
        FileChooser chooser = new FileChooser();
        File file;
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("dat", "*.dat"),
                new FileChooser.ExtensionFilter("All files", "*.*"));


        Stage window = (Stage) btnFind.getScene().getWindow();
        if (save) {
            chooser.setTitle("Save As");
            file = chooser.showSaveDialog(window);
        } else {
            chooser.setTitle("Open file");
            file = chooser.showOpenDialog(window);
        }

        return file;
    }

    @FXML
    public void onSaveAsAction(ActionEvent event) {
        File file = getFile(true);

        if (file != null) {
            this.file = file;
            onSaveAction(event);
        }
    }


    @FXML
    public void onSaveAction(ActionEvent event) {

        if (file != null) {
            try {
                Repository.getInstance().saveDBToFile(file);
            } catch (Exception e) {
                showDialog("File write error", "Unable to save file due to I / O error!", Alert.AlertType.ERROR, file.getPath());
                e.printStackTrace();
            }
        } else {
            onSaveAsAction(event);
        }
    }

    @FXML
    public void onExitAction() {
        System.exit(0);
    }
}
