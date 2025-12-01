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

public class EditCollectionController {
    Stage stage;
    @FXML private Button editcollectionreturn_button;
    @FXML private Button editcollectionsubmit_button; // saved for later even if not used currently
    @FXML private MenuItem editcollectionquit_menu;
    @FXML private MenuItem editcollectionaboutlibrestock_menu;
    @FXML private MenuItem editcollectionnew_menu;
    @FXML private TextField editcollectioncollectionID_textfield;
    @FXML private TextField editcollectioncollectionname_textfield;
    @FXML private TextField editcollectioncollectiontype_textfield;
    @FXML private TextField editcollectionmaxsize_textfield;

    @FXML
    private void editcolreturnButtonClick() throws IOException{
        System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)editcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editcolsubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*Process is as follows:
        1.) Capture data from the entered fields
        1.5) Check if only Collection Name was entered - if so, perform a search for collection ID
        2.) If not searching, perform a match inquiry based on entered CollectionID
        3.) If matched, formulate query to submit changes and notify user of success (if not - give error message) */

        String enteredEditCollectionID = editcollectioncollectionID_textfield.getText();
        String enteredEditCollectionName = editcollectioncollectionname_textfield.getText();
        String enteredEditCollectionType = editcollectioncollectiontype_textfield.getText();
        String enteredEditCollectionSize = editcollectionmaxsize_textfield.getText();

         /*
        Check if looking up or submitting changes
        if(editcollectioncollectionID_textfield.isEmpty()){
            if(!editcollectioncollectionname_textfield.isEmpty()){
                String collectionIDFromDB = getCollectionID(enteredEditCollectionName);
                editcollectioncollectionID_textfield.setText(collectionIDFromDB);
                }}
        else{
        
         TODO: Look up in DB, match collection ID values

         TODO: If matched, perform update query on collection DB using entered values above
        
         showMessage("Query Matched! Changes were successful");
         newEditCollectionMenuClick();*/

    }

     @FXML
    public void quitEditCollectionMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openEditCollectionAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)editcollectionreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void newEditCollectionMenuClick() throws IOException{
        editcollectioncollectionID_textfield.clear();
        editcollectioncollectionname_textfield.clear();
        editcollectioncollectiontype_textfield.clear();
        editcollectionmaxsize_textfield.clear();
    }
}