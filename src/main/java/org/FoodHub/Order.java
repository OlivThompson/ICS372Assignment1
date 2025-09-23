package org.FoodHub;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<FoodItem> foodItems = new ArrayList<>();
    private String orderStatus;
    private LocalDateTime orderTime;
    private String jsonOrderData;

    public Order(int orderId, ArrayList <FoodItem> foodItems, String orderStatus,
                 LocalDateTime orderTime, String jsonOrderData) {
        this.orderId = orderId;
        this.foodItems = foodItems;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.jsonOrderData = jsonOrderData;
    }

    public int getOrderId(){
        return orderId;
    }

    public double getTotalPrice(){
        double totalPrice = 0;
        for (Object foodItem : foodItems) {
            totalPrice += foodItem.getPrice();
        }
        return totalPrice;
    }
    public int getStatus(){
        try {
            return Integer.parseInt(orderStatus);
        } catch(NumberFormatException e) {
            return -1;
        }
    }

    public String getJson(){
        return  jsonOrderData;
    }

}
