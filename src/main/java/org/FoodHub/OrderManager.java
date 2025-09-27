package org.FoodHub;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing and managing orders
 */
public class OrderManager {
    /**
     * The list of orders that an OrderManager will manage.
     */
    private List<Order> orders = new ArrayList<Order>();

    /**
     * Adds an order to orders.
     *
     * @param order - The order to be added to orders.
     */
    public void addOrder(Order order) {
       this.orders.add(order);
    }

    /**
     * Cancels an order based on its orderID.
     *
     * @param orderID the order ID of the order to be cancelled.
     */
    public void cancelOrder(int orderID) {
        findOrder(orderID).setStatus("Cancelled");
    }

    /**
     * Changes an incoming order's status to Started.
     *
     * @param orderID - the orderID of the order to be started.
     */
    public void startIncomingOrder(int orderID) {
        Order order = findOrder(orderID);

        if(!order.getStatus().equals("Incoming")) {
            System.out.println("Only incoming orders can be started.\n");
        } else {
            order.setStatus("Started");
        }
    }

    /**
     * Sets a given order's status to Completed.
     *
     * @param orderID - the orderID of the order to be completed.
     */
    public void completeIncomingOrder(int orderID) {
        findOrder(orderID).setStatus("Completed");
    }

    /**
     * Finds an order based on its orderID.
     *
     * @param orderID - the orderID of the order to be found.
     * @return the order with the specified orderID.
     */
    private Order findOrder(int orderID) {
        Order order = null;
        for(Order o : orders) {
            if (o.getOrderId() == orderID) {
                order = o;
            }
        }
        return order;
    }

    /**
     * @return the list of orders.
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    /**
     * Displays a given order based on its orderID.
     *
     * @param orderID the orderID of the order to be displayed.
     */
    public void displayOrder(int orderID) {
        findOrder(orderID).displayOrder();
    }

    /**
     * Displays all incomplete orders from the orders list.
     */
    public void displayAllIncompleteOrders() {
        for(Order o : this.orders) {
            if(!o.getStatus().equals("Completed")) {
                o.displayOrder();
            }
        }
    }
}

