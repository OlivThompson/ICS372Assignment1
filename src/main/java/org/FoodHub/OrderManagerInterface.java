package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/**
 * OrderManagerInterface provides an entryway for staff to add and manage orders.
 */
public class OrderManagerInterface {
    /**
     * orderManager - manages states of the orders as they come in and are altered.
     * orderParser - parses orders from JSON files.
     * s - Scanner used to get input from user.
     */
    private OrderParser orderParser = new OrderParser();
    private OrderManager orderManager = new OrderManager();
    private Scanner s = new Scanner(System.in);

//    public OrderManagerInterface() throws IOException, ParseException {
//    }

    /**
     * Prints a menu of options for the user.
     */
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

    /**
     * Loops a menu, continuously prompting user for input.
     *
     * @throws IOException
     * @throws ParseException
     */
    public void loopMenu() throws IOException, ParseException {
        while(true) {
            printUserOptions();
            parseUserInput(getUserChoice());
        }
    }

    /**
     * Takes input from a user and executes the appropriate operation.
     *
     * @param userInput input from a user.
     * @throws IOException
     * @throws ParseException
     */
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

    /**
     * Displays all incomplete orders.
     */
    private void displayAllIncompleteOrders() {
        orderManager.displayAllIncompleteOrders();
    }

    /**
     * Exports every order to a JSON file.
     */
    private void exportAllOrders() {
        orderParser.writeAllOrderToFile(orderManager.getOrders());
    }

    /**
     * Prompts user for an order ID of order to be completed,
     * then records it in a JSON file.
     */
    private void completeIncomingOrder() {
        System.out.println("Enter order ID to complete: ");
        int orderID = s.nextInt();

        orderManager.completeIncomingOrder(orderID);

        for(Order o: orderManager.getOrders()) {
            if(o.getOrderId() == orderID) {
                orderParser.writeOrderToJSON(o);
            }
        }
    }

    /**
     * Displays incoming orders, then prompts user to select
     * one to see the details.
     */
    private void displayIncomingOrder() {
        for(Order o : orderManager.getOrders()) {
            if(o.getStatus().equals("Incoming")) {
                System.out.printf("     OrderID: %d | Status:%s\n", o.getOrderId(), o.getStatus());
            }
        }

        System.out.println("Enter order ID to display: ");
        int orderID = s.nextInt();

        orderManager.displayOrder(orderID);
    }

    /**
     * Displays list of incoming orders, then
     * prompts user enter order ID of order
     * to be started.
     */
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

    /**
     * Prompts user for the order ID of order to be cancelled.
     */
    private void cancelOrder() {
        System.out.println("Enter orderID to cancel: ");
        int orderID = s.nextInt();

        orderManager.cancelOrder(orderID);
    }

    /**
     * Prompts user for filepath to JSON order file,
     * parses the contents of the order, and adds the
     * order to orderManager's orders.
     *
     * @throws IOException
     * @throws ParseException
     */
    private void addOrder() throws IOException, ParseException {
        System.out.println("Enter filepath for new order: ");
        s.nextLine();
        String filepath = s.nextLine();
        Order order = orderParser.readOrderFromJson(filepath);
        orderManager.addOrder(order);
    }

    /**
     *
     * @return User's input.
     */
    private int getUserChoice() {
        return s.nextInt();
    }


}

