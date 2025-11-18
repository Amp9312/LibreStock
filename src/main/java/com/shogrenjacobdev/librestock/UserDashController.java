package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class UserDashController {
    Stage stage;
    @FXML
    private Button userdashlogout_button;
    private Button userdashedititem_button;
    private Button userdashnewitem_button;

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
}