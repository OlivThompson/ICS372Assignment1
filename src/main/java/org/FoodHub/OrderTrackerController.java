package org.FoodHub;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller for tracking and updating Orders in the UI.
 */
public class OrderTrackerController {
    public TableView<Order> orderTable;
    public TableColumn<Order, Integer> idColumn;
    public TableColumn<Order, String> typeColumn;
    public TableColumn<Order, String> statusColumn;
    public ComboBox<OrderStatus> statusBox;
    public Button updateButton;
    public Button exportButton;

    OrderManager om;

    /**
     * Initializes the Order Tracker Controller.
     * Sets up the TableView columns and sample data.
     */
    @FXML
    public void initialize() {
        om = new OrderManager();
        om.addOrder(new Order(new ArrayList<FoodItem>(), OrderStatus.INCOMING, 100000L, OrderType.DELIVERY));

        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrderID()).asObject());
        typeColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getOrderType().toString()));
        statusColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getOrderStatus().toString()));

        orderTable.setItems(FXCollections.observableArrayList(om.getOrders()));
        List<OrderStatus> statuses = Arrays.asList(OrderStatus.values());
        statusBox.setItems(FXCollections.observableArrayList(statuses));
    }

    /**
     * Handles updating the status of a selected Order.
     *
     * @param actionEvent The action event triggered by button press.
     */
    public void handleUpdateStatus(ActionEvent actionEvent) {
        Order selected = orderTable.getSelectionModel().getSelectedItem();
        OrderStatus newStatus = statusBox.getValue();
        if (selected != null && newStatus != null) {
            om.findOrder(selected.getOrderID()).setOrderStatus(newStatus);
            orderTable.refresh();
        }
    }

    /**
     * Handles exporting Orders (stub function).
     *
     * @param actionEvent The action event triggered by button press.
     */
    public void handleExportOrders(ActionEvent actionEvent) {

    }
}

