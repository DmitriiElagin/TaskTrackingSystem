package elagin.dmitry.tasktrackingsystem.controller;


import javafx.scene.control.Button;
import javafx.stage.Stage;


public interface DialogController<T> {

    void setModel(T model);

    boolean getResult();
    void onSaveAction();
    void onCancelAction();
    default void closeWindow() {
        Stage stage = (Stage) getOKButton().getScene().getWindow();
        stage.close();
    }
    Button getOKButton();
    Button getCancelButton();


}
