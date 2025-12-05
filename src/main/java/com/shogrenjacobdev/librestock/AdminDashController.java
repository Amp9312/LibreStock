package com.shogrenjacobdev.librestock;

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

    @FXML private Label admindashtotcal_value;
    @FXML private Label admindashtotitem_value;
    @FXML private Label admindashavgitem_value;
    @FXML private Label admindashwelcome_value;


    @FXML
    private void colCreationButtonClick() throws IOException{
        /*open col creation view */
        System.out.println("Opening collection creation scene");
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
        System.out.println("Opening collection editing scene");
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
        System.out.println("Opening collection deletion scene");
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
        System.out.println("Opening export inventory scene");
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
        System.out.println("Opening import inventory scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importinventory-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashimportinventory_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

    @FXML
    private void adminlogoutButtonClick() throws IOException{
    /*log user out fr fr*/
        System.out.println("user logged out fr fr");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminNewItemMenuClick() throws IOException{
    
        System.out.println("user opening make item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminnewitem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminEditItemMenuClick() throws IOException{
    
        System.out.println("user opening edit item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminedititem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminDeleteItemMenuClick() throws IOException{
    
        System.out.println("user deleting item");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admindeleteitem-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminAddUserMenuClick() throws IOException{
    
        System.out.println("admin opening make user");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adduser-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminRemoveUserMenuClick() throws IOException{
    
        System.out.println("admin opening edit user");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteuser-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
}

@FXML
    private void adminEditUserItemMenuClick() throws IOException{
    
        System.out.println("admin editing user");
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
        System.out.println("User Opened Docs...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)admindashlogout_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    // NOTE: This runs after the window has been opened/set-up. If something needs to access the fxml properties after opening the window, it gets done here. 
    @FXML
    public void initialize(){
        System.out.println("Dashboad is loading stats...");

        try {
            DbAccess db = new DbAccess();
            List<Map<String, Object>> totColList = db.runQuery("SELECT COUNT(*) FROM collections");
            Map<String, Object> totColMap = totColList.getFirst();
            Object totCol = totColMap.get("COUNT(*)");

            System.out.println(totCol.toString());
            admindashtotcal_value.setText(totCol.toString());

            List<Map<String, Object>> totItemList = db.runQuery("SELECT COUNT(*) FROM items");
            Map<String, Object> totItemMap = totItemList.getFirst();
            Object totItem = totItemMap.get("COUNT(*)");

            admindashtotitem_value.setText(totItem.toString());

            // Run sql query to get the quantity and collection of all items
            // Create empty dictionary
            // use collection as key and amount of items as data
            // go through sql response, get the collection, check if collection is key in new dict, if not create a new entry, if so add to the running total
            // Once done, go through new dict and add up data and get an average
            HashMap<String, Integer> collectionCounts = new HashMap<>();
            List<Map<String, Object>> itemsResponse = db.runQuery("select quantity, collection from items");

            for (Map<String, Object> item : itemsResponse) {
                Integer itemQuantity = (Integer) item.get("quantity");

                if (!collectionCounts.containsKey(item.get("collection").toString())) {
                    collectionCounts.put(item.get("collection").toString(), itemQuantity);
                }
                else {
                    Integer loadedCount = collectionCounts.get(item.get("collection").toString());
                    loadedCount += itemQuantity;

                    collectionCounts.put(item.get("collection").toString(), loadedCount);
                }
            }

            System.out.println(collectionCounts.toString());
            
            Integer totalItems = 0;
            for (Integer i : collectionCounts.values()) {
                totalItems += i;
            }

            Integer avgItem = totalItems / collectionCounts.size();
            System.out.println(avgItem.toString());
            admindashavgitem_value.setText(avgItem.toString());

        } catch (SQLException e) {
            e.getMessage();
        }
        /*Process should work as follows:
        1.) Create DB query for the following values: 
            a.) Total number of collections 
                i.) Just a count on the number of values in the collection table (call it value totCol)
            b.) Total number of items
                i.) Just a count on the number of values in the item table (call it value totItem)
            c.) Average number of items in collection
                i.) This might be a bit tough - lets come back this or replace with something else
        2.) Populate these values
        
        admindashtotcal_value.setText(totCol);
        admindashtotitem_value.setText(totItem);
        admindashavgitem_value.setText(avgItem);        
                
                */
                
    }
}