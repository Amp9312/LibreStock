package com.shogrenjacobdev.librestock;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("login-scene.fxml"));
        stage.setTitle("Welcome to LibreStock! A Free Open Source Inventory Software");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}
