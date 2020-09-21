package elagin.dmitry.tasktrackingsystem.controller;

import elagin.dmitry.tasktrackingsystem.model.Repository;
import elagin.dmitry.tasktrackingsystem.model.User;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Creates a save user dialog controller.
 * @author Dmitry Elagin
 * @see elagin.dmitry.tasktrackingsystem.controller.DialogController
 */
public class UserController extends DialogController<User> {


    @FXML
    private TextField tfFName, tfLName;


    @FXML
    private Button btnSave;


    @FXML
    public void initialize() {
        InvalidationListener listener = observable ->
                btnSave.setDisable((tfFName.getText().isEmpty() || tfLName.getText().isEmpty()));
        tfFName.textProperty().addListener(listener);
        tfLName.textProperty().addListener(listener);

    }


    public boolean getResult() {
        return result;
    }

    @FXML
    @Override
    public void onSaveAction() {
        model.setFirstName(tfFName.getText());
        model.setLastName(tfLName.getText());
        Repository.getInstance().saveUser(model);
        result=true;
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) btnSave.getScene().getWindow()).close();
    }

    @FXML
    @Override
    public void onCancelAction() {
        result=false;
        closeWindow();
    }


    @Override
    public void setModel(User model) {
        this.model=model;
        tfFName.setText(model.getFirstName());
        tfLName.setText(model.getLastName());

    }
}
