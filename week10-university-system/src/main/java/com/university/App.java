package com.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App extends Application {

    private ScheduledExecutorService scheduler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        // 2. Set up the Scene and Stage
        primaryStage.setTitle("University Management System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        // 3. LO3: Schedule a background task to run every 5 seconds
        scheduler = Executors.newScheduledThreadPool(1);

        Runnable heartbeat = () -> {
            // Print to the console every 5 seconds (simulating a background check)
            System.out.println("System Heartbeat: Checking for updates at " + new java.util.Date());
        };

        // Schedule the task: run after 0 seconds, then every 5 seconds
        scheduler.scheduleAtFixedRate(heartbeat, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}