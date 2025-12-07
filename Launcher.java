package com.shogrenjacobdev.librestock;

import javafx.application.Application;

import java.io.IOException;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        // NOTE: If still in developent and user auth via DB is not yet developed, go to HelloController.java and manually set role mode
        Application.launch(HelloApplication.class, args);
    }
}
