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
        // Start out with a JSON Parser Object
        JSONParser parser = new JSONParser();

        try{
            String file = "orders/order.json";
            Object obj = parser.parse(new FileReader(file));

            JSONObject theOrder = (JSONObject) obj;
            Object order = theOrder.get("order");
            JSONObject orderData = (JSONObject) order;
            String orderType = (String) orderData.get("type");
            Long orderDate = (Long) orderData.get("order_date");

            JSONArray itemsArray = (JSONArray) orderData.get("items");

            for (Object itemObj : itemsArray){
                JSONObject itemsData = (JSONObject) itemObj;

                String name = (String) itemsData.get("name");
                Long quantity = (Long) itemsData.get("quantity");
                double price = (double) itemsData.get("price");

                System.out.println(name + ":" + quantity + ":" + price);
            }

        }
        catch (IOException e){
            System.err.println("Reading Error: " + e.getMessage());
        }
        catch (ParseException e){
            System.err.println("Error Parsing: " + e.getMessage());
        }




    }
}