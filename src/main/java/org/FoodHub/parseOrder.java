package org.FoodHub;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class parseOrder {
    private String filePath = "";
    private JSONObject objectInfo;
    ArrayList<String> items = new ArrayList<>();

    /// Parse the file that is being directed
    public parseOrder(String orderFile) throws IOException, ParseException{
        filePath = orderFile;
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(filePath));
        /// Assign this to the variable
        this.objectInfo = (JSONObject)obj;
    };

    /// Return the order information to whoever requests it
    public Object getObjInfo(){
        return objectInfo;
    };

    /// Return the array of the items from the json
    public ArrayList<String> getItems(){
        /// Create another JSONObject in order to get access into the (order's) data
        JSONObject objectItemDataAccess = (JSONObject)objectInfo.get("order");
        JSONArray itemsArray = (JSONArray)objectItemDataAccess.get("items");
        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String itemName = (String)itemData.get("name");
            items.add(itemName);
        }
        return items;
    };

    public void readOrdersFromJson(){
        JSONObject orderData = (JSONObject)objectInfo.get("order");
        String orderType = (String)orderData.get("type");
        Long orderDate = (Long)orderData.get("order_date");
        JSONArray itemsArray = (JSONArray)orderData.get("items");
        System.out.printf("Order Type: %s\n", orderType);
        System.out.printf("Date: %d\n", orderDate);
        System.out.printf("\tItems%15s %10s", "Quantity:", "Price\n");
        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String name = (String)itemData.get("name");
            Long quantity = (Long)itemData.get("quantity");
            double price = (double)itemData.get("price");
            System.out.printf("\t%-" + 15 + "s %-8d %.2f\n", name, quantity, price);
        }
    }
}
