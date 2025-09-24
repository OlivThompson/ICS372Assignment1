package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.stream.Stream;

public class OrderManager {
    private ArrayList<Order> allOrders = new ArrayList<Order>();
    private static int orderID = 0;
    private String orderType;
    private long orderDate;
    private int status = 0;  /// By default
    /// 0 = Incomplete/Start
    /// 1 = Complete
    /// 2 = Cancelled
    parseOrder orderParser = new parseOrder();


    public OrderManager(){
    }

//    public void addOrder(Order order) {
//        allOrders.add(order);
//    }

    public void cancelOrder(Order order) {
        order.setStatus(2);
        allOrders.remove(order);
    }

    public void startIncomingOrder(String file) {
        orderID += 1;
        try {
            orderParser.parseFile(file);
            orderType = orderParser.getOrderType();
            orderDate = orderParser.getOrderTime();
        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }
        Order newOrder = new Order(orderID, orderType, orderDate, status);
        allOrders.add(newOrder);
    }

    public ArrayList<Order> getCompletedOrders() {
        ArrayList<Order> completedOrders = new ArrayList<Order>();
        for (Order theOrder : allOrders){
            if (theOrder.getStatus() == 1){
                completedOrders.add(theOrder);
            }
        }
        return completedOrders;
    }

    public ArrayList<Order> getIncompleteOrders() {
        ArrayList<Order> incompleteOrders = new ArrayList<Order>();
        for (Order theOrder : allOrders){
            if (theOrder.getStatus() == 0){
                incompleteOrders.add(theOrder);
            }
        }
        return incompleteOrders;
    }

    public void exportAllOrders() {
        orderParser.writeAllOrderToFile(allOrders);
    }

    public ArrayList<Order> getAllOrders(){
        return allOrders;
    }

    public void printIncomingOrder(){
        orderParser.readOrdersFromJson();
    }

    public void printOrderList(){
        Path directory = Paths.get("orders");
        String extensions = ".json";

        try(Stream<Path> stream = Files.list(directory)){
            stream.filter(file ->file.toString().endsWith(extensions)).forEach(file -> System.out.println(file.getFileName()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readOrderInfo(){
        orderParser.readOrdersFromJson();
    }
}
