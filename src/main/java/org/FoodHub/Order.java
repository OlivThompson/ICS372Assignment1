package org.FoodHub;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderID = 0;
    private int orderId;
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private String orderStatus;
    private Long orderTime;
    private String orderType;

    public Order(List<FoodItem> foodItems, String orderStatus,
                 Long orderTime, String orderType) {
        this.orderId = Order.orderID++;
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
    public List<FoodItem> getFoodItems() {
        return this.foodItems;
    }
    public void setStatus(String status) {
        this.orderStatus = status;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for( FoodItem i : this.foodItems) {
            totalPrice += (i.getPrice() * i.getQuantity());
        }
        return totalPrice;
    }

    public void displayOrder() {
        String header = """
        Order #%d
        Order Type: %s
        Date: %d
        Price Total: %.2f
            Items       Quantity    Price 
                """;
        System.out.printf(header, this.orderId, this.orderType, this.orderTime, this.calculateTotalPrice());
        for(FoodItem i : this.foodItems) {
            System.out.printf("    %-10s    %-10d$%-10.2f\n", i.getName(),i.getQuantity(),i.getPrice());
        }
        System.out.println();
    }

    public long getOrderTime() {
        return this.orderTime;
    }

    public String getOrderType() {
        return this.orderType;
    }

}

