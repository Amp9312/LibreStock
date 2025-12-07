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
    @FXML private Button addcollectionsubmit_button;
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
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)addcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addcolsubmitButtonClick() throws IOException{
        //System.out.println("submitting data fr fr");
        /* Process should work as follows:
        1.) Capture input data
        2.) Formulate query using this input data
        3.) Create a new entry in collectionsDB table
        4.) Notify user of success */
        String enteredCollectionName = addcollectioncollectionname_textfield.getText();
        String enteredCollectionType = addcollectioncollectiontype_textfield.getText();
        String enteredMaxSize = addcollectionmaxsize_textfield.getText();

        if (enteredCollectionName == null || enteredCollectionName.trim().isEmpty()) {
            showMessage("Please enter a name for the new collection.");
            return;
        }

        int maxSize = 0;
        if (enteredMaxSize != null && !enteredMaxSize.trim().isEmpty()) {
            try {
                maxSize = Integer.parseInt(enteredMaxSize.trim());
            } catch (NumberFormatException nfe) {
                showMessage("Max size must be a whole number.");
                return;
            }
        }

        if (maxSize < 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("LibreStock Notification");
            err.setHeaderText(null);
            err.setContentText("Max size must be zero or a positive number.");
            err.showAndWait();
            return;
        }

        try {
            DbAccess db = new DbAccess();
            Inventory inv = new Inventory(db);

            // Compute next available collection id
            java.util.List<java.util.Map<String, Object>> nextCollRows = db.runQuery("SELECT COALESCE(MAX(id), 0) + 1 AS nextId FROM collections");
            int nextCollId = 1;
            if (nextCollRows != null && !nextCollRows.isEmpty()) {
                Object val = nextCollRows.get(0).get("nextId");
                nextCollId = ((Number) val).intValue();
            }

            String collName = enteredCollectionName.trim();
            String collType = (enteredCollectionType == null) ? "" : enteredCollectionType.trim();

            inv.createCollection(nextCollId, collName, collType, maxSize);

            showMessage("Collection created successfully.");
            clearAddCollectionFields();
        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in add collection: " + sqle.getMessage());
            showMessage("Database error: " + sqle.getMessage());
        }
    }

    @FXML
    public void quitAddCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAddCollectionAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)addcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}