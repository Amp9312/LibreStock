package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminEditItemController {
    Stage stage;
    @FXML private Button adminedititemreturn_button;
    @FXML private Button adminedititemsubmit_button;
    @FXML private Button adminedititemsearch_button; // saved for later even if not used currently
    @FXML private MenuItem adminedititemquit_menu;
    @FXML private MenuItem adminedititemaboutlibrestock_menu;

    @FXML
    private void adminededititemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminedititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adminedititemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /* put craaaaazy submit logic here later (some sort of update method for items in the db) */
    }

    @FXML
    private void adminedititemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy search logic here later (some sort of search method for items in the db) */
    }

    @FXML
    public void quitAdminEditItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAdminEditItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminedititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}