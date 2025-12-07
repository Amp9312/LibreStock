package com.shogrenjacobdev.librestock;

import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class AdminDashController {
    Stage stage;
    @FXML private Button admindashcollectioncreation_button;
    @FXML private Button admindashcollectionediting_button;
    @FXML private Button admindashcollectiondeletion_button;
    @FXML private Button admindashexportinventory_button;
    @FXML private Button admindashimportinventory_button;
    @FXML private Button admindashlogout_button;

    @FXML private MenuItem adminnewitem_menu;
    @FXML private MenuItem adminedititem_menu;
    @FXML private MenuItem admindeleteitem_menu;
    @FXML private MenuItem adminnewuser_menu;
    @FXML private MenuItem adminedituser_menu;
    @FXML private MenuItem admindeleteuser_menu;
    @FXML private MenuItem admindashquit_menu;
    @FXML private MenuItem admindashaboutlibrestock_menu;
    @FXML private Label admindashwelcome_value;

    @FXML private Label admindashtotcal_value;
    @FXML private Label admindashtotitem_value;
    @FXML private Label admindashavgitem_value;


    @FXML
    private void colCreationButtonClick() throws IOException{
        /*open col creation view */
        //System.out.println("Opening collection creation scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addcollection-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashcollectioncreation_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void colEditingButtonClick() throws IOException{
        /*open col edit view */
        //System.out.println("Opening collection editing scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editcollection-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashcollectionediting_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void coldeletionButtonClick() throws IOException{
    /*open col deletion view */
        //System.out.println("Opening collection deletion scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deletecollection-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashcollectiondeletion_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

    @FXML
    private void exportinvenButtonClick() throws IOException{
    /*open export inventory view */
        //System.out.println("Opening export inventory scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exportinventory-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashexportinventory_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

    @FXML
    private void importinvenButtonClick() throws IOException{
    /*open import inventory view */
        //System.out.println("Opening import inventory scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importinventory-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashimportinventory_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

    @FXML
    private void adminlogoutButtonClick() throws IOException{
        //System.out.println("user logged out fr fr");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminNewItemMenuClick() throws IOException{
    
        //System.out.println("user opening make item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminnewitem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminEditItemMenuClick() throws IOException{
    
        //System.out.println("user opening edit item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminedititem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminDeleteItemMenuClick() throws IOException{
    
        //System.out.println("user deleting item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindeleteitem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminAddUserMenuClick() throws IOException{
    
        //System.out.println("admin opening make user");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adduser-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminRemoveUserMenuClick() throws IOException{
    
        //System.out.println("admin opening edit user");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteuser-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminEditUserItemMenuClick() throws IOException{
    
        //System.out.println("admin editing user");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edituser-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    public void quitAdminDashMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAdminDashAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }


    @FXML
public void initialize() {
    try {
        // --- 1) Set welcome name safely ---
        String firstName = CurrentUser.getFirstName();
        if (admindashwelcome_value != null) {
            if (firstName != null && !firstName.isEmpty()) {
                admindashwelcome_value.setText(firstName);
            } else {
                admindashwelcome_value.setText("");
            }
        }

        // --- 2) Load stats safely ---
        System.out.println("Dashboard is loading stats...");

        DbAccess db = new DbAccess();

        // total collections
        if (admindashtotcal_value != null) {
            List<Map<String, Object>> totColList =
                    db.runQuery("SELECT COUNT(*) FROM collections");
            Map<String, Object> totColMap = totColList.getFirst();
            Object totCol = totColMap.get("COUNT(*)");
            admindashtotcal_value.setText(totCol.toString());
        }

        // total items
        if (admindashtotitem_value != null) {
            List<Map<String, Object>> totItemList =
                    db.runQuery("SELECT COUNT(*) FROM items");
            Map<String, Object> totItemMap = totItemList.getFirst();
            Object totItem = totItemMap.get("COUNT(*)");
            admindashtotitem_value.setText(totItem.toString());
        }

        // average items per collection
        if (admindashavgitem_value != null) {
            HashMap<String, Integer> collectionCounts = new HashMap<>();
            List<Map<String, Object>> itemsResponse =
                    db.runQuery("SELECT quantity, collection FROM items");

            for (Map<String, Object> item : itemsResponse) {
                Integer itemQuantity = (Integer) item.get("quantity");
                String collectionName = item.get("collection").toString();

                collectionCounts.merge(collectionName, itemQuantity, Integer::sum);
            }

            if (!collectionCounts.isEmpty()) {
                int totalItems = 0;
                for (Integer i : collectionCounts.values()) {
                    totalItems += i;
                }
                int avgItem = totalItems / collectionCounts.size();
                admindashavgitem_value.setText(Integer.toString(avgItem));
            } else {
                admindashavgitem_value.setText("0");
            }
        }

    } catch (Exception e) {
        System.out.println("Error in AdminDashController.initialize: " + e);
        e.printStackTrace();
        // We swallow the exception so the FXML still loads instead of crashing.
    }
}
    
                
    
}