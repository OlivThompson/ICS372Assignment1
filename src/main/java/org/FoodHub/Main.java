package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String orderFilePath = "orders/order.json";

        // Get the order data through readOrderFile function
        try {
            JSONObject orderData = readOrderFile(orderFilePath);
            if (orderData != null){
                String orderType = (String) orderData.get("type");
                Long orderDate = (Long) orderData.get("order_date");
                // Print out the orderType and orderDate
                System.out.println("Type: " + orderType);
                System.out.println("Order_Date: " + orderDate);
                // Item is an object array
                System.out.printf("\tItems%15s %10s", "Quantity:", "Price\n");
                JSONArray itemsArray = (JSONArray) orderData.get("items");
                for (Object itemObj : itemsArray){
                    JSONObject itemData = (JSONObject)itemObj;

                    String name = (String)itemData.get("name");
                    Long quantity = (Long)itemData.get("quantity");
                    double price = (double)itemData.get("price");

                    //  Print out the items ordered
                    System.out.printf("\t%-" + 15 + "s %-8d %.2f\n", name, quantity, price);
                }
            }
        }
        catch (ParseException | IOException e){
            System.out.println(e.getMessage());
        }




    }

    public static JSONObject readOrderFile(String pathFile) throws IOException, ParseException {
        // Create the JSON Parse object
        JSONParser parser = new JSONParser();

        // Create the file path convert it into an object and then read it into the parser
        Object obj = parser.parse(new FileReader(pathFile));
        JSONObject objectInfo = (JSONObject) obj;

        return (JSONObject) objectInfo.get("order");
    }
}