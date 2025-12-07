package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.InvalidKeyException;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ExportInventoryController {
    Stage stage;
    @FXML private Button exportinventoryreturn_button;
    @FXML private Button exportinventorysubmit_button;
    @FXML private MenuItem exportinventoryquit_menu;
    @FXML private MenuItem exportinventoryaboutlibrestock_menu;
    @FXML private TextField exportinventoryfilename_textfield;
    @FXML private TextField exportinventoryusername_textfield;
    @FXML private TextField exportinventorypassword_textfield;

    @FXML
    private void exportreturnButtonClick() throws IOException{
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)exportinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Inventory");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export Inventory Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void exportsubmitButtonClick() throws IOException{
        //System.out.println("exporting data fr fr");

        String fileName = exportinventoryfilename_textfield.getText();
        String adminuser = exportinventoryusername_textfield.getText();
        String adminpass = exportinventorypassword_textfield.getText();

        // TODO - Check creds, export csv or similar tabular file format with the file named as the file name

        try {
            DbAccess Db = new DbAccess();
            Inventory Inv = new Inventory(Db);

            List<Map<String, Object>> usersWithUsername = Db.runQuery("SELECT * FROM users WHERE username = ?", adminuser);

            if (usersWithUsername == null) {
                showError("Admin with given username does not exist.");
                throw new IOException("Admin with given username does not exist.");
            }

            Map<String, Object> user = usersWithUsername.getFirst();

            if (user.get("password").equals(adminpass)) {
                Inv.ExportInventory(fileName);
                System.out.println("Successfully exported inventory");
                showInfo("Inventory successfully exported!");
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
        /* put craaaaazy submit logic here later (some sort of export inventory method for collections in the db) */
    }

    @FXML
    public void quitExportInventoryMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openExportInventoryAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)exportinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}