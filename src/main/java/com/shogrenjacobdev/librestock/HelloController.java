package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    Stage stage;
    @FXML private Button login_button;
    @FXML private MenuItem loginquit_menu;
    @FXML private MenuItem loginAboutLibre_menu;

    @FXML
    private void onLoginButtonClick() throws IOException{
        System.out.println("User Logged In...");
        //Uncomment if you want login to go to standard user view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));

        //If checking standard user view, make sure to comment the next line out so there isn't a conflict
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)login_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void onFirstTimeButtonClick() throws IOException{
        System.out.println("First Time User Option Clicked...");
        System.out.println("User Logged In...");
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("firsttimesetup-scene.fxml"));
        Parent root = loader2.load();

        Stage stage = (Stage)login_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onLogoutButtonClick() throws IOException{
        System.out.println("User Logged Out...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)login_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void quitMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)login_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}