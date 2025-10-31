package org.FoodHub;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OrderTrackerController {
    private OrderProcessor process = new OrderProcessor();
    private OrderManager orderManager = new OrderManager();
    private ObservableList<Order> allOrdersList;
    private SaveState saveData = new jsonSaveData(orderManager);
    private File filePath = new File("SavedDataForLoad.json");
    private FileAccesser accesser = new FileAccesser();
    private List<Order> allOrders;

    @FXML
    public TableView<Order> orderTable;
    @FXML
    public TableColumn<Order, Integer> idColumn;
    @FXML
    public TableColumn<Order, String> typeColumn;
    @FXML
    public TableColumn<Order, String> statusColumn;
    @FXML
    public ComboBox<OrderStatus> statusBox;
    @FXML
    public Button updateButton;
    @FXML
    public Button exportButton;

    public void handleExportOrders(ActionEvent actionEvent) {

    }

    /**
     * Will initialize with SavedDataForLoad.json being processed
     *
     *
     * **/
    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        if (filePath.exists()) {
            allOrders = process.processSingleOrder("SavedDataForLoad.json");
            orderManager.setAllOrder(allOrders);
        }
        else
        {
            allOrders = process.processAllOrder();
            orderManager.setAllOrder(allOrders);
        }

        allOrdersList = FXCollections.observableArrayList(allOrders);
        orderTable.setItems(allOrdersList);
        orderTable.setItems(FXCollections.observableArrayList(allOrders));
        List<OrderStatus> statuses = Arrays.asList(OrderStatus.values());
        statusBox.setItems(FXCollections.observableArrayList(statuses));
        saveData.save(orderManager, filePath);
    }

    public void handleUpdateStatus(ActionEvent actionEvent) {
        Order selected = orderTable.getSelectionModel().getSelectedItem();
        OrderStatus newStatus = statusBox.getValue();
        if (selected != null && newStatus != null) {
            orderManager.findOrder(selected.getOrderID()).setOrderStatus(newStatus);
            orderTable.refresh();
        }
        saveData.save(orderManager, filePath);
    }

}
