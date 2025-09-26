package org.FoodHub;

import java.util.Scanner;

public class UserInterface {
    OrderManager manage = new OrderManager();
    Scanner scnr = new Scanner(System.in);
    boolean tryAgain = true;

    public UserInterface() {
        while (true) {

            System.out.println("1. Select Order#");
            System.out.println("2. Get All Order Status");
            System.out.println("3. Show all uncompleted orders");
            System.out.println("4. Show all completed orders");
            System.out.println("5. Export all orders");
            System.out.println("10. Exit");
            int selector = scnr.nextInt();
            scnr.nextLine();
            if (selector == 10) {
                break;
            }

            switch (selector) {
                case 1:
                    printOrderAllStatus();
                    System.out.print("Select Order: ");
                    int orderID = scnr.nextInt();
                    scnr.nextLine();
                    while(tryAgain) {
                        System.out.println("\n\nSelected Order: " + orderID);
                        System.out.println("1. View Order");
                        System.out.println("2. Start Order");
                        System.out.println("3. Complete Order");
                        System.out.println("4. Cancel Order");
                        selector = scnr.nextInt();
                        scnr.nextLine();
                        switch (selector) {

                            ///  If status = 0 (Incoming) it can be only Started(1) or cancel(3)
                            ///  If stats = 1 (Started) it can only be cancel(3) or completed(2)
                            ///  If status = 2 (completed), do nothing (already exported to completedOrder file)
                            ///  If status = 3 (Cancel) do nothing

                            case 1:
                                System.out.println("\n\n\n");
                                manage.printOrder(orderID);
                                System.out.println("\n\n");
                                break;
                            case 2:
                                if (manage.getOrderStatus(orderID) == 0) {
                                    System.out.println("\n");
                                    manage.startIncomingOrder(orderID);
                                    waitForEnter(scnr);
                                    break;
                                } else if (manage.getOrderStatus(orderID) == 1) {
                                    System.out.println("\n\nOrder has already been started\n");
                                    waitForEnter(scnr);
                                } else if (manage.getOrderStatus(orderID) == 3) {
                                    System.out.println("\n\nOrder has already been cancelled, you cannot start!\n");
                                    waitForEnter(scnr);
                                }
                                else if (manage.getOrderStatus(orderID) == 2){
                                    System.out.println("\n\nOrder has already been completed!");
                                    waitForEnter(scnr);
                                    break;
                                }
                                else{
                                    System.out.println("\n\nWrong input try again!");
                                    waitForEnter(scnr);
                                    break;
                                }
                                break;
                            case 3:
                                if (manage.getOrderStatus(orderID) == 0){
                                    System.out.println("\n\nThe has not been started yet");
                                    waitForEnter(scnr);
                                    break;
                                }
                                else if (manage.getOrderStatus(orderID) == 3){
                                    System.out.println("\n\nOrder has already been cancelled!");
                                    waitForEnter(scnr);
                                    break;
                                }
                                else if (manage.getOrderStatus(orderID) == 2){
                                    System.out.println("\n\nOrder has already been completed!");
                                    waitForEnter(scnr);
                                    break;
                                }
                                else if (manage.getOrderStatus(orderID) == 1) {
                                    manage.completeOrder(orderID);
                                    System.out.println("\n\nOrder has been completed");
                                    waitForEnter(scnr);
                                    break;
                                }
                                break;
                            case 4:
                                if (manage.getOrderStatus(orderID) == 2){
                                    System.out.println("\n\nYou cannot cancel a completed order!");
                                    waitForEnter(scnr);
                                    break;
                                }
                                else if (manage.getOrderStatus(orderID) == 3){
                                    System.out.println("\n\nOrder is already cancelled");
                                    waitForEnter(scnr);
                                    break;
                                }
                                manage.cancelOrder(orderID);
                                System.out.println("\n\nOrder has been cancelled");
                                waitForEnter(scnr);
                                break;
                        }
                        break;
                    }
                    break;
                case 2:
                    printOrderAllStatus();
                    waitForEnter(scnr);
                    break;
                case 3:
                    getIncompleteOrder();
                    waitForEnter(scnr);
                    break;
                case 4:
                    getCompletedOrder();
                    waitForEnter(scnr);
                    break;
                case 5:
                    exportAllOrders();
                    waitForEnter(scnr);
                    break;
            }
        }
    }

    public void printOrderAllStatus() {
        String orderStatus = "";
        for (Order order : manage.getAllOrders()) {
            if (order.getStatus() == 0) {
                orderStatus = "Incoming";
            } else if (order.getStatus() == 1) {
                orderStatus = "Started";
            } else if (order.getStatus() == 2) {
                orderStatus = "Completed";
            }
            else if (order.getStatus() == 3){
                orderStatus = "Cancelled";
            }
            System.out.println("Order#" + order.getOrderId() + "\t\t|\t Order Status: " + orderStatus);
        }
    }

    public void waitForEnter(Scanner scnr) {
        System.out.println("\n\nPress Enter to continue...");
        scnr.nextLine();
    }

    public void getIncompleteOrder(){
        String orderStatus = "Incomplete";
        double totalPrice = 0;
        for (Order order : manage.getIncompleteOrders()){
            System.out.println("Order#" + order.getOrderId() + "\t\t|\t Order Status: " + orderStatus);
            totalPrice += order.getTotalPrice();
        }
        System.out.printf("\nTotal Price: %.02f\n\n", totalPrice);
    }

    public void getCompletedOrder(){
        double totalPrice = 0;
        for (Order theOrder : manage.getCompletedOrders()){
            totalPrice += theOrder.getTotalPrice();
            System.out.println("Order# " + theOrder.getOrderId() + "\t\t|\t Order Status: " + "Completed");
        }
        System.out.printf("\nTotal Price: %.02f\n\n", totalPrice);
    }

    public void exportAllOrders(){
        manage.exportAllOrders();
        System.out.println("All orders has been exported");
    }

}