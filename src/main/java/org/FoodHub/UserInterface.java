package org.FoodHub;

import java.io.File;
import java.util.Scanner;

public class UserInterface {
    OrderManager manage = new OrderManager();
    Scanner scnr = new Scanner(System.in);
    String file;

    public UserInterface() {
        while (true) {

            System.out.println("1. Select Order#");
            System.out.println("2. Get all order Price");
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
                    System.out.println("1. View Order");
                    System.out.println("2. Start Order");
                    System.out.println("3. Complete Order");
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
            System.out.println("\nOrder#" + order.getOrderId() + "\t\t|\t Order Status: " + orderStatus);
        }
    }

    public void waitForEnter(Scanner scnr) {
        System.out.println("\n\nPress Enter to continue...");
        scnr.nextLine();
    }
}