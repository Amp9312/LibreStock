package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class EditItemController {
    Stage stage;
    @FXML private Button editcollectionreturn_button;
    @FXML private Button editcollectionsubmit_button;
    @FXML private Button edititemsearch_button; // saved for later even if not used currently

    @FXML
    private void edititemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)editcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ititemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /* put craaaaazy submit logic here later (some sort of update method for items in the db) */
    }

        @FXML
    private void edititemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy search logic here later (some sort of search method for items in the db) */
    }
}