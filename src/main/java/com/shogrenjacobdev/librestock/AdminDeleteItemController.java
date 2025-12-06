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

public class AdminDeleteItemController {
    Stage stage;
    @FXML private Button admindeleteitemreturn_button;
    @FXML private Button admindeleteitemdelete_button;
    @FXML private Button admindeleteitemsearch_button;
    @FXML private MenuItem admindeleteitemquit_menu;
    @FXML private MenuItem admindeleteitemaboutlibrestock_menu;
    @FXML private TextField admindeleteitemitemID_textfield;
    @FXML private TextField admindeleteitemitemname_textfield;
    @FXML private TextField admindeleteitemcollection_textfield;
    @FXML private TextField admindeleteitemquantity_textfield;
    @FXML private TextField admindeleteitemsku_textfield;

    public void clearFields() throws IOException{
        admindeleteitemitemID_textfield.clear();
        admindeleteitemitemname_textfield.clear();
        admindeleteitemcollection_textfield.clear();
        admindeleteitemquantity_textfield.clear();
        admindeleteitemsku_textfield.clear();
    }

    @FXML
    private void admindelitemreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindeleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void admindelitemsearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /*This process works as follows:
        1.) Capture itemID
        2.) Perform search for match in itemDB using itemID
        3.) If match found, populate fields so user can confirm details
        3.5) if match not found, state item does not exist in DB
         */

        try {
            boolean found = adminDelCheckForSearch();
            if (!found) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("No matching item found.");
                a.showAndWait();
            }
        } catch (java.sql.SQLException sqle) {
            System.err.println("SQLException in AdminDeleteItemController.search: " + sqle.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + sqle.getMessage());
            a.showAndWait();
        } catch (IllegalArgumentException iae) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText(iae.getMessage());
            a.showAndWait();
        }
    }

    /*
     Lookup an item by itemId or name and populate the delete form.
     Returns true if an item is found. Throws IllegalArgumentException when input invalid.
     */

    private boolean adminDelCheckForSearch() throws java.sql.SQLException {
        String enteredDelItemID = admindeleteitemitemID_textfield.getText();
        String enteredDelItemName = admindeleteitemitemname_textfield.getText();

        if (enteredDelItemID != null) enteredDelItemID = enteredDelItemID.trim();
        if (enteredDelItemName != null) enteredDelItemName = enteredDelItemName.trim();

        DbAccess db = new DbAccess();
        java.util.List<java.util.Map<String, Object>> rows = null;

        if (enteredDelItemID != null && !enteredDelItemID.isEmpty()) {
            try {
                int itemId = Integer.parseInt(enteredDelItemID);
                rows = db.runQuery("SELECT * FROM items WHERE itemId = ?", itemId);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("itemID must be a whole number.");
            }
        }

        if ((rows == null || rows.isEmpty()) && enteredDelItemName != null && !enteredDelItemName.isEmpty()) {
            rows = db.runQuery("SELECT * FROM items WHERE name = ?", enteredDelItemName);
        }

        if (rows != null && !rows.isEmpty()) {
            java.util.Map<String, Object> item = rows.get(0);

            Object idVal = item.get("itemId");
            Object collValObj = item.get("collection");
            Object qtyVal = item.get("quantity");
            Object skuVal = item.get("sku");

            // Resolve collection id -> collection name for better UI/UX context 
            // (this is what shows the collection name in the field instead of the collection ID)
            String collName = "";
            if (collValObj != null) {
                try {
                    int collId = ((Number) collValObj).intValue();
                    java.util.List<java.util.Map<String, Object>> collRows = db.runQuery("SELECT name FROM collections WHERE id = ?", collId);
                    if (collRows != null && !collRows.isEmpty()) {
                        Object nameObj = collRows.get(0).get("name");
                        collName = nameObj == null ? "" : nameObj.toString();
                    } else {
                        // fallback to showing the id if no collection row found
                        collName = Integer.toString(collId);
                    }
                } catch (ClassCastException cce) {
                    collName = collValObj.toString();
                }
            }

            admindeleteitemitemID_textfield.setText(idVal == null ? "" : idVal.toString());
            admindeleteitemitemname_textfield.setText(item.get("name") == null ? "" : item.get("name").toString());
            admindeleteitemcollection_textfield.setText(collName);
            admindeleteitemquantity_textfield.setText(qtyVal == null ? "" : qtyVal.toString());
            admindeleteitemsku_textfield.setText(skuVal == null ? "" : skuVal.toString());

            // keep fields non-editable for delete confirmation
            admindeleteitemitemID_textfield.setEditable(false);
            admindeleteitemitemname_textfield.setEditable(false);
            admindeleteitemcollection_textfield.setEditable(false);
            admindeleteitemquantity_textfield.setEditable(false);
            admindeleteitemsku_textfield.setEditable(false);

            return true;
        }

        throw new IllegalArgumentException("No matching item in the database.");
    }
        @FXML
    private void admindelitemsubmitButtonClick() throws IOException{
        System.out.println("deleting for data fr fr");
        /*Process works as follows:
        1.) Look up item using itemID
        2.) If a match is found, remove that entry from the item DB
        2.5) If a match is not found, give user an error message (note: this would only occur if they edit the item id after searching)
        3.) Show user blank slate, incase they need to delete other items */
        
        String enteredDelItemID = admindeleteitemitemID_textfield.getText();

        if (enteredDelItemID == null || enteredDelItemID.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("To delete an item, please provide its itemID.");
            a.showAndWait();
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(enteredDelItemID.trim());
        } catch (NumberFormatException nfe) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("itemID must be a whole number.");
            a.showAndWait();
            return;
        }

        try {
            DbAccess db = new DbAccess();
            java.util.List<java.util.Map<String, Object>> rows = db.runQuery("SELECT * FROM items WHERE itemId = ?", itemId);
            if (rows == null || rows.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("LibreStock");
                a.setHeaderText(null);
                a.setContentText("No item found with that itemID.");
                a.showAndWait();
                return;
            }

            db.runQuery("DELETE FROM items WHERE itemId = ?", itemId);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("LibreStock");
            info.setHeaderText(null);
            info.setContentText("Item deleted successfully.");
            info.showAndWait();

            // clear the form and reset editability
            clearFields();
            admindeleteitemitemID_textfield.setEditable(true);

        } catch (java.sql.SQLException e) {
            System.err.println("SQLException in AdminDeleteItemController.delete: " + e.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("LibreStock");
            a.setHeaderText(null);
            a.setContentText("Database error: " + e.getMessage());
            a.showAndWait();
        }
    }

        @FXML
    public void quitAdminDeleteItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAdminDeleteItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)admindeleteitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}