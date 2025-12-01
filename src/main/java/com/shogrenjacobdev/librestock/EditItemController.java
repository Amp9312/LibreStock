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
    @FXML private Button edititemsearch_button; // saved for later even if not used currently
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
    private void ititemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*Process is as follows: 
        1. Capture data from collection, item name, quantity, and sku
        1.5) Check if user only entered item name - if so, then run lookup method instead
        2. Find database entry using itemID, make sure they match
        3. Perform update query using data pulled from fields  */

        String enteredItemID = edititemitemID_textfield.getText();
        String enteredCollection = edititemcollection_textfield.getText();
        String enteredItemName = edititemitemname_textfield.getText();
        String enteredQuantity = edititemquantity_textfield.getText();
        String enteredSKU = edititemsku_textfield.getText();

        /*
        Check if looking up or submitting changes
        if(adminedititemitemID_textfield.isEmpty()){
            if(!adminedititemitemname_textfield.isEmpty()){
                String itemIDFromDB = getItemID(enteredItemName);
                adminedititemitemID_textfield.setText(itemIDFromDB);
                }}
        else{
        
         TODO: Look up in DB, match itemID values

         TODO: If matched, perform update query on item DB using entered values above
        
         showMessage("Query Matched! Changes were successful");
         newAdminEditItemMenuClick();*/
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
        String enteredItemID = edititemitemID_textfield.getText();

        // Insert cool database checking logic here where the ID is matched against the items in the DB

        // TODO: If itemID match is found, populate data in fields with item data from db entry (note the methods listed here arent made yet)
        /* String itemCollection = getItemCollectionFromDB();
           String itemName = getItemCollectionFromDB();
           String itemQuantity = getItemCollectionFromDB();
           String itemSKU = getItemCollectionFromDB();

        Will need to turn off itemID field to prevent insertion anomalies in the DB

        if(itemID match found) {
        edititemitemID_textfield.setEditable(false);
        }
        
        This is where it checks if the textfields are currently editable.   
        if(!edititemcollection_textfield.isEditable()){
           edititemcollection_textfield.setEditable(true);
           edititemitemname_textfield.setEditable(true);
           edititemquantity_textfield.setEditable(true);
           edititemsku_textfield.setEditable(true);
           }
        

        Here is where the textfield values get set - this should just work as is   
        edititemcollection_textfield.setText(itemcollection);
        edititemitemname_textfield.setText(itemName);
        edititemquantity_textfield.setText(itemQuantity);
        edititemsku_textfield.setText(itemSKU);
        */
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