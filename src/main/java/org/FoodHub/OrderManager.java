package org.FoodHub;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class OrderManager {
    private ArrayList<Order> allOrders = new ArrayList<Order>();
    private static int orderID = 0;
    private String orderType;
    private long orderDate;
    int status = 0;  /// By default
    /// 0 = Incomplete/Start
    /// 1 = Complete
    /// 2 = Cancelled
    parseOrder orderParser = new parseOrder();


    public OrderManager(){
        orderType = orderParser.getOrderType();
        orderDate = orderParser.getOrderTime();
    }

//    public void addOrder(Order order) {
//        allOrders.add(order);
//    }

    public void cancelOrder(Order order) {
        order.setStatus(2);
        allOrders.remove(order);
    }

    public void startIncomingOrder() {
        orderID += 1;
        Order newOrder = new Order(orderID, orderType, orderDate, status);
        allOrders.add(newOrder);
    }

    public ArrayList<Order> getCompletedOrders() {
        ArrayList<Order> completedOrders = new ArrayList<Order>();
        for (Order theOrder : allOrders){
            if (theOrder.getStatus() == 1){
                completedOrders.add(theOrder);
            }
        }
        return completedOrders;
    }

    public ArrayList<Order> getIncompleteOrders() {
        ArrayList<Order> incompleteOrders = new ArrayList<Order>();
        for (Order theOrder : allOrders){
            if (theOrder.getStatus() == 0){
                incompleteOrders.add(theOrder);
            }
        }
        return incompleteOrders;
    }

    public void exportAllOrders() {
        orderParser.writeAllOrderToFile(allOrders);
    }

    public ArrayList<Order> getAllOrders(){
        return allOrders;
    }

    public void printIncomingOrder(){
        orderParser.readOrdersFromJson();
    }
}
