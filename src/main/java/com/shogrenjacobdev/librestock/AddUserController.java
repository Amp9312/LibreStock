package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class AddUserController {
    Stage stage;
    @FXML private Button adduserreturn_button;
    @FXML private Button addusersubmit_button; // saved for later even if not used currently
    @FXML private MenuItem adduserquit_menu;
    @FXML private MenuItem adduseraboutlibrestock_menu;

    @FXML
    private void adduserreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adduserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addusersubmitButtonClick() throws IOException{
        System.out.println("submitting account data fr fr");
        /* put craaaaazy submit logic here later (some sort of add user method) */
    }

    @FXML
    public void quitAddUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAddUserAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)adduserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}