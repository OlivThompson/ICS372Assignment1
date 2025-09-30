package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * OrderManagerInterface provides an entryway for staff to add and manage orders.
 */
class OrderManagerInterface {
    /**
     * orderManager - manages states of the orders as they come in and are altered.
     * orderParser - parses orders from JSON files.
     * s - Scanner used to get input from user.
     */
    private final OrderParser orderParser = new OrderParser();
    private final OrderManager orderManager = new OrderManager();
    private final Scanner s = new Scanner(System.in);

    /**
     * Prints a menu of options for the user.
     */
    void printUserOptions()  {
        String userMenu = """
                User Menu
                1. Add Order
                2. Cancel Order
                3. Start Incoming Order
                4. View An Order
                5. Complete An Order
                6. View All Incomplete Orders
                7. Export All Orders
                8. Exit
                """;
        System.out.println(userMenu);
    }

    /**
     * Loops a menu, continuously prompting user for input.
     */
    void loopMenu() {
        while(true) {
            printUserOptions();
            parseUserInput(getUserChoice());
        }
    }

    /**
     * Takes input from a user and executes the appropriate operation.
     *
     * @param userInput - input from a user.
     */
    private void parseUserInput(int userInput) {
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
                displayOrderDetails();
                break;
            case 5:
                completeOrder();
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
            case -1:
                break;
            default:
                System.out.println("Enter the number of a valid choice.\n");
                break;

        }
    }

    /**
     * Displays all incomplete orders.
     */
    private void displayAllIncompleteOrders() {
        for(Order o : orderManager.getOrders()) {
            if(!o.getStatus().equals("Completed")) {
                o.displayOrder();
            }
        }
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
    private void completeOrder() {
        displayOrderOptions();
        System.out.println("Enter order ID to complete: ");
        int orderID = getUserChoice();

        orderManager.completeIncomingOrder(orderID);

        orderParser.writeOrderToJSON(orderManager.findOrder(orderID));
    }

    /**
     * Displays orders, then prompts user to select
     * one to see the details.
     */
    private void displayOrderDetails() {
        displayOrderOptions();

        System.out.println("Enter order ID for detailed display: ");
        int orderID = getUserChoice();

        orderManager.displayOrder(orderID);
    }

    /**
     * Displays the order options for user to choose from.
     */
    private void displayOrderOptions() {
        for(Order o : orderManager.getOrders()) {
            System.out.printf("     OrderID: %d | Price: $%.2f Status:%s\n", o.getOrderID(), o.calculateTotalPrice(), o.getStatus());
        }
    }

    /**
     * Displays list of orders, then
     * prompts user enter order ID of order
     * to be started.
     */
    private void startIncomingOrder() {
        displayOrderOptions();

        System.out.println("Enter order ID to start: ");
        int orderID = getUserChoice();
        orderManager.startIncomingOrder(orderID);
    }

    /**
     * Displays order options, then prompts user for the order ID of order to be cancelled.
     */
    private void cancelOrder() {
        displayOrderOptions();

        System.out.println("Enter orderID to cancel: ");
        int orderID = getUserChoice();

        orderManager.cancelOrder(orderID);
    }

    /**
     * Prompts user for filepath to JSON order file,
     * parses the contents of the order, and adds the
     * order to orderManager's orders.
     *
     */
    private void addOrder() {

        try {
            System.out.println("Enter filepath for new order: ");
            s.nextLine();
            String filepath = s.nextLine();
            Order order = orderParser.readOrderFromJson(filepath);
            orderManager.addOrder(order);

        } catch (ParseException e) {
            System.out.println("File could not be parsed: " + e.getMessage());

        } catch (FileNotFoundException e) {
            System.out.println("File could not be found.");

        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
    }

    /**
     * Gets user input and validates it is a number.
     *
     * @return User's input.
     */
    private int getUserChoice() {
        int choice = -1;

        try {
            choice = s.nextInt();

        } catch(InputMismatchException e) {
            System.out.println("Please enter a valid number.\n");
            s.nextLine();
        }
        return choice;
    }

    public static void main(String[] args) {
        OrderManagerInterface orderManagerInterface = new OrderManagerInterface();
        orderManagerInterface.loopMenu();
    }
}

