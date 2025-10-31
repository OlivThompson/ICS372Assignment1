package org.FoodHub;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
import javafx.scene.image.Image;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    public TableColumn<Order, String> deliveryStatusColumn;
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
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("OrderTimeToText"));
        deliveryStatusColumn.setCellValueFactory(order ->
                new SimpleStringProperty(order.getValue().getDeliveryStatus() != null ?
                        order.getValue().getDeliveryStatus().toString() : ""));
        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOrderType().toString()));

        typeColumn.setCellFactory(column -> new TableCell<Order, String>() {
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();
            private final javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5);

            {
                hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                hbox.getChildren().addAll(imageView, label);
            }

            /**
             * Updates the cell display with an order type icon and label.
             * Loads appropriate icon from classpath
             * Uses text only display if icon resource is not found.
             *
             * @param item the order type string value to display
             * @param empty true if the cell is empty, false otherwise
             */
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setGraphic(null);
                } else {
                    Order order = getTableRow().getItem();
                    if (order != null) {
                        OrderTypeIcon icon = order.getOrderTypeIcon();
                        label.setText(order.getOrderType().toString());
                        try {
                            InputStream imageStream = getClass().getResourceAsStream(icon.getImagePath());
                            if (imageStream != null) {
                                imageView.setImage(new Image(imageStream));
                                imageView.setFitWidth(30);
                                imageView.setFitHeight(30);
                                setGraphic(hbox);
                            } else {
                                label.setText(order.getOrderType().toString());
                                setGraphic(label);
                            }
                        } catch (Exception e) {
                            label.setText(order.getOrderType().toString());
                            setGraphic(label);
                        }
                    }
                }
            }
        });


        deliveryStatusColumn.setCellValueFactory(order ->
                new SimpleStringProperty(order.getValue().getDeliveryStatus() != null ?
                        order.getValue().getDeliveryStatus().toString() : ""));

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
