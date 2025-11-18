package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminNewItemController {
    Stage stage;
    @FXML private Button adminnewitemreturn_button;
    @FXML private Button adminnewitemsubmit_button;

    @FXML
    private void adminnewitemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminnewitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adminnewitemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /* put craaaaazy submit logic here later (some sort of update method for items in the db) */
    }
}