package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AddCollectionController {
    Stage stage;
    @FXML private Button addcollectionreturn_button;
    @FXML private Button addcollectionsubmit_button; // saved for later even if not used currently

    @FXML
    private void addcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)addcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addcolsubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /* put craaaaazy submit logic here later (some sort of add method for collections in the db) */
    }
}