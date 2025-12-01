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
        System.out.println("Returning to user dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userdash-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)newitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newitemSubmitButtonClick() throws IOException{
        System.out.println("submitting data fr fr");
        /*This process works as follows:
        1.) Capture input from fields
        2.) Match collection with existing collection name 
        2.5) If no existing collection, one is made - creating new entry in collectionDB
        3.) Create new item record in item DB*/

        String enteredItemName = newitemitemname_textfield.getText();
        String enteredCollection = newitemcollection_textfield.getText();
        String enteredQuantity = newitemquantity_textfield.getText();
        String enteredSKU = newitemsku_textfield.getText();

        // TODO: Match collection name against all collection names in collection DB

        // TODO: Create collection (or inherit method from add collection controller?)

        // TODO: Create item
        // showMessage("Item Made! Press Enter to Continue");
        // clearFields();
    }

    @FXML
    public void quitNewItemMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openNewItemAboutMenuClick() throws IOException{
        System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)newitemreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}