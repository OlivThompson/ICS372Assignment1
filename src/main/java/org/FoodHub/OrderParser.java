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

/**
 * Class to parse a JSON Order file
 */
public class OrderParser {

    /**
     * Reads and parses a JSON Order file.
     *
     * @param orderFile - the filepath of the JSON Order file to be parsed.
     * @return an Order based upon the JSON file.
     * @throws IOException
     * @throws ParseException
     */
    public Order readOrderFromJson(String orderFile) throws IOException, ParseException {
        String filePath = orderFile;
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(filePath));
        /// Assign this to the variable
        JSONObject objectInfo = (JSONObject) obj;
        
        JSONObject orderData = (JSONObject) objectInfo.get("order");
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

    /**
     * Writes an Order to JSON.
     *
     * @param theOrder - the order to be serialized to JSON.
     */
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

    /**
     * Provides the format for serializing an Order to JSON and writing it to a file.
     *
     * @param incomingOrder - the Order to be serialized.
     * @return the JSONObject containing the attributes of an Order.
     */
    public JSONObject fortmatForWriting(Order incomingOrder){
        JSONArray itemArray = new JSONArray();
        for (FoodItem itemData : incomingOrder.getFoodItems()){
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

    /**
     * Serializes a list of Orders to JSON and writes them to a file.
     *
     * @param allOrders - the orders to be serialized.
     */
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


