package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteItemController {
    Stage stage;
    @FXML private Button deleteitemreturn_button;
    @FXML private Button deleteitemdelete_button; // saved for later even if not used currently
    @FXML private Button deleteitemsearch_button; // saved for later even if not used currently
    @FXML private MenuItem deleteitemquit_menu;
    @FXML private MenuItem deleteitemaboutlibrestock_menu;


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

    @FXML
    public void quitDeleteItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openDeleteItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)deleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}