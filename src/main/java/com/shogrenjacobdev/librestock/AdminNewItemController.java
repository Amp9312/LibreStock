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

        // TODO: Match collection name against all collection names in collection DB

        // TODO: Create collection (or inherit method from add collection controller?)

        // TODO: Create item
        // showMessage("Item Made! Press Enter to Continue");
        // clearFields();
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