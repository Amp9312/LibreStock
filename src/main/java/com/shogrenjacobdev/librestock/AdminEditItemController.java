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

public class AdminEditItemController {
    Stage stage;
    @FXML private Button adminedititemreturn_button;
    @FXML private Button adminedititemsubmit_button;
    @FXML private Button edititemsearch_button;
    @FXML private MenuItem adminedititemquit_menu;
    @FXML private MenuItem adminedititemaboutlibrestock_menu;
    @FXML private MenuItem adminedititemnew_menu;
    @FXML private TextField edititemitemID_textfield;
    @FXML private TextField edititemcollection_textfield;
    @FXML private TextField edititemitemname_textfield;
    @FXML private TextField edititemquantity_textfield;
    @FXML private TextField edititemsku_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            String adminEnteredItemID = edititemitemID_textfield.getText();
            String adminEnteredCollection = edititemcollection_textfield.getText();
            String adminEnteredItemName = edititemitemname_textfield.getText();
            String adminEnteredQuantity = edititemquantity_textfield.getText();
            String adminEnteredSKU = edititemsku_textfield.getText();
        alert.setTitle("LibreStock Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void adminededititemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminedititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adminedititemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*Process is as follows: 
        1. Capture data from collection, item name, quantity, and sku
        1.5) Check if user only entered item name - if so, then run lookup method instead
        2. Find database entry using itemID, make sure they match
        3. Perform update query using data pulled from fields  */
        // Capture entered values
        String adminEnteredItemID = edititemitemID_textfield.getText();
        String adminEnteredCollection = edititemcollection_textfield.getText();
        String adminEnteredItemName = edititemitemname_textfield.getText();
        String adminEnteredQuantity = edititemquantity_textfield.getText();
        String adminEnteredSKU = edititemsku_textfield.getText();

        if (adminEnteredItemID == null || adminEnteredItemID.trim().isEmpty()) {
            showMessage("To update an item, please provide its itemID.");
            return;
        }

        try {
            DbAccess db = new DbAccess();

            int itemId;
            try {
                itemId = Integer.parseInt(adminEnteredItemID.trim());
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

            String newSku = (adminEnteredSKU == null || adminEnteredSKU.trim().isEmpty()) ? (current.get("sku") == null ? "" : current.get("sku").toString()) : adminEnteredSKU.trim();
            String newName = (adminEnteredItemName == null || adminEnteredItemName.trim().isEmpty()) ? (current.get("name") == null ? "" : current.get("name").toString()) : adminEnteredItemName.trim();

            int newQuantity;
            if (adminEnteredQuantity == null || adminEnteredQuantity.trim().isEmpty()) {
                Object q = current.get("quantity");
                newQuantity = q == null ? 0 : ((Number) q).intValue();
            } else {
                try {
                    newQuantity = Integer.parseInt(adminEnteredQuantity.trim());
                } catch (NumberFormatException nfe) {
                    showMessage("Quantity must be a whole number.");
                    return;
                }
            }

            int newCollection;
            if (adminEnteredCollection == null || adminEnteredCollection.trim().isEmpty()) {
                Object c = current.get("collection");
                newCollection = c == null ? 0 : ((Number) c).intValue();
            } else {
                try {
                    newCollection = Integer.parseInt(adminEnteredCollection.trim());
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
            System.err.println("SQLException in AdminEditItemController.update: " + e.getMessage());
            showMessage("Database error: " + e.getMessage());
        }
    }

    @FXML
    private void adminedititemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* Process is as follows:
        1.) Capture data from itemID field
        2.) Match data against all items in item DB
        3.) If match is found, disable itemID field, and enable fields and populate them with data from that database entry 
        3.5) If match is not found, throw error state
*/
        try {
            boolean found = adminCheckForSearch();
            if (!found) {
                showMessage("No matching item found.");
            }
        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in AdminEditItemController.search: " + sqle.getMessage());
            showMessage("Database error: " + sqle.getMessage());
        } catch (IllegalArgumentException iae) {
            showMessage(iae.getMessage());
        }
        /* String enteredAdminItemCollection = getAdminItemCollectionFromDB();
           String enteredAdminItemName = getAdminItemCollectionFromDB();
           String enteredAdminItemQuantity = getAdminItemCollectionFromDB();
           String enteredAdminItemSKU = getAdminItemCollectionFromDB();

        Will need to turn off itemID field to prevent insertion anomalies in the DB

        if(adminitemID match found) {
        adminedititemitemID_textfield.setEditable(false);
        }
        
        This is where it checks if the textfields are currently editable.   
        if(!adminedititemcollection_textfield.isEditable()){
           adminedititemcollection_textfield.setEditable(true);
           adminedititemitemname_textfield.setEditable(true);
           adminedititemquantity_textfield.setEditable(true);
           adminedititemsku_textfield.setEditable(true);
           }
        

        Here is where the textfield values get set - this should just work as is   
        adminedititemcollection_textfield.setText(itemcollection);
        adminedititemitemname_textfield.setText(itemName);
        adminedititemquantity_textfield.setText(itemQuantity);
        adminedititemsku_textfield.setText(itemSKU);
        */
    }

    @FXML
    public void quitAdminEditItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    public void newAdminEditItemMenuClick() throws IOException{
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

    /**
     * Admin helper: lookup by admin itemId or name, populate fields and return true when found.
     * Throws IllegalArgumentException when no match or input invalid.
     */
    private boolean adminCheckForSearch() throws java.sql.SQLException {
        String enteredAdminItemID = edititemitemID_textfield.getText();
        String enteredAdminItemName = edititemitemname_textfield.getText();

        if (enteredAdminItemID != null) enteredAdminItemID = enteredAdminItemID.trim();
        if (enteredAdminItemName != null) enteredAdminItemName = enteredAdminItemName.trim();

        DbAccess db = new DbAccess();

        java.util.List<java.util.Map<String, Object>> rows = null;

        if (enteredAdminItemID != null && !enteredAdminItemID.isEmpty()) {
            try {
                int itemId = Integer.parseInt(enteredAdminItemID);
                rows = db.runQuery("SELECT * FROM items WHERE itemId = ?", itemId);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("itemID must be a whole number.");
            }
        }

        if ((rows == null || rows.isEmpty()) && enteredAdminItemName != null && !enteredAdminItemName.isEmpty()) {
            rows = db.runQuery("SELECT * FROM items WHERE name = ?", enteredAdminItemName);
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
    public void openAdminEditItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminedititemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML void newEditItemMenuClick() throws IOException{
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
}