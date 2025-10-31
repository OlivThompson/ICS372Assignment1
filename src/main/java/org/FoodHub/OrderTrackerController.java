package org.FoodHub;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackerController {
    public TableView<Order> orderTable;
    public TableColumn<Order, Integer> idColumn;
    public TableColumn<Order, String> typeColumn;
    public TableColumn<Order, String> statusColumn;
    public ComboBox<String> statusBox;
    public Button updateButton;
    public Button exportButton;

    OrderManager om;

    @FXML
    public void initialize() {
        om = new OrderManager();
        om.addOrder(new Order(new ArrayList<FoodItem>(), "Incoming", 100000L, "Delivery"));

        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrderID()).asObject());
        typeColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getOrderType()));
        statusColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        orderTable.setItems(FXCollections.observableArrayList(om.getOrders()));
        List<String> statuses = List.of("Incoming", "Started", "Completed", "Cancelled");
        statusBox.setItems(FXCollections.observableArrayList(statuses));
    }

    public void handleUpdateStatus(ActionEvent actionEvent) {
        Order selected = orderTable.getSelectionModel().getSelectedItem();
        String newStatus = statusBox.getValue();
        if (selected != null && newStatus != null) {
            om.findOrder(selected.getOrderID()).setStatus(newStatus);
            orderTable.refresh();
        }
    }

    public void handleExportOrders(ActionEvent actionEvent) {

    }
}
