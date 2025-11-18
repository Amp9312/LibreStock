package com.shogrenjacobdev.librestock;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            DbAccess db = new DbAccess();
            Inventory Inv = new Inventory(db);

            Item idTest = Inv.getItemById(1);
            Item skuTest = Inv.getItemBySku("ABC-DEF-GH");
            Item nameTest = Inv.getItemByName("Test Item");
            List<Item> namesTest = Inv.getItemsByName("Test Item");

            System.out.println("idTest = " + idTest.toString() + "\n" +
                    "skuTest = " + skuTest.toString() + "\n" +
                    "nameTest = " + nameTest.toString() + "\n" +
                    "namesTest = " + namesTest.toString()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
