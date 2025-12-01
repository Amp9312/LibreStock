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
    @FXML private Button admindeleteitemdelete_button; // saved for later even if not used currently
    @FXML private Button admindeleteitemsearch_button; // saved for later even if not used currently
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

        String enteredDelItemID = admindeleteitemitemID_textfield.getText();

        // TODO: Search for match in itemDB using itemID

        /*
        Populate fields once a match is found (note: methods listed here are not yet made)
        String delitemName = getItemNameFromDB();
        String delitemCollection = getItemCollectionFromDB();
        String delitemQuantity = getItemQuantityFromDB();
        String delitemSKU = getItemSKUFromDB();

        admindeleteitemitemname_textfield.setText(delitemName);
        admindeleteitemcollection_textfield.setText(delitemCollection);
        admindeleteitemquantity_textfield.setText(delitemQuantity);
        admindeleteitemsku_textfield.setText(delitemSKU);
        */
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
    
        // TODO: Method to match itemID above with all items in current itemDB

        // TODO: Method to remove items from item DB

        // clearFields();
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