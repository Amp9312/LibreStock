package com.shogrenjacobdev.librestock;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DocumentationController{
    Stage stage;
    @FXML private MenuItem docsaboutlibrestockopendocs_menu; // saved for later even if not used currently
    @FXML private MenuItem documentationquit_menu;
    @FXML private TreeView<String> documentation_treeview;
    @FXML private Button documentationreturn_button;
    @FXML private AnchorPane docsAnchorPane;
    @FXML private TextArea documentation_textarea;

    @FXML
    public void initialize() {
        // Initialization logic for the documentation view goes here

        TreeItem<String> root, item1, item2;

        root = new TreeItem<>();
        root.setExpanded(true);
        //testMakeBranch();
        //item1 is the user documentation
        item1=makeBranch("User Documentation", root);
        makeBranch("Logging In", item1);
        makeBranch("Making an Item",item1);
        makeBranch("Editing an Item",item1);

        //item2 is the admin documentation
        item2=makeBranch("Admin User Documentation", root);
        makeBranch("Your Role as Admin", item2);
        makeBranch("Deleting An Item", item2);
        makeBranch("Adding a Collection",item2);
        makeBranch("Deleting a Collection",item2);
        makeBranch("Editing a Collection",item2);
        makeBranch("Adding Users",item2);
        makeBranch("Deleting Users",item2);
        makeBranch("Editing Users",item2);
        makeBranch("Exporting DB",item2);
        makeBranch("Importing DB",item2);

        // attach the tree to the injected TreeView control instead of creating a new one
        documentation_treeview.setRoot(root);
        documentation_treeview.setShowRoot(false);

        documentation_treeview.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null) return;
            String val = newValue.getValue();
            if (val == null) return;

            switch (val) {
                case "Logging In":
                    documentation_textarea.setText("To log in, enter your username and password on the login screen and click the 'Login' button.\n\nIf you encounter an error, double check that you are entering the correct username and password.\n\nUsernames and passwords are case sensitive. Check with your system admin if you continue having issues logging in. ");
                    break;
                case "Making an Item":
                    documentation_textarea.setText("To make an item, enter data in the corresponding fields such as name, collection, quantity, and the SKU (if needed) of the item.\n\nVerify that the data entered correctly represents the item being added to the inventory.\n\nKeep in mind that the collection must match an existing collection available within the inventory. If given collection entry errors, double check that you are spelling the collection correctly, or verify with an Admin User that the collection exists.\n\nWhen the correct data is filled in, press 'submit' to try adding this new item to the inventory.");
                    break;
                case "Your Role as Admin":
                    documentation_textarea.setText("Welcome to LibreStock. Your role as admin will be to perform backups of the inventory using export and import tools, delete erroneous items, manage other non admin user accounts, and add, edit, and delete collections for standard users to put items in.\n\nAdmin accounts must be present in order for LibreStock to work, as some operations require an admin password check to process.\n\nAs a result, if there are no user accounts present in the database, the ‘First Time Setup’ button on the log-in screen will enable. Use this button to create a “default” admin account to manage the application.\n\nIt is also very important that you track your passwords. Unlike online software, LibreStock runs 100% offline and cannot enable you to remotely reset your password. If all admin accounts lose access to their privileges, you are effectively locked out of LibreStock.\n\nFor this reason, keep the “default” admin account present in the application, and store it’s log-in credentials in a safe place so your organization always has access to your inventory data. ");
                    break;
                
                case "Editing an Item":
                    documentation_textarea.setText("To edit an item, first you must know the itemID to perform a lookup.\n\nIf you do not know the itemID, you can perform a reverse lookup by entering the Item Name (keep in mind that the Item Name is case sensitive and must match exactly).\n\nOnce the itemID or Item Name is entered, press the ‘search’ button. If a match is found, the other fields will populate with information about that data.\n\nTo modify this data, simply enter the new data as part of the textfields presented.\n\nKeep in mind that when editing an item, the itemID field will be locked. This prevents the accidental editing of any other items, as well as preserves the database integrity constraints of each item always having a unique ID.\n\nTo also preserve database integrity, items cannot be moved from one collection to another. To perform this change correctly, remove the item with the wrong collection and add a new item with the new collection value. Ensure this mistake is made minimally, as each time an item is remade their ID is changed.\n\nIf you looked up the wrong item, open the ‘file’ menu and click ‘new’ to reset your searching capabilities.\n\nOnce the data inside each text field accurately represents this item, press the ‘submit’ button to save your changes about this item to the inventory. ");
                    break;
                case "Deleting An Item":
                    documentation_textarea.setText("In order to delete items, you must know the item ID of the item you wish to delete. If unsure, return to menu and select edit item to perform a reverse lookup.\n\nOnce an item ID is entered, press ‘search’ to perform a lookup for that item. If a match is found, the other textfields will populate to show you the rest of the information about that item.\n\nThe other fields present are not editable, and only for displaying of data.\n\nOnce you are sure that this is the item you would like to delete, select ‘DELETE’.\n\nIf this is not the item you would like to delete, return to admin dashboard and re open delete item. This will reset the fields.");
                    break;
                case "Adding a Collection":
                    documentation_textarea.setText("In order to create a new collection, enter the collection name, collection type, and collection size.\n\nIf you are unsure what to set for size, it can be left blank or 0 for no size limit, although we recommend setting one.\n\nUsing edit collection, collection max size can be expanded if needed.\n\nOnce the details entered are correct, press ‘submit’ to create a new collection.\n\nWARNING: Use reverse lookup in edit collection to double check that a similarly named collection does not already exist. You can create two collections with the same name, but have different IDs. To prevent this issue, ensure that your collections always have a unique name and purpose.");
                    break;
                case "Deleting a Collection":
                    documentation_textarea.setText("In order to delete a collection, you must know the collection ID of the collection you wish to delete.\n\nIf unsure, return to menu and select edit collection to perform a reverse lookup. This will enable you to find the ID for that collection name.\n\nOnce you have entered the right ID, press ‘Search’ to find a collection with that ID.\n\nIf successful, the fields will populate with more information about that collection to verify that this the collection being deleted.\n\nWARNING: Collection deletion IS RECURSIVE. Meaning that if you delete a collection, it will also recursively delete every item that belongs to that collection. Double check that you are deleting the right collection, as the only means to come back from this is to perform an Import DB process with the latest backup file.");
                    break;
                case "Editing a Collection":
                    documentation_textarea.setText("To edit a collection, first you must know the collection ID to perform a lookup.\n\nIf you do not know the collection ID, you can perform a reverse lookup by entering the Collection Name (keep in mind that the Collection Name is case sensitive and must match exactly).\n\nOnce the collection ID or Collection Name is entered, press the ‘search’ button. If a match is found, the other fields will populate with information about that data.\n\nTo modify this data, simply enter the new data as part of the textfields presented.\n\nKeep in mind that when editing an item, the Collection ID field will be locked. This prevents the accidental editing of any other items, as well as preserves the database integrity constraints of each item always having a unique ID.\n\nIf you looked up the wrong collection, open the ‘file’ menu and click ‘new’ to reset your searching capabilities.\n\nOnce the data inside each text field accurately represents this collection, press the ‘submit’ button to save your changes about this collection to the inventory.");
                    break;
                case "Adding Users":
                    documentation_textarea.setText("To add a user, simply fill in the text fields with information about that user account.\n\nTo make a new admin account, ensure that the ‘Upgrade Account’ checkbox is marked.\n\nOnce the details are entered, press ‘submit’ and a new user account will be made.");
                    break;
                case "Deleting Users":
                    documentation_textarea.setText("To delete a user, you must know the user ID for that account. If user ID is unknown, return to menu and use edit user to reverse lookup their user ID.\n\nConfirm your admin password and hit submit to delete.\n\nWARNING: Ensure that the correct user account is being removed as this change is permanent.");
                    break;
                case "Editing Users":
                    documentation_textarea.setText("To edit a user, you must know their user ID. If not known, you can perform a reverse lookup using the username of the account.\n\nOnce you have either the ID or username entered, enter your changes you would like to make to the information about that account.\n\nWARNING: If you are editing the default admin account made using first time set up, DO NOT uncheck “Upgrade Account”, especially if this is the only admin account in this software. Doing so will lock you out of any administrative menus permanently.\n\nYou can also reset the passwords of your own account or others using this view. Handle this functionality responsibly.\n\nOnce changes are complete, click ‘submit’ to process changes for that user account.");
                    break;
                case "Exporting DB":
                    documentation_textarea.setText("To back up your inventory, you will need to know your admin username and admin password.\n\nOnce these details are confirmed, enter the name you would like to use for your export file (keep in mind that this value is case sensitive).\n\nOnce a name is entered, simply press ‘Submit’ to have a .CSV file made representing all data inside the inventory database.\n\nWARNING: This DOES NOT export user accounts. If a user account is deleted by mistake, it is gone forever and a new one will need made.");
                    break;
                case "Importing DB":
                    documentation_textarea.setText("To perform an inventory import, ensure that an exported ‘.CSV’ file is within the /export directory of this instance of LibreStock.\n\nCopy the entire name of this export directory from the properties of the .CSV file.\n\nPaste this file name into the ‘Name of Import File’ field.\n\nConfirm your admin username and password.\n\nOnce you are certain these details are correct, press ‘Submit’ to overwrite your current inventory with this import file.\n\nWARNING: This WILL overwrite the entire collection and item inventory database. It is recommended that an additional export is made to revert to in the case of import failure or data corruption.");
                    break;
                default:
                    documentation_textarea.setText(val);
            }
            //System.out.println(val);
        });
    }

    //helper function that binds the tree items
    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    //unit test for helper function makeBranch
    /*public void testMakeBranch(){
        TreeItem<String> testRoot = new TreeItem<>("Root");
        TreeItem<String> child = makeBranch("Child", testRoot);
        assert child.getValue().equals("Child") : "makeBranch failed to set correct value";
        assert testRoot.getChildren().contains(child) : "makeBranch failed to add child to parent";
        System.out.println("makeBranch test passed.");
    }*/

    @FXML
    private void documentationReturnButtonClick() throws IOException{
        //System.out.println("Returning to about librestock scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librestockdocs.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)documentationreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void documentationTreeSelectItem() throws IOException{
        System.out.println("User Selected Item...");
        // Logic for selecting items from the treeview and displaying content goes here
        
    }
    
    @FXML
    public void quitDocumentationMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }
}