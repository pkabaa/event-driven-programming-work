package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        primaryStage.setTitle("Event-Driven Calculator (JavaFX)");
        primaryStage.setScene(new Scene(root, 350, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}