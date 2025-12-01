package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class ImportInventoryController {
    Stage stage;
    @FXML private Button importinventoryreturn_button;
    @FXML private Button importtinventorysubmit_button; // saved for later even if not used currently
    @FXML private MenuItem importinventoryquit_menu;
    @FXML private MenuItem importinventoryaboutlibrestock_menu;
    @FXML private TextField importinventoryfilename_textfield;
    @FXML private TextField importinventoryusername_textfield;
    @FXML private TextField importinventorypassword_textfield;

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
        System.out.println("importing data fr fr");
        /* put craaaaazy submit logic here later (some sort of import inventory method for collections in the db) */

        String importFileName = importinventoryfilename_textfield.getText();
        String importusername = importinventoryusername_textfield.getText();
        String importpass = importinventorypassword_textfield.getText();

        // TODO: Check creds, search root folder for backup file, if file match then drop table and populate using import file
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