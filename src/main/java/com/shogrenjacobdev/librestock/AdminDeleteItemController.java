package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminDeleteItemController {
    Stage stage;
    @FXML private Button admindeleteitemreturn_button;
    @FXML private Button admindeleteitemdelete_button; // saved for later even if not used currently
    @FXML private Button admindeleteitemsearch_button; // saved for later even if not used currently
    @FXML private MenuItem admindeleteitemquit_menu;
    @FXML private MenuItem admindeleteitemaboutlibrestock_menu;


    @FXML
    private void admindelitemreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindeleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void admindelitemsearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy submit logic here later (some sort of search method for items in the db) */
    }
        @FXML
    private void admindelitemsubmitButtonClick() throws IOException{
        System.out.println("deleting for data fr fr");
        /* put craaaaazy delete logic here later (some sort of deletion method for items in the db) */
    }

        @FXML
    public void quitAdminDeleteItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAdminDeleteItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)admindeleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}