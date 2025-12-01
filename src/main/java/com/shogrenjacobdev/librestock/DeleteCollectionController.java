package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteCollectionController {
    Stage stage;
    @FXML private Button deletecollectionreturn_button;
    @FXML private Button deletecollectiondelete_button; // saved for later even if not used currently
    @FXML private Button deletecollectionsearch_button; // saved for later even if not used currently
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

        // TODO: Search for match in itemDB using itemID

        /*
        Populate fields once a match is found (note: methods listed here are not yet made)
        String delCollectionName = getItemNameFromDB();
        String delNumberOfItems = getNumberOfItemsFromDB();
        String delMaxSize = getMaxSizeFromDB();
        String delCollectionType = getCollectionTypeFromDB();

        deletecollectioncollectionname_textfield.setText(delCollectionName);
        deletecollectionnumitems_textfield.setText(delNumberOfItems);
        deletecollectionmaxsize_textfield.setText(delMaxSize);
        deletecollectioncollectiontype_textfield.setText(delCollectionType);
        */

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
        
        String enteredDelItemID = deletecollectioncollectionID_textfield.getText();
    
        // TODO: Method to match collection ID above with all collections in current collection DB

        // TODO: Method to identify and remove items that have this collection

        // TODO: Method to remove collection from collection DB

        // clearDelColFields();
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