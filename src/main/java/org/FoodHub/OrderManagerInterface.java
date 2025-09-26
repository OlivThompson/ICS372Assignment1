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
                4. View Incoming Orders
                5. Complete Incoming Order
                6. View Incomplete Orders
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
                viewIncomingOrders();
                break;
            case 5: break;
            case 6: break;
            case 7: break;
            case 8:
                System.exit(0);
                break;

        }
    }

    private void viewIncomingOrders() {

    }

    private void startIncomingOrder() {
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
        orderParser.readOrderFromJson(s.nextLine());
    }

    private int getUserChoice() {
        return s.nextInt();
    }


}
