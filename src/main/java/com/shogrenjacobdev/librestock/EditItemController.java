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

public class EditItemController {
    Stage stage;
    @FXML private Button edititemreturn_button;
    @FXML private Button edititemsubmit_button;
    @FXML private Button edititemsearch_button;
    @FXML private MenuItem edititemquit_menu;
    @FXML private MenuItem edititemaboutlibrestock_menu;
    @FXML private MenuItem edititemnew_menu;
    @FXML private TextField edititemitemID_textfield;
    @FXML private TextField edititemcollection_textfield;
    @FXML private TextField edititemitemname_textfield;
    @FXML private TextField edititemquantity_textfield;
    @FXML private TextField edititemsku_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("LibreStock Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void edititemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)edititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void edititemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        // Capture entered values
        String enteredItemID = edititemitemID_textfield.getText();
        String enteredCollection = edititemcollection_textfield.getText();
        String enteredItemName = edititemitemname_textfield.getText();
        String enteredQuantity = edititemquantity_textfield.getText();
        String enteredSKU = edititemsku_textfield.getText();

        if (enteredItemID == null || enteredItemID.trim().isEmpty()) {
            showMessage("To update an item, please provide its itemID.");
            return;
        }

        try {
            DbAccess db = new DbAccess();

            int itemId;
            try {
                itemId = Integer.parseInt(enteredItemID.trim());
            } catch (NumberFormatException nfe) {
                showMessage("itemID must be a whole number.");
                return;
            }

            java.util.List<java.util.Map<String, Object>> rows = db.runQuery("SELECT * FROM items WHERE itemId = ?", itemId);
            if (rows == null || rows.isEmpty()) {
                showMessage("No item found with that itemID.");
                return;
            }

            java.util.Map<String, Object> current = rows.get(0);

            String newSku = (enteredSKU == null || enteredSKU.trim().isEmpty()) ? (current.get("sku") == null ? "" : current.get("sku").toString()) : enteredSKU.trim();
            String newName = (enteredItemName == null || enteredItemName.trim().isEmpty()) ? (current.get("name") == null ? "" : current.get("name").toString()) : enteredItemName.trim();

            int newQuantity;
            if (enteredQuantity == null || enteredQuantity.trim().isEmpty()) {
                Object q = current.get("quantity");
                newQuantity = q == null ? 0 : ((Number) q).intValue();
            } else {
                try {
                    newQuantity = Integer.parseInt(enteredQuantity.trim());
                } catch (NumberFormatException nfe) {
                    showMessage("Quantity must be a whole number.");
                    return;
                }
            }

            int newCollection;
            if (enteredCollection == null || enteredCollection.trim().isEmpty()) {
                Object c = current.get("collection");
                newCollection = c == null ? 0 : ((Number) c).intValue();
            } else {
                try {
                    newCollection = Integer.parseInt(enteredCollection.trim());
                } catch (NumberFormatException nfe) {
                    showMessage("Collection must be a whole number (collection id).");
                    return;
                }
            }

            String newDescription = current.get("description") == null ? "" : current.get("description").toString();

            db.runQuery("UPDATE items SET sku = ?, name = ?, quantity = ?, collection = ?, description = ? WHERE itemId = ?",
                    newSku, newName, newQuantity, newCollection, newDescription, itemId);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("LibreStock");
            info.setHeaderText(null);
            info.setContentText("Item updated successfully.");
            info.showAndWait();

            // Reset form state
            edititemitemID_textfield.setEditable(true);
            edititemcollection_textfield.setEditable(false);
            edititemitemname_textfield.setEditable(false);
            edititemquantity_textfield.setEditable(false);
            edititemsku_textfield.setEditable(false);

        } catch (java.sql.SQLException e) {
            System.err.println("SQLException in EditItemController.update: " + e.getMessage());
            showMessage("Database error: " + e.getMessage());
        }
    }

        @FXML
    private void edititemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* Process is as follows:
        1.) Capture data from itemID field
        2.) Match data against all items in item DB
        3.) If match is found, disable itemID field, and enable fields and populate them with data from that database entry 
        3.5) If match is not found, throw error state
*/
        try {
            boolean found = checkForSearch();
            if (!found) {
                // This should not happen because checkForSearch throws when not found,
                showMessage("No matching item found.");
            }
        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in EditItemController.search: " + sqle.getMessage());
            showMessage("Database error: " + sqle.getMessage());
        } catch (IllegalArgumentException iae) {
            showMessage(iae.getMessage());
        }
    }

    /*
    Helper: performs a lookup in the items table using the values currently
    present in the `edititemitemID_textfield` or `edititemitemname_textfield`.
    If a matching item is found the UI fields are populated and the method
    returns true. If no match is found an IllegalArgumentException is thrown.
    */
    private boolean checkForSearch() throws java.sql.SQLException {
        String enteredItemID = edititemitemID_textfield.getText();
        String enteredItemName = edititemitemname_textfield.getText();

        if (enteredItemID != null) enteredItemID = enteredItemID.trim();
        if (enteredItemName != null) enteredItemName = enteredItemName.trim();

        DbAccess db = new DbAccess();

        java.util.List<java.util.Map<String, Object>> rows = null;

        if (enteredItemID != null && !enteredItemID.isEmpty()) {
            try {
                int itemId = Integer.parseInt(enteredItemID);
                rows = db.runQuery("SELECT * FROM items WHERE itemId = ?", itemId);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("itemID must be a whole number.");
            }
        }

        if ((rows == null || rows.isEmpty()) && enteredItemName != null && !enteredItemName.isEmpty()) {
            rows = db.runQuery("SELECT * FROM items WHERE name = ?", enteredItemName);
        }

        if (rows != null && !rows.isEmpty()) {
            java.util.Map<String, Object> item = rows.get(0);

            Object idVal = item.get("itemId");
            Object collVal = item.get("collection");
            Object qtyVal = item.get("quantity");
            Object skuVal = item.get("sku");

            edititemitemID_textfield.setText(idVal == null ? "" : idVal.toString());
            edititemcollection_textfield.setText(collVal == null ? "" : collVal.toString());
            edititemitemname_textfield.setText(item.get("name") == null ? "" : item.get("name").toString());
            edititemquantity_textfield.setText(qtyVal == null ? "" : qtyVal.toString());
            edititemsku_textfield.setText(skuVal == null ? "" : skuVal.toString());

            // set editability so user can edit the returned data
            edititemitemID_textfield.setEditable(false);
            edititemcollection_textfield.setEditable(true);
            edititemitemname_textfield.setEditable(true);
            edititemquantity_textfield.setEditable(true);
            edititemsku_textfield.setEditable(true);

            return true;
        }

        throw new IllegalArgumentException("No matching item in the database.");
    }

        @FXML
    public void quitEditItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void newEditItemMenuClick() throws IOException{
        edititemitemID_textfield.clear();
        edititemcollection_textfield.clear();
        edititemitemname_textfield.clear();
        edititemquantity_textfield.clear();
        edititemsku_textfield.clear();

        edititemitemID_textfield.setEditable(true);
        edititemcollection_textfield.setEditable(false);
        edititemitemname_textfield.setEditable(false);
        edititemquantity_textfield.setEditable(false);
        edititemsku_textfield.setEditable(false);
    }

    @FXML
    public void openEditItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)edititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}