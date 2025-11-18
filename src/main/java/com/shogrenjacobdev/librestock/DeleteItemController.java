package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteItemController {
    Stage stage;
    @FXML private Button deleteitemreturn_button;
    @FXML private Button deleteitemdelete_button; // saved for later even if not used currently
    @FXML private Button deleteitemsearch_button; // saved for later even if not used currently


    @FXML
    private void delitemreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)deleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void delitemsearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy submit logic here later (some sort of search method for items in the db) */
    }
        @FXML
    private void delitemsubmitButtonClick() throws IOException{
        System.out.println("deleting for data fr fr");
        /* put craaaaazy delete logic here later (some sort of deletion method for items in the db) */
    }
}