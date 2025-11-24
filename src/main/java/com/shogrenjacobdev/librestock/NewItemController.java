package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class NewItemController {
    Stage stage;
    @FXML private Button newitemreturn_button;
    @FXML private Button newitemsubmit_button;
    @FXML private MenuItem newitemquit_menu;
    @FXML private MenuItem newitemaboutlibrestock_menu;

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
        /* put craaaaazy submit logic here later (some sort of update method for items in the db) */
    }

        @FXML
    private void newitemSearchButtonClick() throws IOException{
        System.out.println("searching data fr fr");
        /* put craaaaazy search logic here later (some sort of search method for items in the db) */
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