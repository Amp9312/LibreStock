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
import java.util.Map;


public class EditUserController {
    Stage stage;
    @FXML private Button edituserreturn_button;
    @FXML private Button editusersubmit_button;
    @FXML private Button editusersearch_button;
    @FXML private MenuItem edituserquit_menu;
    @FXML private MenuItem edituseraboutlibrestock_menu;

    @FXML private TextField edituseruserID_textfield;
    @FXML private TextField edituserusername_textfield;
    @FXML private TextField edituserpassword_textfield;
    @FXML private TextField edituserconfirmpassword_textfield;
    @FXML private TextField edituserfirstname_textfield;
    @FXML private TextField edituserlastname_textfield;
    @FXML private CheckBox editusermakeadmin_check;

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

    //Buttton UI Methods
    @FXML
    private void edituserreturnButtonClick() throws IOException{
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)edituserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editusersubmitButtonClick() {
        String idText     = edituseruserID_textfield.getText();
        String username   = edituserusername_textfield.getText();
        String password   = edituserpassword_textfield.getText();
        String confirm    = edituserconfirmpassword_textfield.getText();
        String firstName  = edituserfirstname_textfield.getText();
        String lastName   = edituserlastname_textfield.getText();
        boolean makeAdmin = editusermakeadmin_check.isSelected();
    if (idText == null || idText.isBlank()) {
        if(username == null || username.isBlank()){
            showError("User ID is required.");
        return;}
        else if(!username.isBlank()){
            // If no ID is provided but a username is, perform a lookup by username to get the ID
        }
    }

    int userId;
    try {
        userId = Integer.parseInt(idText);
    } catch (NumberFormatException e) {
        showError("User ID must be a number.");
        return;
    }

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

    String role = makeAdmin ? "AD" : "US";

    try {
        DbAccess db = new DbAccess();

        // Check user exists
        Map<String, Object> row = db.getRowById(userId, "users");
        if (row == null) {
            showError("No user found with ID " + userId);
            return;
        }

        String sql = "UPDATE users SET username = ?, password = ?, " +
                     "firstName = ?, lastName = ?, role = ? WHERE userId = ?";

        db.runQuery(sql, username, password, firstName, lastName, role, userId);

        showInfo("User updated successfully!");

    } catch (SQLException e) {
        e.printStackTrace();
        showError("Database error: " + e.getMessage());
    }
}


    @FXML
    private void editusersearchButtonClick() {
        // Implementation for searching users
        //System.out.println("Searching for user and applying updates if user exists...");

        String idText = edituseruserID_textfield.getText();
        String username = edituserusername_textfield.getText();
        if (idText == null || idText.isBlank()) {
           if(username == null || username.isBlank()){
            showError("User ID is required.");
        return;}
        else if(!username.isBlank()){
            // If no ID is provided but a username is, perform a lookup by username to get the ID
            try {
                DbAccess db = new DbAccess();
                java.util.List<java.util.Map<String, Object>> rows = db.runQuery("SELECT userId FROM users WHERE username = ?", username.trim());
                if (rows == null || rows.isEmpty() || rows.get(0).get("userId") == null) {
                    showError("No user found with username: " + username);
                    return;
                }
                Object idObj = rows.get(0).get("userId");
                idText = idObj.toString();
                edituseruserID_textfield.setText(idText);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                showError("Database error: " + sqle.getMessage());
                return;
            }
        }
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

            // Look up existing user row
            Map<String, Object> row = db.getRowById(userId, "users");
            if (row == null) {
                showError("User ID does not match any record. Please try again.");
                return;
            }

            // Use entered values when present; otherwise preserve DB values
            String dbUsername = row.get("username") == null ? "" : row.get("username").toString();
            String dbPassword = row.get("password") == null ? "" : row.get("password").toString();
            String dbFirst   = row.get("firstName") == null ? "" : row.get("firstName").toString();
            String dbLast    = row.get("lastName") == null ? "" : row.get("lastName").toString();

            //String username = edituserusername_textfield.getText();
            String password = edituserpassword_textfield.getText();
            String confirm  = edituserconfirmpassword_textfield.getText();
            String firstName= edituserfirstname_textfield.getText();
            String lastName = edituserlastname_textfield.getText();
            boolean makeAdmin = editusermakeadmin_check.isSelected();

            if (username == null || username.isBlank()) username = dbUsername;

            // If both password and confirm are blank, preserve existing password.
            if ((password == null || password.isBlank()) && (confirm == null || confirm.isBlank())) {
                password = dbPassword;
            } else {
                if (password == null) password = "";
                if (!password.equals(confirm)) {
                    showError("Password and Confirm Password do not match.");
                    return;
                }
            }

            if (firstName == null || firstName.isBlank()) firstName = dbFirst;
            if (lastName == null || lastName.isBlank()) lastName = dbLast;

            String role = makeAdmin ? "AD" : "US";

            String sql = "UPDATE users SET username = ?, password = ?, firstName = ?, lastName = ?, role = ? WHERE userId = ?";

            db.runQuery(sql, username, password, firstName, lastName, role, userId);

            // Populate fields with the (now updated) values and lock the ID field
            edituserusername_textfield.setText(username);
            edituserpassword_textfield.setText("");
            edituserconfirmpassword_textfield.setText("");
            edituserfirstname_textfield.setText(firstName);
            edituserlastname_textfield.setText(lastName);
            editusermakeadmin_check.setSelected("AD".equals(role));
            edituseruserID_textfield.setDisable(true);

            

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        }
    }

    // Menu Option UI Methods
        @FXML
    public void quitEditUserMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openEditUserAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)edituserreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}