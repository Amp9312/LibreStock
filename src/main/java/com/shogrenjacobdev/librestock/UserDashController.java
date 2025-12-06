package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Label;

public class UserDashController {
    Stage stage;
    @FXML private Button userdashlogout_button;
    @FXML private Button userdashedititem_button;
    @FXML private Button userdashnewitem_button;
    @FXML private MenuItem userdashquit_menu;
    @FXML private MenuItem userdashaboutlibrestock_menu;
    @FXML private Label userdashusername_label;


    @FXML
    private void newItemButtonClick() throws IOException{
        System.out.println("Making new item...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newitem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)userdashnewitem_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    
    @FXML
    public void initialize() {
        String firstName = CurrentUser.getFirstName();
        if (firstName != null && !firstName.isEmpty()) {
            userdashusername_label.setText(firstName);
        } else {
            userdashusername_label.setText("");
        }
    }


    @FXML
    private void editItemButtonClick() throws IOException{
        System.out.println("Editing item...");
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("edititem-scene.fxml"));
        Parent root = loader2.load();

        Stage stage = (Stage)userdashedititem_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onLogoutButtonClick() throws IOException{
        System.out.println("User Logged Out...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)userdashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void quitUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openUserAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)userdashnewitem_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}

