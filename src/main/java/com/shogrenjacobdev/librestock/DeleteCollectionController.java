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

public class DeleteCollectionController {
    Stage stage;
    @FXML private Button deletecollectionreturn_button;
    @FXML private Button deletecollectiondelete_button; 
    @FXML private Button deletecollectionsearch_button;
    @FXML private MenuItem deletecollectionquit_menu;
    @FXML private MenuItem deletecollectionaboutlibrestock_menu;
    @FXML private TextField deletecollectioncollectionID_textfield;
    @FXML private TextField deletecollectioncollectionname_textfield;
    @FXML private TextField deletecollectionnumitems_textfield;
    @FXML private TextField deletecollectionmaxsize_textfield;
    @FXML private TextField deletecollectioncollectiontype_textfield;

    public void clearDelColFields() throws IOException{
        deletecollectioncollectionID_textfield.clear();
        deletecollectioncollectionname_textfield.clear();
        deletecollectionnumitems_textfield.clear();
        deletecollectionmaxsize_textfield.clear();
        deletecollectioncollectiontype_textfield.clear();
    }

    @FXML
    private void delcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)deletecollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void delcolsearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /*This process works as follows:
        1.) Capture CollectionID
        2.) Perform search for match in collection DB using collection ID
        3.) If match found, populate fields so user can confirm details
        3.5) if match not found, state item does not exist in DB
         */

        String enteredDelCollectionID = deletecollectioncollectionID_textfield.getText();

        if (enteredDelCollectionID == null || enteredDelCollectionID.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Please enter a collection ID to search.");
            a.showAndWait();
            return;
        }

        int collId;
        try {
            collId = Integer.parseInt(enteredDelCollectionID.trim());
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

            // number of items in this collection
            java.util.List<java.util.Map<String, Object>> countRows = db.runQuery("SELECT COUNT(*) AS cnt FROM items WHERE collection = ?", collId);
            int numItems = 0;
            if (countRows != null && !countRows.isEmpty() && countRows.get(0).get("cnt") != null) {
                numItems = ((Number) countRows.get(0).get("cnt")).intValue();
            }

            deletecollectioncollectionID_textfield.setText(idVal == null ? "" : idVal.toString());
            deletecollectioncollectionname_textfield.setText(nameVal == null ? "" : nameVal.toString());
            deletecollectioncollectiontype_textfield.setText(typeVal == null ? "" : typeVal.toString());
            deletecollectionmaxsize_textfield.setText(sizeVal == null ? "" : sizeVal.toString());
            deletecollectionnumitems_textfield.setText(Integer.toString(numItems));

            // lock fields for confirmation
            deletecollectioncollectionID_textfield.setEditable(false);
            deletecollectioncollectionname_textfield.setEditable(false);
            deletecollectioncollectiontype_textfield.setEditable(false);
            deletecollectionmaxsize_textfield.setEditable(false);
            deletecollectionnumitems_textfield.setEditable(false);

        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in DeleteCollectionController.search: " + sqle.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + sqle.getMessage());
            a.showAndWait();
        }
    }
    @FXML
    private void delcolsubmitButtonClick() throws IOException{
        System.out.println("deleting for data fr fr");
        /*Process works as follows:
        1.) Look up item using collection ID
        2.) If a match is found, find out how many items have that collection type
        2.5) If a match is not found, give user an error message (note: this would only occur if they edit the item id after searching)
        3.) Delete items from item DB if their collection matches the type being deleted
        4.) Delete collection from collection DB
        5.) Show user blank slate, incase they need to delete other collections */
        
        String enteredDelCollectionID = deletecollectioncollectionID_textfield.getText();

        if (enteredDelCollectionID == null || enteredDelCollectionID.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Please provide a collection ID to delete.");
            a.showAndWait();
            return;
        }

        int collId;
        try {
            collId = Integer.parseInt(enteredDelCollectionID.trim());
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

            java.util.List<java.util.Map<String, Object>> collRows = db.runQuery("SELECT * FROM collections WHERE id = ?", collId);
            if (collRows == null || collRows.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("No collection found with that ID.");
                a.showAndWait();
                clearDelColFields();
                return;
            }

            // Find all items that belong to this collection
            java.util.List<java.util.Map<String, Object>> itemRows = db.runQuery("SELECT itemId FROM items WHERE collection = ?", collId);

            if (itemRows != null && !itemRows.isEmpty()) {
                for (java.util.Map<String, Object> row : itemRows) {
                    Object idObj = row.get("itemId");
                    if (idObj != null) {
                        int itemId = ((Number) idObj).intValue();
                        db.runQuery("DELETE FROM items WHERE itemId = ?", itemId);
                    }
                }
            }

            // Delete the collection itself
            db.runQuery("DELETE FROM collections WHERE id = ?", collId);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("LibreStock");
            info.setHeaderText(null);
            info.setContentText("Collection and affiliated items deleted successfully.");
            info.showAndWait();

            // clear and reset
            clearDelColFields();
            deletecollectioncollectionID_textfield.setEditable(true);

        } catch (java.sql.SQLException e) {
            System.err.println("SQLException in DeleteCollectionController.delete: " + e.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + e.getMessage());
            a.showAndWait();
        }
    }

        @FXML
    public void quitDeleteCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openDeleteCollectionAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)deletecollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}