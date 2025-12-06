package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ImportInventoryController {
    Stage stage;
    @FXML private Button importinventoryreturn_button;
    @FXML private Button importtinventorysubmit_button;
    @FXML private MenuItem importinventoryquit_menu;
    @FXML private MenuItem importinventoryaboutlibrestock_menu;
    @FXML private TextField importinventoryfilename_textfield;
    @FXML private TextField importinventoryusername_textfield;
    @FXML private TextField importinventorypassword_textfield;

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import Inventory");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Import Inventory Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void importreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)importinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void importsubmitButtonClick() throws IOException{

        String importFileName = importinventoryfilename_textfield.getText();
        String importusername = importinventoryusername_textfield.getText();
        String importpass = importinventorypassword_textfield.getText();

        // TODO: Check creds, search root folder for backup file, if file match then drop table and populate using import file

        try {
            DbAccess Db = new DbAccess();
            Inventory Inv = new Inventory(Db);

            List<Map<String, Object>> usersWithUsername = Db.runQuery("SELECT * FROM users WHERE username = ?", importusername);

            if (usersWithUsername == null) {
                showError("Admin with given username does not exist.");
                throw new IOException("Admin with given username does not exist.");
            }

            Map<String, Object> user = usersWithUsername.getFirst();

            if (user.get("password").equals(importpass)) {
                // Import Inventory
                System.out.println("Successfully Imported inventory");
                showInfo("Inventory successfully Imported!");
            }
            else {
                showError("Password Incorrect!");
                throw new IOException("Admin with given password does not match.");
            }
        }
        catch(IOException k) {
            System.err.println(k.getMessage());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

        @FXML
    public void quitImportInventoryMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openImportInventoryAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)importinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}