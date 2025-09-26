package org.FoodHub;

import java.io.File;
import java.util.Scanner;

public class UserInterface {
    OrderManager manage = new OrderManager();
    Scanner scnr = new Scanner(System.in);
    boolean tryAgain = true;

    public UserInterface() {
        while (true) {

            System.out.println("1. Select Order#");
            System.out.println("2. Get All Order Status");
            System.out.println("3. Get all order Price");
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
                    if (checkIfOrderExists(orderID)) {
                        System.out.println("Order has already been completed!, try again");
                        waitForEnter(scnr);
                    }
                    scnr.nextLine();
                    while(tryAgain) {
                        System.out.println("Selected Order: " + orderID);
                        System.out.println("1. View Order");
                        System.out.println("2. Start Order");
                        System.out.println("3. Complete Order");
                        System.out.println("4. Cancel Order");
                        selector = scnr.nextInt();
                        scnr.nextLine();
                        switch (selector) {
                            case 1:
                                manage.printOrder(orderID);
                                break;
                            case 2:
                                if (manage.getOrderStatus(orderID) == 0) {
                                    manage.startIncomingOrder(orderID);
                                    break;
                                } else if (manage.getOrderStatus(orderID) == 1) {
                                    System.out.println("\nOrder has already been started\n");
                                    waitForEnter(scnr);
                                } else if (manage.getOrderStatus(orderID) == 3) {
                                    System.out.println("\nOrder has already been cancelled, you cannot start!\n");
                                    waitForEnter(scnr);
                                }
//                                else if(manage.getOrderStatus(orderID) == 2){
//                                    System.out.println("Order has already been completed");
//                                }
                                break;
                            case 3:
                                if (manage.getOrderStatus(orderID) != 1){
                                    System.out.println("The has not been started yet");
                                    waitForEnter(scnr);
                                    break;
                                }
                                manage.completeOrder(orderID);
                                break;
                        }
                        break;
                    }
                    break;
                case 2:
                    printOrderAllStatus();
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
            System.out.println("Order#" + order.getOrderId() + "\t\t|\t Order Status: " + orderStatus);
        }
    }

    public void waitForEnter(Scanner scnr) {
        System.out.println("\n\nPress Enter to continue...");
        scnr.nextLine();
    }

    public boolean checkIfOrderExists(int orderID){
        for (Order checkOrder : manage.getCompletedOrders()){
            if (manage.getCompletedOrders().contains(checkOrder)){
                return true;
            }
        }
        return false;
    }
}