package org.FoodHub;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class parseOrder {
    private JSONObject objectInfo;
    ArrayList<foodItem> items = new ArrayList<foodItem>();

    public parseOrder(){};

    public void parseFile(String orderFile) throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        System.out.println(userDirectory);

        Path theOrderDir = Paths.get(userDirectory, "orders");
        Path theOrderFile = theOrderDir.resolve(orderFile);

        Object obj = parser.parse(new FileReader(theOrderFile.toFile()));
        System.out.println("Error Pass Here");
        this.objectInfo = (JSONObject)obj;
    }

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

    public String getOrderType(){
        JSONObject orderData = (JSONObject)objectInfo.get("order");
        String orderType = (String)orderData.get("type");
        return orderType;
    }

    public long getOrderTime(){
        JSONObject orderData = (JSONObject)objectInfo.get("order");
        long orderDate = (long)orderData.get("order_date");
        return orderDate;
    };

    /// Create a new order
    /// But this new will also create a new orderfile
    /// orderfile will be created and named incrementally
    /// the Path to the file will automatically be set to "orders/ + "orderId""
    public void writeOrderToJSON(Order theOrder){
        File orderPath = new File("orders/completedOrder");
        if (!orderPath .exists()){
            orderPath.mkdirs();
            System.out.println("Create Directory");
        }

        File outputFile = new File(orderPath, "Order#" + theOrder.getOrderId() + ".json");

        try(FileWriter file = new FileWriter(outputFile)){
            file.write(fortmatForWriting(theOrder).toJSONString());
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public JSONObject fortmatForWriting(Order incomingOrder){

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
        return theOrder;
    }

    public void writeAllOrderToFile(List<Order> allOrders){
        JSONArray allOrdersArray = new JSONArray();

        for (Order order : allOrders){
            JSONObject orderJSON = fortmatForWriting(order);
            allOrdersArray.add(orderJSON);
        }

        File all_Orders_Path = new File("orders/all_orders");
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
