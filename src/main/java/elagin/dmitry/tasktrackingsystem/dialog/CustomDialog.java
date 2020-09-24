package elagin.dmitry.tasktrackingsystem.dialog;

import elagin.dmitry.tasktrackingsystem.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomDialog<T> {

    private final int width, height;
    private final String fxmlName;
    private final String title;
    private final T model;

    public CustomDialog(T model, int width, int height, String fxmlName, String title) {
        this.width = width;
        this.height = height;
        this.fxmlName = fxmlName;
        this.title = title;
        this.model=model;
    }

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
