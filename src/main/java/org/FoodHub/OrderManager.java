package org.FoodHub;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> incomingOrders = new ArrayList<Order>();

    private List<Order> completedOrders = new ArrayList<Order>();

    public void addOrder(Order order) {
       this.incomingOrders.add(order);
    }

    public void cancelOrder(int orderID) {
        int index = -1;
        for(int i = 0; i < incomingOrders.size(); i++) {
            if (incomingOrders.get(i).getOrderId() == orderID) {
                index = i;
            }
        }

        incomingOrders.remove(index);
    }

    public void startIncomingOrder(int orderID) {
        int index = -1;
        for(int i = 0; i < incomingOrders.size(); i++) {
            if (incomingOrders.get(i).getOrderId() == orderID) {
                index = i;
            }
        }

        incomingOrders.get(index).setStatus(0);
    }

    public void completeIncomingOrder(int orderID) {
        int index = -1;
        for(int i = 0; i < incomingOrders.size(); i++) {
            if (incomingOrders.get(i).getOrderId() == orderID) {
                index = i;
            }
        }

        Order completedOrder = incomingOrders.get(index);
        completedOrder.setStatus(2);
        completedOrders.add(completedOrder);
        incomingOrders.remove(completedOrder);
    }

    public void exportAllOrders() {
        //CONSIDER MOVING TO ORDERMANAGERINTERFACE
    }

    public List<Order> getIncompleteOrders() {
        return this.incomingOrders;
    }
}
