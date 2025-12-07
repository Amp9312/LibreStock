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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import java.sql.SQLException;


public class AddUserController {
    Stage stage;
    @FXML private Button adduserreturn_button;
    @FXML private Button addusersubmit_button;
    @FXML private MenuItem adduserquit_menu;
    @FXML private MenuItem adduseraboutlibrestock_menu;

    @FXML private TextField adduserusername_textfield;
    @FXML private TextField adduserpassword_textfield;
    @FXML private TextField adduserconfirmpass_textfield;
    @FXML private TextField adduserfirstname_textfield;
    @FXML private TextField adduserlastname_textfield;
    @FXML private CheckBox addusermakeadmin_check;


    private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void showInfo(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

    @FXML
    private void adduserreturnButtonClick() throws IOException{
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adduserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addusersubmitButtonClick() {
        String username   = adduserusername_textfield.getText();
        String password   = adduserpassword_textfield.getText();
        String confirm    = adduserconfirmpass_textfield.getText();
        String firstName  = adduserfirstname_textfield.getText();
        String lastName   = adduserlastname_textfield.getText();
        boolean makeAdmin = addusermakeadmin_check.isSelected();

        if (username == null || username.isBlank() ||
            password == null || password.isBlank() ||
            confirm == null || confirm.isBlank() ||
            firstName == null || firstName.isBlank() ||
            lastName == null || lastName.isBlank()) {

            showError("All fields are required.");
            return;
        }

    if (!password.equals(confirm)) {
        showError("Password and Confirm Password do not match.");
        return;
    }

    String role = makeAdmin ? "AD" : "US";   // "AD" = admin, "US" = normal user

    try {
        DbAccess db = new DbAccess();
        int newUserId = db.getNextUserId();

        boolean ok = db.insertUser(
                newUserId,
                newUserId,     // accountId – simplest thing for now
                firstName,
                lastName,
                role,
                username,
                password
        );

        if (!ok) {
            showError("Failed to create user. See console for details.");
            return;
        }

        showInfo("User created successfully!");

    } catch (SQLException e) {
        e.printStackTrace();
        showError("Database error: " + e.getMessage());
    }
}


    @FXML
    public void quitAddUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAddUserAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)adduserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}