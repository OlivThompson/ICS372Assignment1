package org.FoodHub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class OrderTrackerApplication extends Application {

//    OrderManagerInterface orderManagerInterface = new OrderManagerInterface();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderTrackerView.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Restaurant Order Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
