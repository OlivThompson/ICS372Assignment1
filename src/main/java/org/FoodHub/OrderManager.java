package org.FoodHub;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new ArrayList<Order>();

    public void addOrder(Order order) {
       this.orders.add(order);
    }

    public void viewIncomingOrders() {
        for(Order o : orders) {
            if(o.getStatus().equals("Incoming")) {
                o.displayOrder();
            }
        }
    }

    public void cancelOrder(int orderID) {
        findOrder(orderID).setStatus("Cancelled");
    }

    public void startIncomingOrder(int orderID) {
        Order order = findOrder(orderID);
        if(!order.getStatus().equals("Incoming")) {
            System.out.println("Only incoming orders can be started.\n");
        } else {
            order.setStatus("Started");
        }
    }

    public void completeIncomingOrder(int orderID) {
        findOrder(orderID).setStatus("Completed");
    }

    public List<Order> getIncompleteOrders() {
        return this.orders;
    }

    private Order findOrder(int orderID) {
        Order order = null;
        for(Order o : orders) {
            if (o.getOrderId() == orderID) {
                order = o;
            }
        }
        return order;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void displayAllIncompleteOrders() {
        for(Order o : this.orders) {
            if(!o.getStatus().equals("Completed")) {
                o.displayOrder();
            }
        }
    }
}

