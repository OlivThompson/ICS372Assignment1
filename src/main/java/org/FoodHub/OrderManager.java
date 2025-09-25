package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.stream.Stream;

/// 0 = Incoming
/// 1 = Start
/// 2 = Completed

public class OrderManager {
    private ArrayList<Order> allOrders = new ArrayList<Order>();
    parseOrder orderParser = new parseOrder();
    private int orderID = 0;


    public OrderManager(){
    }

    public void cancelOrder(Order order) {
        order.setStatus(3);
        allOrders.remove(order);
    }

    public void addOrder(String file){
        Order newOrder = new Order(file);
        orderID++;
        newOrder.setOrderId(orderID);
        allOrders.add(newOrder);
    }

    public void startIncomingOrder(int orderID) {
        for (Order theOrder : allOrders){
            if (theOrder.getOrderId() == orderID){
                theOrder.setStatus(1);
            }
        }
        System.out.println("Order#" + orderID + " has been started");
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

    public ArrayList<Integer> getAllOrders(){
        ArrayList<Integer> orderIDs = new ArrayList<Integer>();
        for (Order theOrder : allOrders){
            orderIDs.add(theOrder.getOrderId());
        }
        return orderIDs;
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

    public void readOrderInfo(String theFileOrder){
        try {
            orderParser.parseFile(theFileOrder);
        }catch (IOException|ParseException e){
            e.printStackTrace();
        }
        orderParser.readOrdersFromJson();
    }

    public void printOrderStatus(int orderID){
        for (Order theOrder : allOrders){
            if (theOrder.getOrderId() == orderID){
                System.out.println(theOrder.getStatus());
            }
        }
    }
}
