package org.FoodHub;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing the attributes and contents of an Order.
 */
public class Order {
    /**
     * The attributes of an Order.
     */
    private static int orderIDCounter = 0;
    private int orderID;
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();
    private String orderStatus;
    private Long orderTime;
    private String orderType;

    /**
     * Creates a new Order with the given attributes.
     *
     * @param foodItems - the FoodItems contained in the order.
     * @param orderStatus - the status of an Order.
     * @param orderTime - the time of an Order.
     * @param orderType - the type of Order.
     */
    public Order(List<FoodItem> foodItems, String orderStatus,
                 Long orderTime, String orderType) {
        this.orderID = Order.orderIDCounter++;
        this.foodItems = foodItems;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderType = orderType;
    }

    /**
     * @return the orderID of an Order.
     */
    public int getOrderID(){
        return orderID;
    }

    /**
     * @return the status of an Order.
     */
    public String getStatus(){
        return this.orderStatus;
    }

    /**
     * @return the list of FoodItems in an Order.
     */
    public List<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    /**
     * Sets an Order's status.
     *
     * @param status - the Order's new status.
     */
    public void setStatus(String status) {
        this.orderStatus = status;
    }

    /**
     * Calculates an Order's total price.
     *
     * @return the total price of an order.
     */
    public double calculateTotalPrice() {
        double totalPrice = 0;
        for( FoodItem i : this.foodItems) {
            totalPrice += (i.getPrice() * i.getQuantity());
        }
        return totalPrice;
    }

    /**
     * Displays the details of an order.
     */
    public void displayOrder() {
        String header = """
        Order #%d
        Order Type: %s
        Date: %d
        Price Total: %.2f
            Items       Quantity    Price 
                """;
        System.out.printf(header, this.orderID, this.orderType, this.orderTime, this.calculateTotalPrice());
        for(FoodItem i : this.foodItems) {
            System.out.printf("    %-10s    %-10d$%-10.2f\n", i.getName(),i.getQuantity(),i.getPrice());
        }
        System.out.println();
    }

    /**
     * @return the time of an Order.
     */
    public long getOrderTime() {
        return this.orderTime;
    }

    /**
     * @return the type of Order.
     */
    public String getOrderType() {
        return this.orderType;
    }

}

