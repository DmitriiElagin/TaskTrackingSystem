package elagin.dmitry.tasktrackingsystem.controller;

import elagin.dmitry.tasktrackingsystem.model.Repository;
import elagin.dmitry.tasktrackingsystem.model.User;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class UserController implements DialogController<User> {



    @FXML
    private TextField tfFName, tfLName;



    @FXML
    private Button btnSave, btnCancel;

    private User user;

    private  boolean result;

  @FXML
  public void initialize() {
      InvalidationListener listener= observable ->
              btnSave.setDisable((tfFName.getText().isEmpty()||tfLName.getText().isEmpty()));
      tfFName.textProperty().addListener(listener);
      tfLName.textProperty().addListener(listener);

  }

  public void setUser(User user) {
        this.user = user;
        tfFName.setText(user.getFirstName());
        tfLName.setText(user.getLastName());
    }

    public boolean getResult() {
        return result;
    }

    @FXML
    @Override
    public void onSaveAction() {
        user.setFirstName(tfFName.getText());
        user.setLastName(tfLName.getText());
        Repository.getInstance().saveUser(user);
        setResult(true);
        closeWindow();
    }

    @FXML
    @Override
    public void onCancelAction() {
        setResult(false);
        closeWindow();
    }

    public void setResult(boolean result) {
        this.result = result;
    }




    @Override
    public Button getOKButton() {
        return btnSave;
    }

    @Override
    public Button getCancelButton() {
        return null;
    }


    @Override
    public void setModel(User model) {
        setUser(model);
    }
}
