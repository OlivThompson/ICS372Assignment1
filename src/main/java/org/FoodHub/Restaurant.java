package org.FoodHub;

import org.json.simple.parser.ParseException;


import java.io.*;
import java.util.List;

/*
* Domain Layer
*
*
* */

public class Restaurant implements Serializable{
    // Serialized Version for compatibility
    private static long serialVersionUID = 1L;
    private final String name;
    private final OrderManager orderManager;
    // Need Parser to be transient
    private transient OrderParser orderParser = OrderParser.getInstance();

    // Constructor
    public Restaurant(String name){
        this.name = name;
        this.orderManager = new OrderManager();
        this.createRestaurantDirectory();
    }

    public void addOrder(String filePath) throws IOException, ParseException{
        Order order = orderParser.readOrderFromJson(filePath);
        orderManager.addOrder(order);
    }

    public void cancelOrder(int orderID){
        orderManager.cancelOrder(orderID);
    }

    public void startOrder(int orderID){
        orderManager.startIncomingOrder(orderID);
    }

    public void completeOrder(int orderID){
        orderManager.completeIncomingOrder(orderID);
        Order completedOrder = orderManager.findOrder(orderID);
        orderParser.writeOrderToJSON(completedOrder);
    }

    public List<Order> getAllOrder() {
        return orderManager.getOrders();
    }

    public void exportAllOrders(){
        orderParser.writeAllOrderToFile(getAllOrder());
    }

    public Order getOrder(int orderID){
        return orderManager.findOrder(orderID);
    }

    public void createRestaurantDirectory(){
        File restaurantDirectory = new File("Restaurants/" + name);
        if (!restaurantDirectory.exists()){
            restaurantDirectory.mkdirs();
            System.out.println("Restaurant Directory Created Successfully");
        }
        else{
            System.out.println("Directories has been loaded");
        }
    }

    public void displayOrder(int orderID){
        orderManager.displayOrder(orderID);
    }

    public String getName(){
        return this.name;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.orderParser = OrderParser.getInstance();
    }

}
