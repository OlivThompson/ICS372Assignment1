package org.FoodHub;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class parseOrder {
    private String filePath = "";
    private JSONObject objectInfo;
    ArrayList<foodItem> items = new ArrayList<foodItem>();

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
    public ArrayList<foodItem> getItems(){
        /// Create another JSONObject in order to get access into the (order's) data
        JSONObject objectItemDataAccess = (JSONObject)objectInfo.get("order");
        JSONArray itemsArray = (JSONArray)objectItemDataAccess.get("items");
        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String itemName = (String)itemData.get("name");
            double price = (double)itemData.get("price");
            long quantity = (long)itemData.get("quantity");

            foodItem newItem = new foodItem(itemName);
            newItem.setFoodQuantity((int)quantity);
            newItem.foodSetPrice(price);
            items.add(newItem);
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

    /// Create a new order
    /// But this new will also create a new orderfile
    /// orderfile will be created and named incrementally
    /// the Path to the file will automatically be set to "orders/ + "orderId""
    public JSONObject writeToFile(Order incomingOrder){

        JSONArray itemArray = new JSONArray();
        for (foodItem itemData : incomingOrder.getFoodList()){
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", itemData.getName());
            itemObj.put("quantity", itemData.getQuantity());
            itemObj.put("price", itemData.getPrice());
            itemArray.add(itemObj);
        }

        JSONObject orderObj = new JSONObject();
        orderObj.put("type", incomingOrder.getOrderType());
        orderObj.put("order_date", incomingOrder.getOrderTime());
        orderObj.put("items", itemArray);

        JSONObject theOrder = new JSONObject();
        theOrder.put("order", orderObj);
        System.out.println(theOrder.toJSONString());
        return theOrder;
    }

    public void writeAllOrderToFile(List<Order> allOrders){
        JSONArray allOrdersArray = new JSONArray();

        for (Order order : allOrders){
            JSONObject orderJSON = writeToFile(order);
            allOrdersArray.add(orderJSON);
        }

        File all_Orders_Path = new File("/orders/all_orders");
        if  (!all_Orders_Path.exists()){
            all_Orders_Path.mkdirs();
            System.out.println("Created Directory");
        }
        try(FileWriter file = new FileWriter(all_Orders_Path + File.separator + "all_orders.json")){
            file.write(allOrdersArray.toJSONString());
            System.out.println("Success");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
