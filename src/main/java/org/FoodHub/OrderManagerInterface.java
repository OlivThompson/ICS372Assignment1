package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

    class OrderManagerInterface {
        /**
         * orderManager - manages states of the orders as they come in and are altered.
         * orderParser - parses orders from JSON files.
         * s - Scanner used to get input from user.
         */
        private final OrderManager orderManager = new OrderManager();
        private final Scanner s = new Scanner(System.in);
        SaveState saveData = new jsonSaveData(orderManager);
        File filePath = new File("SavedDataForLoad.json");
        OrderProcessor processOrder = new OrderProcessor();

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
            saveData.load(filePath, orderManager);
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
                    saveData.save(orderManager, filePath);
                    break;
                case 2:
                    cancelOrder();
                    saveData.save(orderManager, filePath);
                    break;
                case 3:
                    startIncomingOrder();
                    saveData.save(orderManager, filePath);
                    break;
                case 4:
                    displayOrderDetails();
                    break;
                case 5:
                    completeOrder();
                    saveData.save(orderManager, filePath);
                    break;
                case 6:
                    displayAllIncompleteOrders();
                    break;
                case 7:
                    exportAllOrders();
                    break;
                case 8:
                    saveData.save(orderManager, filePath);
                    System.exit(0);
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
                if(!o.getOrderStatus().equals(OrderStatus.COMPLETED)) {
                    o.displayOrder();
                }
            }
        }

        /**
         * Exports every order to a JSON file.
         */
        private void exportAllOrders() {
            processOrder.writeAllOrdersToFile(orderManager.getOrders());
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

            processOrder.writeToJSON(orderManager.findOrder(orderID));
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
                System.out.printf("     OrderID: %d | Price: $%.2f Status:%s\n", o.getOrderID(), o.calculateTotalPrice(), o.getOrderStatus());
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
                System.out.println("Enter filepath for new order: ");
                s.nextLine();
                String filepath = s.nextLine();
//                File file = new File(filepath);
                List<Order> loadedOrders = processOrder.processSingleOrder(filepath);
                if (loadedOrders.isEmpty()){
                    System.out.println("No orders");
                }
                else{
                    for (Order o : loadedOrders){
                        orderManager.addOrder(o);
                    }
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

    }


