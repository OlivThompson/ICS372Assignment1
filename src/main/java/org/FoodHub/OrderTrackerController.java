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
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
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
    private FetchFilesService fileListenerService;
    private Thread fileListenerThread;


    @FXML
    public TableView<Order> orderTable;
    @FXML
    public TableColumn<Order, Integer> idColumn;
    @FXML
    public TableColumn<Order, Integer> dateColumn;
    @FXML
    public TableColumn<Order, String> typeColumn;
    @FXML
    public TableColumn<Order, String> statusColumn;
    @FXML
    public ComboBox<OrderStatus> statusBox;
    @FXML
    public ComboBox<DeliveryStatus> deliveryStatusBox;
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
    public void initialize() throws IOException, ParseException, ParserConfigurationException, SAXException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("OrderTimeToText"));

        if (filePath.exists()) {
            allOrders = process.processSingleOrder("SavedDataForLoad.json");
            allOrders.addAll(process.processAllOrder());
            orderManager.setAllOrder(allOrders);
        }
        else
        {
            allOrders = process.processAllOrder();
            orderManager.setAllOrder(allOrders);
        }

        allOrdersList = FXCollections.observableArrayList(allOrders);
        orderTable.setItems(allOrdersList);
//        orderTable.setItems(FXCollections.observableArrayList(allOrders));
        List<OrderStatus> statuses = Arrays.asList(OrderStatus.values());
        statusBox.setItems(FXCollections.observableArrayList(statuses));
        saveData.save(orderManager, filePath);
        deliveryStatusBox.setItems(FXCollections.observableArrayList(DeliveryStatus.values()));
        deliveryStatusBox.setDisable(true);
        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateUIForSelectedOrder(newSelection);
        });

        fileListenerService = new FetchFilesService(allOrdersList, orderManager, process, accesser);
        fileListenerThread = new Thread(fileListenerService, "FileWatcherThread");
        fileListenerThread.setDaemon(true);
        fileListenerThread.start();


    }

    public void shutdown(){
        if (fileListenerService != null){
            fileListenerService.stop();
        }
    }

    /**
     * Updates UI components based on the currently selected order type.
     * Enables or disables the delivery status ComboBox.
     * @param selected The currently selected Order from the TableView.
     */
    private void updateUIForSelectedOrder(Order selected) {
        if (selected != null && selected.getOrderType() == OrderType.DELIVERY) {
            deliveryStatusBox.setDisable(false);
            deliveryStatusBox.setValue(selected.getDeliveryStatus());
        } else {
            deliveryStatusBox.setDisable(true);
            deliveryStatusBox.setValue(null);
        }

        if (selected != null) {
            statusBox.setValue(selected.getOrderStatus());
        }
    }

    public void handleUpdateStatus(ActionEvent actionEvent) {
        Order selected = orderTable.getSelectionModel().getSelectedItem();
        OrderStatus newStatus = statusBox.getValue();
        if (selected != null && newStatus != null) {
            orderManager.findOrder(selected.getOrderID()).setOrderStatus(newStatus);
            orderTable.refresh();
        }
        if (selected != null && !deliveryStatusBox.isDisable() && deliveryStatusBox.getValue() != null) {
            orderManager.findOrder(selected.getOrderID()).setDeliveryStatus(deliveryStatusBox.getValue());
        }
        orderTable.refresh();

        saveData.save(orderManager, filePath);
    }

}
