package org.FoodHub;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<foodItem> foodItems = new ArrayList<foodItem>();
    private int orderStatus;
    private long orderTime;
    private String orderType;
    private final parseOrder orderParser = new parseOrder();

    public Order(String file){
        try{
            orderStatus = 0;
            orderParser.parseFile(file);
            orderType = orderParser.getOrderType();
            orderTime = orderParser.getOrderTime();
            foodItems = orderParser.getItems();
        }catch(IOException| ParseException e){
            e.printStackTrace();
        }
    }

    public int getOrderId(){
        return orderId;
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for (foodItem item : foodItems){
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void setStatus(int status){
        orderStatus = status;
    }

    public int getStatus() {
        return orderStatus;
    }

    public ArrayList<foodItem> getFoodList(){
        return foodItems;
    }

    public String getOrderType(){
        return orderType;
    }

    public long getOrderTime(){
        return orderTime;
    }

    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

}
