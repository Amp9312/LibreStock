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

public class AddCollectionController {
    Stage stage;
    @FXML private Button addcollectionreturn_button;
    @FXML private Button addcollectionsubmit_button; // saved for later even if not used currently
    @FXML private MenuItem addcollectionquit_menu;
    @FXML private MenuItem addcollectionaboutlibrestock_menu;
    @FXML private TextField addcollectioncollectionname_textfield;
    @FXML private TextField addcollectioncollectiontype_textfield;
    @FXML private TextField addcollectionmaxsize_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LibreStock Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearAddCollectionFields() throws IOException{
        addcollectioncollectionname_textfield.clear();
        addcollectioncollectiontype_textfield.clear();
        addcollectionmaxsize_textfield.clear();
    }

    @FXML
    private void addcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)addcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addcolsubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /* Process should work as follows:
        1.) Capture input data
        2.) Formulate query using this input data
        3.) Create a new entry in collectionsDB table
        4.) Notify user of success */
        String enteredCollectionName = addcollectioncollectionname_textfield.getText();
        String enteredCollectionType = addcollectioncollectiontype_textfield.getText();
        String enteredMaxSize = addcollectionmaxsize_textfield.getText();

        // TODO: Formulate query based on input

        // TODO: Create new entry in collections DB table

        // showMessage("Collection Made! Press Enter to Continue");
        // clearAddCollectionFields();
    }

    @FXML
    public void quitAddCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAddCollectionAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)addcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}