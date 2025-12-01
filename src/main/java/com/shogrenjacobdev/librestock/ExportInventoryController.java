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
import java.sql.SQLException;

public class ExportInventoryController {
    Stage stage;
    @FXML private Button exportinventoryreturn_button;
    @FXML private Button exportinventorysubmit_button; // saved for later even if not used currently
    @FXML private MenuItem exportinventoryquit_menu;
    @FXML private MenuItem exportinventoryaboutlibrestock_menu;
    @FXML private TextField exportinventoryfilename_textfield;
    @FXML private TextField exportinventoryusername_textfield;
    @FXML private TextField exportinventorypassword_textfield;

    @FXML
    private void exportreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)exportinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void exportsubmitButtonClick() throws IOException{
        System.out.println("exporting data fr fr");

        String fileName = exportinventoryfilename_textfield.getText();
        String adminuser = exportinventoryusername_textfield.getText();
        String adminpass = exportinventorypassword_textfield.getText();

        // TODO - Check creds, export csv or similar tabular file format with the file named as the file name

        try {
            DbAccess Db = new DbAccess();
            Inventory Inv = new Inventory(Db);

            Inv.ExportInventory();
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
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)exportinventoryreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}