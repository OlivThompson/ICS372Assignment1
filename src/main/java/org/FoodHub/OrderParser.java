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
    private String filePath = "";
    private JSONObject objectInfo;
    private int orderID = 0;
    /// Parse the file that is being directed
//    public OrderParser(String orderFile) throws IOException, ParseException{
//        filePath = orderFile;
//        JSONParser parser = new JSONParser();
//
//        Object obj = parser.parse(new FileReader(filePath));
//        /// Assign this to the variable
//        this.objectInfo = (JSONObject)obj;
//    };

    /// Return the order information to whoever requests it
    public Object getObjInfo(){
        return objectInfo;
    };

    /// Return the array of the items from the json
//    public ArrayList<FoodItem> getItems(){
//        /// Create another JSONObject in order to get access into the (order's) data
//        JSONObject objectItemDataAccess = (JSONObject)objectInfo.get("order");
//        JSONArray itemsArray = (JSONArray)objectItemDataAccess.get("items");
//
//        for (Object itemObject : itemsArray){
//            JSONObject itemData = (JSONObject)itemObject;
//            String itemName = (String)itemData.get("name");
//            double price = (double)itemData.get("price");
//            int quantity = (int)itemData.get("quantity");
//
//            FoodItem newItem = new FoodItem(itemName, quantity, price);
//            newItem.setFoodQuantity((int)quantity);
//            newItem.foodSetPrice(price);
//            items.add(newItem);
//        }
//        return items;
//    };

    public Order readOrderFromJson(String orderFile) throws IOException, ParseException {

        filePath = orderFile;
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
            int quantity = (int)itemData.get("quantity");
            double price = (double)itemData.get("price");
            orderedItems.add(new FoodItem(name, quantity, price));
        }
        return new Order(this.orderID, orderedItems, "Incoming", orderDate, orderType);
    }
}
