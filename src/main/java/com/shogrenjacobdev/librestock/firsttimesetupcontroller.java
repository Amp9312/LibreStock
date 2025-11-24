package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;


public class firsttimesetupcontroller {
    Stage stage;

    @FXML private Button ftsreturn_button;
    @FXML private MenuItem firsttimesetupquit_menu;
    @FXML private MenuItem firsttimesetupabout_menu;



    @FXML
    private void submitButtonClick() throws IOException{
        System.out.println("submitting!! Making the new DB entry!!!");
        /*Do cool DB stuff here */
    }
    @FXML
    private void returnButtonClick() throws IOException{
        System.out.println("Returning to main menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)ftsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void quitFirstTimeSetupMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openFirstTimeSetupAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)ftsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}