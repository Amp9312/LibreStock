package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class EditUserController {
    Stage stage;
    @FXML private Button edituserreturn_button;
    @FXML private Button editusersubmit_button; // saved for later even if not used currently

    @FXML
    private void edituserreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)edituserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editusersubmitButtonClick() throws IOException{
        System.out.println("submitting account data fr fr");
        /* put craaaaazy edit logic here later (some sort of edit user method) */
    }
}