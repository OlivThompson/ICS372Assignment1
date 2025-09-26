package org.FoodHub;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private String orderStatus;
    private Long orderTime;
    private String orderType;

    public Order(int orderId, List<FoodItem> foodItems, String orderStatus,
                 Long orderTime, String orderType) {
        this.orderId = orderId;
        this.foodItems = foodItems;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderType = orderType;
    }

    public int getOrderId(){
        return orderId;
    }

    public String getStatus(){
        return this.orderStatus;
    }

    public void printOrderItems(){
        System.out.println(foodItems);
    }

    public void setStatus(String status) {
        this.orderStatus = status;
    }

    public double totalPrice() {
        double totalPrice = 0;
        for( FoodItem i : this.foodItems) {
            totalPrice += (i.getPrice() * i.getQuantity());
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return String.format("ID:%d,  Status:%s, Type:%s, Price:%.2f, OrderTime:%d, ItemsOrdered:%s", this.getOrderId(), this.getStatus(), this.getType(), this.totalPrice(), this.orderTime, this.foodItems);
    }

    private String getType() {
        return this.orderType;
    }

}

