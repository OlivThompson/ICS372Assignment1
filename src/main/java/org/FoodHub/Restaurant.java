package org.FoodHub;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Restaurant{
    private String name;
    // A List of Orders
    private List<Order> incompleteOrders;
    // A List of Completed Orders
    private List<Order> completedOrders;
    // Calls to the instance of the OrderParser
    private OrderParser orderParser = OrderParser.getInstance();
    private Scanner s = new Scanner(System.in);

    public Restaurant(String name){
        this.name = name;
        this.incompleteOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
    }

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

}
