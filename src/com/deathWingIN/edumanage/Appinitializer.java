package com.deathWingIN.edumanage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Initializes the JavaFX application by setting the primary stage and loading the initial scene.
 */
public class Appinitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load and set the login form as the initial scene of the application
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/deathWingIN/edumanage/view/LoginForm.fxml"))));
        primaryStage.show(); // Display the primary stage
    }
}