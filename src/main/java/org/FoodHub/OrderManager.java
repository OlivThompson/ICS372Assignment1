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
    private List<Order> orders = new ArrayList<>();

    /**
     * Adds an order to orders list.
     *
     * @param order - The order to be added to orders.
     */
    public void addOrder(Order order) {
       this.orders.add(order);
    }

    /**
     * Changes an order's status to Cancelled based on its order ID.
     *
     * @param orderID the order ID of the order to be cancelled.
     */
    public void cancelOrder(int orderID) {
        Order order = findOrder(orderID);
        if(order != null) {
            order.setStatus("Cancelled");
        } else {
            System.out.printf("Order with ID %d doesn't exist.\n", orderID);
        }
    }

    /**
     * Changes an incoming order's status to Started.
     *
     * @param orderID - the orderID of the order to be started.
     */
    public void startIncomingOrder(int orderID) {
        Order order = findOrder(orderID);

        if (order != null) {
            if(!order.getStatus().equals("Incoming")) {
                System.out.println("Only incoming orders can be started.\n");
            } else {
                order.setStatus("Started");
            }
        } else {
            System.out.printf("Order with ID %d doesn't exist.\n", orderID);
        }
    }

    /**
     * Sets a given order's status to Completed.
     *
     * @param orderID - the orderID of the order to be completed.
     */
    public void completeIncomingOrder(int orderID) {
        Order order = findOrder(orderID);
        if(findOrder(orderID) != null) {
            order.setStatus("Completed");
        } else {
            System.out.printf("Order with ID %d doesn't exist.\n", orderID);
        }
    }

    /**
     * Finds an order based on its orderID.
     *
     * @param orderID - the orderID of the order to be found.
     * @return the order with the specified orderID.
     */
    Order findOrder(int orderID) {
        Order order = null;
        for(Order o : orders) {
            if (o.getOrderID() == orderID) {
                order = o;
            }
        }
        return order;
    }

    /**
     * @return the list of orders managed by the OrderManager.
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
        Order order = findOrder(orderID);
        if(order != null) {
            order.displayOrder();
        } else {
            System.out.printf("Order with ID %d doesn't exist.\n", orderID);
        }
    }


}

