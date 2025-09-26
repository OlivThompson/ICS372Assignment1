package org.FoodHub;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderParser {
    //private String filePath = "";
    private JSONObject objectInfo;

    public Order readOrderFromJson(String orderFile) throws IOException, ParseException {

        String filePath = orderFile;
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(filePath));
        /// Assign this to the variable
        this.objectInfo = (JSONObject)obj;
        
        JSONObject orderData = (JSONObject)objectInfo.get("order");
        String orderType = (String)orderData.get("type");
        Long orderDate = (Long)orderData.get("order_date");
        JSONArray itemsArray = (JSONArray)orderData.get("items");
        List<FoodItem> orderedItems = new ArrayList<FoodItem>();

        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String name = (String)itemData.get("name");
            Long quantity = (Long)itemData.get("quantity");
            double price = (double)itemData.get("price");
            orderedItems.add(new FoodItem(name, quantity, price));
        }
        return new Order(orderedItems, "Incoming", orderDate, orderType);
    }
}


