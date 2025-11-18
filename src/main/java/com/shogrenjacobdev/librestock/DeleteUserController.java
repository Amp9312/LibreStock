package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteUserController {
    Stage stage;
    @FXML private Button deleteuserreturn_button;
    @FXML private Button deleteusersubmit_button; // saved for later even if not used currently

    @FXML
    private void deleteuserreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)deleteuserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteusersubmitButtonClick() throws IOException{
        System.out.println("deleting account data fr fr");
        /* put craaaaazy delete logic here later (some sort of delete user method) */
    }
}