package org.FoodHub;

import java.util.List;
/**
 * Class for storing the attributes and contents of an Order.
 *
 * Foundational Layer
 *
 */
class Order{
    /**
     * The attributes of an Order.
     * orderIDCounter - static variable used to give orders their IDs
     * orderID - the order's ID.
     * foodItems - the list of FoodItems contained within an order.
     * orderTime - the time at which an order was received.
     * orderType - the type of order: pick-up or to-go.
     * serialVersionUID - Serialized version for compatibility
     */
    private static int orderIDCounter = 0;
    private int orderID;
    private List<FoodItem> foodItems;
    private String orderStatus;
    private String orderTime;
    private String orderType;

    /**
     * Creates a new Order with the given attributes.
     *
     * @param foodItems - the FoodItems contained in the order.
     * @param orderStatus - the status of an Order.
     * @param orderTime - the time of an Order.
     * @param orderType - the type of Order.
     */
    Order(List<FoodItem> foodItems, String orderStatus,
          String orderTime, String orderType) {
        this.orderID = Order.orderIDCounter++;
        this.foodItems = foodItems;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderType = orderType;
    }

    /**
     * @return the orderID of an Order.
     */
    int getOrderID(){
        return orderID;
    }

    /**
     * @return the status of an Order.
     */
    String getStatus(){
        return this.orderStatus;
    }

    /**
     * @return the list of FoodItems in an Order.
     */
    List<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    /**
     * Sets an Order's status.
     *
     * @param status - the Order's new status.
     */
    void setStatus(String status) {
        this.orderStatus = status;
    }

    /**
     * Calculates an Order's total price.
     *
     * @return the total price of an order.
     */
    double calculateTotalPrice() {
        double totalPrice = 0;
        for( FoodItem i : this.foodItems) {
            totalPrice += (i.getPrice() * i.getQuantity());
        }
        return totalPrice;
    }

    /**
     * Displays the details of an order.
     */
    void displayOrder() {
        String header = """
        Order ID: %d
        Order Type: %s
        Date: %s
        Price Total: $%.2f
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
    String getOrderTime() {
        return this.orderTime;
    }

    /**
     * @return the type of Order.
     */
    String getOrderType() {
        return this.orderType;
    }

}

