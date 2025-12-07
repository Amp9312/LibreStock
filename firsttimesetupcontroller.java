package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.sql.SQLException;


public class firsttimesetupcontroller {
    Stage stage;

    @FXML private Button ftsreturn_button;
    @FXML private MenuItem firsttimesetupquit_menu;
    @FXML private MenuItem firsttimesetupabout_menu;

    @FXML private TextField ftsusername_textfield;
    @FXML private TextField ftspassword_textfield;
    @FXML private TextField ftsconfirmpassword_textfield;
    @FXML private TextField ftsfirstname_textfield;
    @FXML private TextField ftslastname_textfield;


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("First Time Setup Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("First Time Setup");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void submitButtonClick() {
        String username = ftsusername_textfield.getText();
        String password = ftspassword_textfield.getText();
        String confirm = ftsconfirmpassword_textfield.getText();
        String firstName = ftsfirstname_textfield.getText();
        String lastName = ftslastname_textfield.getText();

        if (username == null || username.isBlank() ||
            password == null || password.isBlank() ||
            confirm == null || confirm.isBlank() ||
            firstName == null || firstName.isBlank() ||
            lastName == null || lastName.isBlank()) {

            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirm)) {
            showError("Password and confirm password do not match.");
            return;
        }

        try {
            DbAccess db = new DbAccess();
            int newUserId = db.getNextUserId();

            // For first user, just set accountId = userId and role = "AD" (Admin)
            boolean ok = db.insertUser(
                    newUserId,
                    newUserId,       // accountId
                    firstName,
                    lastName,
                    "AD",            // admin
                    username,
                    password
            );

            if (!ok) {
                showError("Failed to create user. See console for details.");
                return;
            }

            showInfo("Admin account created! You can now log in.");

            // Back to login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ftsreturn_button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error returning to login screen: " + e.getMessage());
        }
    }


    @FXML
    private void returnButtonClick() throws IOException{
        //System.out.println("Returning to main menu");
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
        //System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)ftsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}