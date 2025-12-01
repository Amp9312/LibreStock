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
    @FXML private Button adminedititemsearch_button; // saved for later even if not used currently
    @FXML private MenuItem adminedititemquit_menu;
    @FXML private MenuItem adminedititemaboutlibrestock_menu;
    @FXML private MenuItem adminedititemnew_menu;
    @FXML private TextField adminedititemitemID_textfield;
    @FXML private TextField adminedititemcollection_textfield;
    @FXML private TextField adminedititemitemname_textfield;
    @FXML private TextField adminedititemquantity_textfield;
    @FXML private TextField adminedititemsku_textfield;

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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

        String adminEnteredItemID = adminedititemitemID_textfield.getText();
        String adminEnteredCollection = adminedititemcollection_textfield.getText();
        String adminEnteredItemName = adminedititemitemname_textfield.getText();
        String adminEnteredQuantity = adminedititemquantity_textfield.getText();
        String adminEnteredSKU = adminedititemsku_textfield.getText();

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
    private void adminedititemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* Process is as follows:
        1.) Capture data from itemID field
        2.) Match data against all items in item DB
        3.) If match is found, disable itemID field, and enable fields and populate them with data from that database entry 
        3.5) If match is not found, throw error state
*/
        String enteredAdminItemID = adminedititemitemID_textfield.getText();

        // Insert cool database checking logic here where the ID is matched against the items in the DB

        // TODO: If adminitemID match is found, populate data in fields with item data from db entry (note the methods listed here arent made yet)
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
        adminedititemitemID_textfield.clear();
        adminedititemcollection_textfield.clear();
        adminedititemitemname_textfield.clear();
        adminedititemquantity_textfield.clear();
        adminedititemsku_textfield.clear();

        adminedititemitemID_textfield.setEditable(true);
        adminedititemcollection_textfield.setEditable(false);
        adminedititemitemname_textfield.setEditable(false);
        adminedititemquantity_textfield.setEditable(false);
        adminedititemsku_textfield.setEditable(false);
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
}