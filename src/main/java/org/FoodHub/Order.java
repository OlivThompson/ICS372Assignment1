package org.FoodHub;

import java.util.List;
/**
 * Class for storing the attributes and contents of an Order.
 *
 * Foundational Layer
 *
 */
public class Order{
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
    private Long orderTime;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private DeliveryStatus deliveryStatus;
    private OrderTypeIcon iconType;

    /**
     * Creates a new Order with the given attributes.
     *
     * @param foodItems - the FoodItems contained in the order.
     * @param orderStatus - the status of an Order.
     * @param orderTime - the time of an Order.
     * @param orderType - the type of Order.
     */
    public Order(List<FoodItem> foodItems, OrderStatus orderStatus,
          Long orderTime, OrderType orderType) {
        this.orderID = Order.orderIDCounter++;
        this.foodItems = foodItems;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.deliveryStatus = DeliveryStatus.PENDING;
    }

    /**
     * @return the orderID of an Order.
     */
    public int getOrderID(){
        return orderID;
    }


    /**
     * @return the list of FoodItems in an Order.
     */
    public List<FoodItem> getFoodItems() {
        return this.foodItems;
    }

    /**
     * @return the status of an Order.
     */
    public OrderStatus getOrderStatus(){
        return this.orderStatus;
    }

    /**
     * Sets an Order's status.
     *
     * @param orderStatus - the Order's new status.
     */

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryStatus getDeliveryStatus(){
        return this.deliveryStatus;
    }


    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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
        DateFormatter readableDate = new DateFormatter(this.orderTime);
        String finalDateOutput = readableDate.getDate();
        String header = """
        Order ID: %d
        Order Type: %s
        Date: %s
        Price Total: $%.2f
            Items       Quantity    Price 
                """;
        System.out.printf(header, this.orderID, this.orderType, finalDateOutput, this.calculateTotalPrice());
        for(FoodItem i : this.foodItems) {
            System.out.printf("    %-10s    %-10d$%-10.2f\n", i.getName(),i.getQuantity(),i.getPrice());
        }
        System.out.println();
    }

    /**
     * @return the time of an Order.
     */
    public Long getOrderTime() {
        return this.orderTime;
    }

    public String getOrderTimeToText() {
        DateFormatter formatDate = new DateFormatter(this.orderTime);
        return formatDate.getDate();
    }

    /**
     * @return the type of Order.
     */
    public OrderType getOrderType() {
        return this.orderType;
    }

    /**
     *
     * @return OrderTypeIcon
     */
    OrderTypeIcon getOrderTypeIcon() {
        return switch(this.orderType) {
            case DELIVERY -> OrderTypeIcon.Delivery_Icon;
            case PICKUP -> OrderTypeIcon.Pick_Up_Icon;
            case To_Go -> OrderTypeIcon.To_Go_Icon;
        };
    }


}

