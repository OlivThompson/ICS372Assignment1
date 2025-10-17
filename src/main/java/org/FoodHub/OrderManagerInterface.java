package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

/**
 * OrderManagerInterface provides an entryway for staff to add and manage orders.
 *
 * UI Layer
 *
 */
class OrderManagerInterface {
    /**
     * s - Scanner used to get input from user.
     */
    private final Map<String, Restaurant> restaurants = new HashMap<>();
    private Restaurant activeRestaurant = null;
    private final Scanner s = new Scanner(System.in);
    // Create a restaurant.dat file for serialization
    private static final String restaurant_Data_File = "restaurants.dat";

    // A saving method to send data into restaurants.dat
    public void saveData(){
        try (ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(restaurant_Data_File))){
            // Puts restaurants data into restaurant_Data_File
            objectStream.writeObject(restaurants);
            System.out.println("Test: Saved Successfully");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Method for loading data
    public void loadData(){
        File loadedData = new File(restaurant_Data_File);
        if (!loadedData.exists()){
            System.out.println("No data exists");
            return;
        }

        // Loads restaurant_Data_File into loadedMap and puts data into restraunts
        try(ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(loadedData))) {
            @SuppressWarnings("Unchecked")
            Map<String, Restaurant> loadedMap = (Map<String, Restaurant>) objectStream.readObject();
            restaurants.putAll(loadedMap);
        }catch(IOException e){
            System.err.println("Error Loading" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading classes" + e.getMessage());
        }
        System.out.println("Test Load");
    }

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
                8. Select restaurant
                9. Exit
                """;
        System.out.println(userMenu);
    }

    void printRestaurantOptions(){
        String restaurantSelectionMenu = """
                User Menu
                1. Create Restaurant
                2. Select Restaurant
                """;
        System.out.println(restaurantSelectionMenu);
    }

    /**
     * Loops a menu, continuously prompting user for input.
     */
    void loopMenu() {
        // Loads Data at every loop for the most current
        loadData();

        while(true) {
//            printUserOptions();
            if (!activeRestrauntCheck()) {
                printRestaurantOptions();
            }
            parseUserInput(getUserChoice());
        }
    }

    /**
     * Takes input from a user and executes the appropriate operation.
     *
     * @param userInput - input from a user.
     */
    private void parseUserInput(int userInput) {
        switch(userInput) {
            case 1:
                addRestaurant();
                break;
            case 2:
                selectRestaurant();
                while(userInput != 9){
                    printUserOptions();
                    userInput = getUserChoice();
                    switch (userInput) {
                        case 1:
                            addOrder(); ///// Important OrderID are not being stored and resets each order
                            saveData();
                            break;
                        case 2:
                            cancelOrder();
                            saveData();
                            break;
                        case 3:
                            startIncomingOrder();
                            saveData();
                            break;
                        case 4:
                            displayOrderDetails();
                            saveData();
                            break;
                        case 5:
                            completeOrder();
                            saveData();
                            break;
                        case 6:
                            displayAllIncompleteOrders();
                            break;
                        case 7:
                            exportAllOrders();
                            break;
                        case 8:
                            selectRestaurant();
                            break;
                        case 9:
                            System.exit(0);
                            break;
                        case -1:
                            break;
                        default:
                            System.out.println("Enter the number of a valid choice.\n");
                            break;

                    }
                }
                break;
        }
    }

    /**
     * Displays all incomplete orders.
     */
    private void displayAllIncompleteOrders() {
        List<Order> orders = activeRestaurant.getAllOrder();
        for (Order o : orders){
            if(!o.getStatus().equals("Completed")){
                o.displayOrder();
            }
        }


    }

    /**
     * Exports every order to a JSON file.
     */
    private void exportAllOrders() {
        activeRestaurant.exportAllOrders();
    }

    /**
     * Prompts user for an order ID of order to be completed,
     * then records it in a JSON file.
     */
    private void completeOrder() {
        displayOrderOptions();
        System.out.println("Enter order ID to complete: ");
        int orderID = getUserChoice();

        activeRestaurant.completeOrder(orderID);
    }

    /**
     * Displays orders, then prompts user to select
     * one to see the details.
     */
    private void displayOrderDetails() {
        displayOrderOptions();

        System.out.println("Enter order ID for detailed display: ");
        int orderID = getUserChoice();

        activeRestaurant.displayOrder(orderID);
    }

    /**
     * Displays the order options for user to choose from.
     */
    private void displayOrderOptions() {
        List<Order> orders = activeRestaurant.getAllOrder();
        for (Order o : orders){
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
        activeRestaurant.startOrder(orderID);
    }

    /**
     * Displays order options, then prompts user for the order ID of order to be cancelled.
     */
    private void cancelOrder() {
        displayOrderOptions();

        System.out.println("Enter orderID to cancel: ");
        int orderID = getUserChoice();

        activeRestaurant.cancelOrder(orderID);
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
            activeRestaurant.addOrder(filepath);

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

    private void addRestaurant(){
        s.nextLine();
        System.out.printf("Enter Restaurant Name: ");
        String name = s.nextLine().trim();
        if (restaurants.containsKey(name)){
            System.out.println("Restaurant Already Exists!");
            return;
        }
        Restaurant newRest = new Restaurant(name);
        restaurants.put(name, newRest);
        saveData();
    }

    private void selectRestaurant(){
        System.out.println("Select your restaurant: ");
        displayRestaurants();
        s.nextLine();
        String selectRest = s.nextLine().trim();
        if (restaurants.containsKey(selectRest)){
            activeRestaurant = restaurants.get(selectRest);
            System.out.printf("Current Active Restaurant: %s\n", activeRestaurant.getName());
        }
        else{
            System.out.printf("Restaurant does not exists! Try Again!\n");
        }
    }

    private void displayRestaurants(){
        restaurants.keySet().forEach(System.out::println);
    }

    private Boolean activeRestrauntCheck() {
        if (activeRestaurant == null){
            return false;
        }
        else return true;
    }

    public static void main(String[] args) {

//        OrderParser Parser = OrderParser.getInstance();
//        try {
//            File CurrentFile = new File("CurrentFile.txt");
//            FileWriter write = new FileWriter("CurrentFile.txt");
//            Order order1 = Parser.readOrderFromJson("orders/order.json");
//            String test = Parser.serializeOrder(order1);
//            write.write(test);
//            write.close();
//            System.out.println(test);
//
//
//        }catch(IOException|ParseException e){
//            e.printStackTrace();
//        }
        OrderManagerInterface orderManagerInterface = new OrderManagerInterface();
        orderManagerInterface.loopMenu();
    }
}

