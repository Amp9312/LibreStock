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
import java.util.Map;

public class DeleteUserController {
    Stage stage;
    @FXML private Button deleteuserreturn_button;
    @FXML private Button deleteusersubmit_button;
    @FXML private MenuItem deleteuserquit_menu;
    @FXML private MenuItem deleteuseraboutlibrestock_menu;

    @FXML private TextField deleteuseruserID_textfield;
    @FXML private TextField deleteuseradminuser_textfield;
    @FXML private TextField deleteuseradminpass_textfield;

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
    private void deleteuserreturnButtonClick() throws IOException{
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)deleteuserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteusersubmitButtonClick() {
        String idText      = deleteuseruserID_textfield.getText();
        String adminUser   = deleteuseradminuser_textfield.getText();
        String adminPass   = deleteuseradminpass_textfield.getText();

        if (idText == null || idText.isBlank() ||
            adminUser == null || adminUser.isBlank() ||
            adminPass == null || adminPass.isBlank()) {

            showError("User ID and admin credentials are required.");
        return;
    }

    int userId;
    try {
        userId = Integer.parseInt(idText);
    } catch (NumberFormatException e) {
        showError("User ID must be a number.");
        return;
    }

    try {
        DbAccess db = new DbAccess();

        // Check admin credentials
        Map<String, Object> adminRow = db.findUserByCredentials(adminUser, adminPass);
        if (adminRow == null || !"AD".equalsIgnoreCase((String) adminRow.get("role"))) {
            showError("Invalid admin username/password or not an admin.");
            return;
        }

        // Check user exists
        Map<String, Object> userRow = db.getRowById(userId, "users");
        if (userRow == null) {
            showError("No user found with ID " + userId);
            return;
        }

        String sql = "DELETE FROM users WHERE userId = ?";
        db.runQuery(sql, userId);

        showInfo("User deleted successfully!");

    } catch (SQLException e) {
        e.printStackTrace();
        showError("Database error: " + e.getMessage());
    }
}


        // Menu Option UI Methods
        @FXML
    public void quitDeleteUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openDeleteUserAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)deleteuserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}