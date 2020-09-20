package elagin.dmitry.tasktrackingsystem.dialog;

import elagin.dmitry.tasktrackingsystem.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Dmitry Elagin
 * Сreates and displays a custom dialog using any data model class
 * @param <T> - class of object to be used as the dialog data model
 */
public class CustomDialog<T> {

    private final int width, height;
    private final String fxmlName;
    private final String title;
    private final T model;


    /**
     * Creates a new dialog object with the specified window title, width, height,
     * FXML file name, and object to be used as the data model.
     * @param width  dialog width
     * @param height dialog height
     * @param fxmlName name of FXML file that defines the user interface of a dialog
     * @param title dialog title
     * @param model an object that will be used as a data model and passed to controller
     */

    public CustomDialog(T model, int width, int height, String fxmlName, String title) {
        this.width = width;
        this.height = height;
        this.fxmlName = fxmlName;
        this.title = title;
        this.model=model;
    }

    /**
     * Displays a dialog box, waits for it to close and returns the result of its execution
     * @return returns true if the dialog was closed with a positive action, false if canceled
     */
    public boolean show() {

        try {
            Stage stage=new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            DialogController<T> controller = loader.getController();
            controller.setModel(model);

            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
            return controller.getResult();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }




}
