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

public class AdminNewItemController {
    Stage stage;
    @FXML private Button adminnewitemreturn_button;
    @FXML private Button adminnewitemsubmit_button;
    @FXML private MenuItem adminnewitemaboutlibrestock_menu;
    @FXML private MenuItem adminnewitemquit_menu;
    @FXML private TextField adminnewitemitemname_textfield;
    @FXML private TextField adminnewitemcollection_textfield;
    @FXML private TextField adminnewitemquantity_textfield;
    @FXML private TextField adminnewitemsku_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LibreStock Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() throws IOException{
        adminnewitemitemname_textfield.clear();
        adminnewitemcollection_textfield.clear();
        adminnewitemquantity_textfield.clear();
        adminnewitemsku_textfield.clear();
    }

    @FXML
    private void adminnewitemReturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)adminnewitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void adminnewitemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*This process works as follows:
        1.) Capture input from fields
        2.) Match collection with existing collection name 
        2.5) If no existing collection, one is made - creating new entry in collectionDB
        3.) Create new item record in item DB*/

        String enteredAdminItemName = adminnewitemitemname_textfield.getText();
        String enteredAdminCollection = adminnewitemcollection_textfield.getText();
        String enteredAdminQuantity = adminnewitemquantity_textfield.getText();
        String enteredAdminSKU = adminnewitemsku_textfield.getText();

        if (enteredAdminItemName == null || enteredAdminItemName.trim().isEmpty()) {
            showMessage("Please enter a name for the new item.");
            return;
        }

        int quantity = 0;
        if (enteredAdminQuantity != null && !enteredAdminQuantity.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(enteredAdminQuantity.trim());
            } catch (NumberFormatException nfe) {
                showMessage("Quantity must be a whole number.");
                return;
            }
        }

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

            // Determine collection id: allow either numeric id or collection name
            int collectionId = 0;
            if (enteredAdminCollection != null && !enteredAdminCollection.trim().isEmpty()) {
                String coll = enteredAdminCollection.trim();
                try {
                    collectionId = Integer.parseInt(coll);
                } catch (NumberFormatException nfe) {
                    // treat as name — try to find existing collection
                    List<Map<String, Object>> collRows = db.runQuery("SELECT * FROM collections WHERE name = ?", coll);
                    if (collRows != null && !collRows.isEmpty()) {
                        Object val = collRows.get(0).get("id");
                        collectionId = ((Number) val).intValue();
                    } else {
                        // create new collection id
                        List<Map<String, Object>> nextColl = db.runQuery("SELECT COALESCE(MAX(id), 0) + 1 AS nextId FROM collections");
                        if (nextColl != null && !nextColl.isEmpty()) {
                            collectionId = ((Number) nextColl.get(0).get("nextId")).intValue();
                        } else {
                            collectionId = 1;
                        }
                        inv.createCollection(collectionId, coll, "", 0);
                    }
                }
            }

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
                            err.setContentText("The selected collection is full (size limit: " + collSize + ").\nCreate a new collection or use the collection editing menu to increase its size.");
                            err.showAndWait();
                            return;
                        }
                    }
                }
            }

            // Compute next available itemId
            List<Map<String, Object>> nextItemRows = db.runQuery("SELECT COALESCE(MAX(itemId), 0) + 1 AS nextId FROM items");
            int nextItemId = 1;
            if (nextItemRows != null && !nextItemRows.isEmpty()) {
                nextItemId = ((Number) nextItemRows.get(0).get("nextId")).intValue();
            }

            inv.createItem(nextItemId, enteredAdminSKU == null ? "" : enteredAdminSKU.trim(), enteredAdminItemName.trim(), quantity, collectionId, "");

            showMessage("Item created successfully.");
            clearFields();
        } catch (SQLException sqle) {
            System.err.println("SQLException in admin new item: " + sqle.getMessage());
            showMessage("Database error: " + sqle.getMessage());
        }
    }

    @FXML
    public void quitAdminNewItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAdminNewItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)adminnewitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}