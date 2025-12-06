package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class EditCollectionController {
    Stage stage;
    @FXML private Button editcollectionreturn_button;
    @FXML private Button editcollectionsubmit_button;
    @FXML private Button editcollectionsearch_button;
    @FXML private MenuItem editcollectionquit_menu;
    @FXML private MenuItem editcollectionaboutlibrestock_menu;
    @FXML private MenuItem editcollectionnew_menu;
    @FXML private TextField editcollectioncollectionID_textfield;
    @FXML private TextField editcollectioncollectionname_textfield;
    @FXML private TextField editcollectioncollectiontype_textfield;
    @FXML private TextField editcollectionmaxsize_textfield;

    @FXML
    private void editcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)editcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editcolsubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*Process is as follows:
        1.) Capture data from the entered fields
        2.) Perform a match inquiry based on entered CollectionID
        3.) If matched, formulate query to submit changes and notify user of success (if not - give error message) */

        String enteredEditCollectionID = editcollectioncollectionID_textfield.getText();
        String enteredEditCollectionName = editcollectioncollectionname_textfield.getText();
        String enteredEditCollectionType = editcollectioncollectiontype_textfield.getText();
        String enteredEditCollectionSize = editcollectionmaxsize_textfield.getText();

        if (enteredEditCollectionID == null || enteredEditCollectionID.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Please enter a collection ID to update.");
            a.showAndWait();
            return;
        }

        int collId;
        try {
            collId = Integer.parseInt(enteredEditCollectionID.trim());
        } catch (NumberFormatException nfe) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Collection ID must be a whole number.");
            a.showAndWait();
            return;
        }

        if (enteredEditCollectionName == null) enteredEditCollectionName = "";
        if (enteredEditCollectionType == null) enteredEditCollectionType = "";
        if (enteredEditCollectionSize == null) enteredEditCollectionSize = "";

        // validate size (allow blank -> 0)
        int sizeVal = 0;
        String sizeTrim = enteredEditCollectionSize.trim();
        if (!sizeTrim.isEmpty()) {
            try {
                sizeVal = Integer.parseInt(sizeTrim);
            } catch (NumberFormatException nfe) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("Max size must be a whole number.");
                a.showAndWait();
                return;
            }
        }

        try {
            DbAccess db = new DbAccess();

            // verify collection exists
            java.util.List<java.util.Map<String, Object>> rows = db.runQuery("SELECT * FROM collections WHERE id = ?", collId);
            if (rows == null || rows.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("No collection found with that ID.");
                a.showAndWait();
                return;
            }

            // perform update
            db.runQuery("UPDATE collections SET name = ?, type = ?, size = ? WHERE id = ?",
                        enteredEditCollectionName.trim(), enteredEditCollectionType.trim(), sizeVal, collId);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("LibreStock");
            info.setHeaderText(null);
            info.setContentText("Collection updated successfully.");
            info.showAndWait();

            // allow new searches/edits
            editcollectioncollectionID_textfield.setEditable(true);

        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in EditCollectionController.update: " + sqle.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + sqle.getMessage());
            a.showAndWait();
        }

    }

    @FXML
    private void editcolsearchButtonClick() throws IOException{
        System.out.println("searching collection data");

        String enteredCollectionID = editcollectioncollectionID_textfield.getText();
        if (enteredCollectionID == null) enteredCollectionID = "";
        enteredCollectionID = enteredCollectionID.trim();

        if (enteredCollectionID.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Please enter a collection ID to search.");
            a.showAndWait();
            return;
        }

        int collId;
        try {
            collId = Integer.parseInt(enteredCollectionID);
        } catch (NumberFormatException nfe) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Collection ID must be a whole number.");
            a.showAndWait();
            return;
        }

        try {
            DbAccess db = new DbAccess();
            java.util.List<java.util.Map<String, Object>> rows = db.runQuery("SELECT * FROM collections WHERE id = ?", collId);
            if (rows == null || rows.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("No collection found with that ID.");
                a.showAndWait();
                return;
            }

            java.util.Map<String, Object> coll = rows.get(0);
            Object idVal = coll.get("id");
            Object nameVal = coll.get("name");
            Object typeVal = coll.get("type");
            Object sizeVal = coll.get("size");

            editcollectioncollectionID_textfield.setText(idVal == null ? "" : idVal.toString());
            editcollectioncollectionname_textfield.setText(nameVal == null ? "" : nameVal.toString());
            editcollectioncollectiontype_textfield.setText(typeVal == null ? "" : typeVal.toString());
            editcollectionmaxsize_textfield.setText(sizeVal == null ? "" : sizeVal.toString());

            // set editability: lock id, allow edits to other fields
            editcollectioncollectionID_textfield.setEditable(false);
            editcollectioncollectionname_textfield.setEditable(true);
            editcollectioncollectiontype_textfield.setEditable(true);
            editcollectionmaxsize_textfield.setEditable(true);

        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in EditCollectionController.search: " + sqle.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + sqle.getMessage());
            a.showAndWait();
        }
    }

     @FXML
    public void quitEditCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openEditCollectionAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)editcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void newEditCollectionMenuClick() throws IOException{
        editcollectioncollectionID_textfield.clear();
        editcollectioncollectionname_textfield.clear();
        editcollectioncollectiontype_textfield.clear();
        editcollectionmaxsize_textfield.clear();

        editcollectioncollectionID_textfield.setEditable(true);
    }
}
