package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
public class OrderManagerInterface {
    private OrderParser orderParser = new OrderParser();
    private OrderManager orderManager = new OrderManager();
    private Scanner s = new Scanner(System.in);

    public OrderManagerInterface() throws IOException, ParseException {
    }

    public void printUserOptions()  {
        String userMenu = """
                User Menu
                1. Add Order
                2. Cancel Order
                3. Start Incoming Order
                4. View Incoming Order
                5. Complete Incoming Order
                6. View All Incomplete Orders
                7. Export All Orders
                8. Exit
                """;
        System.out.println(userMenu);
    }

    public void loopMenu() throws IOException, ParseException {
        while(true) {
            printUserOptions();
            parseUserInput(getUserChoice());
        }
    }

    private void parseUserInput(int userInput) throws IOException, ParseException {
        switch (userInput) {
            case 1:
                addOrder();
                break;
            case 2:
                cancelOrder();
                break;
            case 3:
                startIncomingOrder();
                break;
            case 4:
                displayIncomingOrder();
                break;
            case 5:
                completeIncomingOrder();
                break;
            case 6:
                displayAllIncompleteOrders();
                break;
            case 7:
                exportAllOrders();
                break;
            case 8:
                System.exit(0);
                break;

        }
    }

    private void displayAllIncompleteOrders() {
        orderManager.displayAllIncompleteOrders();
    }

    private void exportAllOrders() {
        orderParser.writeAllOrderToFile(orderManager.getOrders());
    }

    private void completeIncomingOrder() {
        System.out.println("Enter order ID to complete: ");
        int orderID = s.nextInt();

        orderManager.completeIncomingOrder(orderID);
    }

    private void displayIncomingOrder() {
        //orderManager.viewIncomingOrders();
        for(Order o : orderManager.getOrders()) {
            if(o.getStatus().equals("Incoming")) {
                System.out.printf("     OrderID: %d | Status:%s\n", o.getOrderId(), o.getStatus());
            }
        }

        System.out.println("Enter order ID to display: ");
        int orderID = s.nextInt();

        orderManager.displayOrder(orderID);
    }

    private void startIncomingOrder() {
        for(Order o : orderManager.getOrders()) {
            if(o.getStatus().equals("Incoming")) {
                System.out.printf("     OrderID: %d | Status:%s\n", o.getOrderId(), o.getStatus());
            }
        }
        System.out.println("Enter order ID to start: ");
        int orderID = s.nextInt();

        orderManager.startIncomingOrder(orderID);

    }

    private void cancelOrder() {
        System.out.println("Enter orderID to cancel: ");
        int orderID = s.nextInt();

        orderManager.cancelOrder(orderID);
    }

    private void addOrder() throws IOException, ParseException {
        System.out.println("Enter filepath for new order: ");
        s.nextLine();
        String filepath = s.nextLine();
        Order order = orderParser.readOrderFromJson(filepath);
        orderManager.addOrder(order);
    }

    private int getUserChoice() {
        return s.nextInt();
    }


}

