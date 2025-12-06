package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import java.io.IOException;

public class HelloController {
    Stage stage;
    @FXML private Button login_button;
    @FXML private Button loginfts_button;
    @FXML private MenuItem loginquit_menu;
    @FXML private MenuItem loginAboutLibre_menu;
    
    
    @FXML private PasswordField loginpassword_textfield;
    @FXML private TextField loginusername_textfield;

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
  
    @FXML
    private void onLoginButtonClick() {
        String username = loginusername_textfield.getText();
        String password = loginpassword_textfield.getText();

        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            showError("Please enter both username and password.");
            return;
        }

        try {
            DbAccess db = new DbAccess();
            Map<String, Object> userRow = db.findUserByCredentials(username, password);

            if (userRow == null) {

                showError("Invalid username or password.");
                return;
            }
            // 1) Extract user info from the row
        //    Column names: userId, firstName, lastName, role, username
            Number userIdNum = (Number) userRow.get("userId");
            int userId = (userIdNum != null) ? userIdNum.intValue() : -1;

            String firstName = (String) userRow.get("firstName");
            String lastName  = (String) userRow.get("lastName");
            String role      = (String) userRow.get("role");
            String dbUsername = (String) userRow.get("username");

            // 2) Is this user an admin?
            boolean isAdmin = "AD".equalsIgnoreCase(role);

            // 3) Remember the logged-in user globally
            CurrentUser.setUser(
                userId,
                dbUsername != null ? dbUsername : username,
                firstName,
                lastName,
                isAdmin
        );

            // 4) Decide which dashboard to load based on role
        FXMLLoader loader;
        if (isAdmin) {
            loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        }
        Parent root = loader.load();
        Stage stage = (Stage) login_button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading dashboard: " + e.getMessage());
        }
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
    public void initialize() {
        // Check number of users in DB; if any users exist, disable the first-time-setup button.
        try {
            DbAccess db = new DbAccess();
            List<Map<String, Object>> rows = db.runQuery("SELECT COUNT(*) AS cnt FROM users");
            int count = 0;
            if (rows != null && !rows.isEmpty()) {
                Object val = rows.get(0).get("cnt");
                if (val instanceof Number) {
                    count = ((Number) val).intValue();
                } else if (val != null) {
                    try { count = Integer.parseInt(val.toString()); } catch (NumberFormatException ignored) {}
                }
            }
            // If there is at least one user, disable first-time setup button.
            loginfts_button.setDisable(count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            // On DB error, be conservative and disable the first-time setup button.
            loginfts_button.setDisable(true);
        }
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