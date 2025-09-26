package org.FoodHub;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private int orderId;
    private ArrayList<foodItem> foodItems = new ArrayList<foodItem>();
    private int orderStatus;
    private LocalDateTime orderTime;

//    public Order(int orderId, ArrayList <foodItem> foodItems, String orderStatus,
//                 LocalDateTime orderTime) {
//        this.orderId = orderId;
//        this.foodItems = foodItems;
//        this.orderStatus = orderStatus;
//        this.orderTime = orderTime;
//    }

    public Order(int orderId){
        this.orderId = orderId;
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

    public int getStatus(){
        return this.orderStatus;
//        try {
//            return Integer.parseInt(orderStatus);
//        } catch(NumberFormatException e) {
//            return -1;
//        }
    }

    public void printOrderItems(){
        System.out.println(foodItems);
    }

    public void setStatus(int status) {

    }

}
