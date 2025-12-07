package com.shogrenjacobdev.librestock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.io.IOException;

public class AboutLibreStockController {
    Stage stage;
    @FXML private Button docsreturn_button;
    @FXML private Button docsdocs_button; // saved for later even if not used currently
    @FXML private MenuItem aboutlibrestockopendocs_menu; // saved for later even if not used currently
    @FXML private MenuItem aboutlibrestockquit_menu;

    @FXML
    private void aboutLibreReturnButtonClick() throws IOException{
        //System.out.println("Returning to admin dash scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)docsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void aboutLibreDocsButtonClick() throws IOException{
        //System.out.println("Opening documentation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("documentation-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)docsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void quitAboutLibreMenuClick() throws IOException{
        javafx.application.Platform.exit();
    }

    @FXML
    public void openAboutMenuClick() throws IOException{
        //System.out.println("User Opened Docs...");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("documentation-scene.fxml"));
        Parent root = loader3.load();

        Stage stage = (Stage)docsreturn_button.getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}