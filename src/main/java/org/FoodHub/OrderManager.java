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
    parseOrder orderParser = new parseOrder();


    public OrderManager(){
    }

//    public void addOrder(Order order) {
//        allOrders.add(order);
//    }

    public void cancelOrder(Order order) {
        order.setStatus(3);
        allOrders.remove(order);
    }

    public void addOrder(String file){
        String orderType = null;
        long orderDate = 0;
        int status = 0;
        ArrayList<foodItem> items = new ArrayList<foodItem>();
        /// 0 = Incoming
        /// 1 = Start
        /// 2 = Complete
        /// 3 = Cancelled

        try {
            orderParser.parseFile(file);
            orderType = orderParser.getOrderType();
            orderDate = orderParser.getOrderTime();
            items = orderParser.getItems();

            orderID += 1;
            Order newOrder = new  Order(orderID, orderType, orderDate, status, items);
            allOrders.add(newOrder);

        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }
    }

    public void startIncomingOrder(Order theOrder) {
        theOrder.setStatus(1);
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
