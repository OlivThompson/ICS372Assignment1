package org.FoodHub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
/**
 * Main application class for the FoodHub order tracker.
 * Loads main UI from the FXML file.
 * Sets window title and size.
 * Shows the primary stage.
 * When stopped, it shows the background services
 */
public class OrderTrackerApplication extends Application {
    private OrderTrackerController controller;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the main window for the application
     * @throws Exception if loading the FXML resource fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderTrackerView.fxml")));
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Restaurant Order Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Called when the application is stopping.
     */
    @Override
    public void stop(){
        System.out.println("Ping Service Shutting Down");
        if (controller != null){
            controller.shutdown();
        }
    }
}
