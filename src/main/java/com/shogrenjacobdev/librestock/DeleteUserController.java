package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteUserController {
    Stage stage;
    @FXML private Button deleteuserreturn_button;
    @FXML private Button deleteusersubmit_button; // saved for later even if not used currently
    @FXML private MenuItem deleteuserquit_menu;
    @FXML private MenuItem deleteuseraboutlibrestock_menu;

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

        // Menu Option UI Methods
        @FXML
    public void quitDeleteUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openDeleteUserAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)deleteuserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}