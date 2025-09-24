package org.FoodHub;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<foodItem> foodItems = new ArrayList<foodItem>();
    private int orderStatus;
    private long orderTime;
    private String orderType;

//    public Order(int orderId, ArrayList <foodItem> foodItems, String orderStatus,
//                 LocalDateTime orderTime) {
//        this.orderId = orderId;
//        this.foodItems = foodItems;
//        this.orderStatus = orderStatus;
//        this.orderTime = orderTime;
//    }

    public Order(int orderId, String orderType, long orderTime, int orderStatus, ArrayList<foodItem> items){
        this.orderId = orderId;
        this.orderType = orderType;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }

    /// WHAT DO I DO WITH THE QUANTITY, HOW SHOULD I IMPLEMENT IT?
    /// 1. Parameter 1 adds the food into the foodItems array
    /// 2. Quantity.....How should I calculate it...need it to access price and * it
    /// Where should I store quantity?
    /// Maybe...I add quantity variable to foodItem and then use setQuantity to the item with parameter 2
//    public void orderAddItem(foodItem item, int quantity){
//        foodItems.add(item);
//        int itemIndex = foodItems.indexOf(item);
//        foodItems.get(itemIndex).setFoodQuantity(quantity);
//    }

    public void getOrderItemFromJSON(ArrayList<foodItem> foodItem){
        this.foodItems.addAll(foodItem);
    }

    public int getOrderId(){
        return orderId;
    }

//    public double getTotalPrice(){
//        double totalPrice = 0;
//        for (Object foodItem : foodItems) {
//            totalPrice += foodItem.getPrice();
//        }
//        return totalPrice;
//    }

    public void setStatus(int status){
        orderStatus = status;
    }

    public int getStatus(){
        return orderStatus;
    }

    public void printOrderItems(){
        System.out.println(foodItems);
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

}
