package org.FoodHub;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<foodItem> foodItems = new ArrayList<foodItem>();
    private int orderStatus;
    private long orderTime;
    private String orderType;
    private static final parseOrder orderParser = new parseOrder();

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

//    public void getOrderItemFromJSON(ArrayList<foodItem> foodItem){
//        this.foodItems.addAll(foodItem);
//    }

    public int getOrderId(){
        return orderId;
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for (foodItem item : foodItems){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    public void setStatus(int status){
        orderStatus = status;
    }

    public int getStatus(){
        return orderStatus;
    }

//    public void printOrderItems(){
//        System.out.println(foodItems);
//    }

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
