package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Scanner scnr = new Scanner(System.in);

        //String orderPath = scnr.nextLine();
        String orderPath = "orders/order.json";

        try{
            parseOrder parseOrderFile = new parseOrder(orderPath);
//            orderManager2 prompter = new orderManager2();
            Order newOrder = new Order(1);

            /// Here is an example that you can use to run fetching JSON FILE foodItem Attributes and printing it
            newOrder.getOrderItemFromJSON(parseOrderFile.getItems());
            newOrder.printOrderItems();

        }
        catch(ParseException|IOException e){
            System.out.println(e.getMessage());
        }
    }
}