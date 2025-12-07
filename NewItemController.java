package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NewItemController {
    Stage stage;
    @FXML private Button newitemreturn_button;
    @FXML private Button newitemsubmit_button;
    @FXML private MenuItem newitemquit_menu;
    @FXML private MenuItem newitemaboutlibrestock_menu;
    @FXML private TextField newitemitemname_textfield;
    @FXML private TextField newitemcollection_textfield;
    @FXML private TextField newitemquantity_textfield;
    @FXML private TextField newitemsku_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LibreStock Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() throws IOException{
        newitemitemname_textfield.clear();
        newitemcollection_textfield.clear();
        newitemquantity_textfield.clear();
        newitemsku_textfield.clear();
    }

    @FXML
    private void newitemReturnButtonClick() throws IOException{
        //System.out.println("Returning to user dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)newitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newitemSubmitButtonClick() throws IOException{
        //System.out.println("submitting data fr fr");
        /*This process works as follows:
        1.) Capture input from fields
        2.) Match collection with existing collection name 
        2.5) If no existing collection, one is made - creating new entry in collectionDB
        3.) Create new item record in item DB*/

        String enteredItemName = newitemitemname_textfield.getText();
        String enteredCollection = newitemcollection_textfield.getText();
        String enteredQuantity = newitemquantity_textfield.getText();
        String enteredSKU = newitemsku_textfield.getText();

        if (enteredItemName == null || enteredItemName.trim().isEmpty()) {
            showMessage("Please enter a name for the new item.");
            return;
        }

        int quantity = 0;
        if (enteredQuantity != null && !enteredQuantity.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(enteredQuantity.trim());
            } catch (NumberFormatException nfe) {
                showMessage("Quantity must be a whole number.");
                return;
            }
        }

        // Reject negative quantities with an error modal
        if (quantity < 0) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("LibreStock Notification");
            err.setHeaderText(null);
            err.setContentText("Quantity must be a positive number.");
            err.showAndWait();
            return;
        }

        try {
            DbAccess db = new DbAccess();
            Inventory inv = new Inventory(db);

            // Determine collection id: allow numeric id or collection name
            int collectionId = 0;
            if (enteredCollection != null && !enteredCollection.trim().isEmpty()) {
                String coll = enteredCollection.trim();
                try {
                    collectionId = Integer.parseInt(coll);
                } catch (NumberFormatException nfe) {
                    List<Map<String, Object>> collRows = db.runQuery("SELECT * FROM collections WHERE name = ?", coll);
                    if (collRows != null && !collRows.isEmpty()) {
                        Object val = collRows.get(0).get("id");
                        collectionId = ((Number) val).intValue();
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("LibreStock");
                        a.setHeaderText(null);
                        a.setContentText("No collection found with that name. Please ask an admin to create the collection, or try a different collection name.");
                        a.showAndWait();
                        return;
                    }
                }
            }

            // Compute next available itemId
            // Before creating the item, if collectionId corresponds to an existing collection
            // check that the collection isn't already at or above its configured size limit.
            if (collectionId > 0) {
                List<Map<String, Object>> collCheck = db.runQuery("SELECT * FROM collections WHERE id = ?", collectionId);
                if (collCheck != null && !collCheck.isEmpty()) {
                    Object sizeObj = collCheck.get(0).get("size");
                    int collSize = sizeObj == null ? 0 : ((Number) sizeObj).intValue();
                    if (collSize > 0) {
                        List<Map<String, Object>> countRows = db.runQuery("SELECT COUNT(*) AS cnt FROM items WHERE collection = ?", collectionId);
                        int numItems = 0;
                        if (countRows != null && !countRows.isEmpty() && countRows.get(0).get("cnt") != null) {
                            numItems = ((Number) countRows.get(0).get("cnt")).intValue();
                        }
                        if (numItems >= collSize) {
                            Alert err = new Alert(Alert.AlertType.ERROR);
                            err.setTitle("LibreStock Notification");
                            err.setHeaderText(null);
                            err.setContentText("The selected collection is full (size limit: " + collSize + ").\nCreate a new collection or contact an admin to increase its size.");
                            err.showAndWait();
                            return;
                        }
                    }
                }
            }

            List<Map<String, Object>> nextItemRows = db.runQuery("SELECT COALESCE(MAX(itemId), 0) + 1 AS nextId FROM items");
            int nextItemId = 1;
            if (nextItemRows != null && !nextItemRows.isEmpty()) {
                nextItemId = ((Number) nextItemRows.get(0).get("nextId")).intValue();
            }

            inv.createItem(nextItemId, enteredSKU == null ? "" : enteredSKU.trim(), enteredItemName.trim(), quantity, collectionId, "");

            showMessage("Item created successfully.");
            clearFields();
        } catch (SQLException sqle) {
            System.err.println("SQLException in new item: " + sqle.getMessage());
            showMessage("Database error: " + sqle.getMessage());
        }
    }

    @FXML
    public void quitNewItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openNewItemAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)newitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}