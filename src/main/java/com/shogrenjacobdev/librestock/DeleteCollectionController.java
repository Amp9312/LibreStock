package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteCollectionController {
    Stage stage;
    @FXML private Button deletecollectionreturn_button;
    @FXML private Button deletecollectiondelete_button; // saved for later even if not used currently
    @FXML private Button deletecollectionsearch_button; // saved for later even if not used currently
    @FXML private MenuItem deletecollectionquit_menu;
    @FXML private MenuItem deletecollectionaboutlibrestock_menu;


    @FXML
    private void delcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)deletecollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void delcolsearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy submit logic here later (some sort of search method for collections in the db) */
    }
        @FXML
    private void delcolsubmitButtonClick() throws IOException{
        System.out.println("deleting for data fr fr");
        /* put craaaaazy delete logic here later (some sort of deletion method for collections in the db) */
    }

        @FXML
    public void quitDeleteCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openDeleteCollectionAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)deletecollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}