package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// 0 = Incoming
/// 1 = Start
/// 2 = Completed
/// 3 = Cancel

public class OrderManager {
    private ArrayList<Order> allOrders = new ArrayList<Order>();
    private ArrayList<Order> completedOrders = new ArrayList<Order>();
    parseOrder orderParser = new parseOrder();
    private int orderID = 0;
    List<String> jsonFileOrders;


    public OrderManager(){
        fetechOrderFolderList();
        for (String theOrder : jsonFileOrders){
            addOrder(theOrder);
        }
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

    public void printOrder(int orderID){
        String getFileName = jsonFileOrders.get(orderID - 1);
        try {
            orderParser.parseFile(getFileName);
            orderParser.readOrdersFromJson();
        }
        catch (IOException|ParseException e){
            e.printStackTrace();
        }
    }

    public List<String> fetechOrderFolderList(){
        Path directory = Paths.get("orders");
        String extensions = ".json";

        try(Stream<Path> stream = Files.list(directory)){
            jsonFileOrders = stream.filter(file ->file.toString().endsWith(extensions)).map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            return jsonFileOrders;
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error");
            return List.of();
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
                System.out.println("Current Status: " + theOrder.getStatus());
            }
        }
    }

    public void completeOrder(int orderID){
        for (Order theOrder : allOrders){
            if (theOrder.getOrderId() == orderID){
                theOrder.setStatus(2);
                orderParser.writeOrderToJSON(theOrder);
                completedOrders.add(theOrder);
                allOrders.remove(theOrder);
            }
        }
    }

    public int getOrderStatus(int orderID){
        for (Order theOrder : allOrders){
            if (theOrder.getOrderId() == orderID){
                return theOrder.getStatus();
            }
        }
        return 1;
    }

}
