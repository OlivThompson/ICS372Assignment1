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
import javax.xml.parsers.*;

/**
 * Class to parse a JSON Order file
 */
public final class OrderParser {

    /*
     * Use the Singleton Pattern into OrderParser because we just need to use one instance of it
     * */
    private static volatile OrderParser instance;

    private OrderParser(){

    }

    public static OrderParser getInstance(){
        if (instance == null){
            synchronized (OrderParser.class){
                if (instance == null){
                    instance = new OrderParser();
                }
            }
        }
        return instance;
    }


    /*
    *   Converting Order Data from JSONObject into Order previously readOrderFromJson
    * */

    private Order jsonToOrder(JSONObject orderData){
        String orderType = (String)orderData.get("type");
        Long orderDate = (Long)orderData.get("order_date");
        JSONArray itemsArray = (JSONArray)orderData.get("items");
        List<FoodItem> orderedItems = new ArrayList<>();

        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String name = (String)itemData.get("name");
            Long quantityLong = (Long)itemData.get("quantity");
            Double price = (Double)itemData.get("price");

            int quantity = quantityLong != null ? quantityLong.intValue() : 0;
            double finalPrice = price != null ? price.doubleValue() : 0.0;
            orderedItems.add(new FoodItem(name, quantity, finalPrice));
        }

        DateFormatter formattingDate = new DateFormatter(orderDate);
        String finalDateOutput = formattingDate.getDate();

        return new Order(orderedItems, "Incoming", finalDateOutput, orderType);
    }

    /*
    * Reads from JSON file with incoming orders and also read for savedstate.json file
    * */
    /**
     * Reads and parses a JSON Order file.
     *
     * @param orderFile - the filepath of the JSON Order file to be parsed.
     * @return an Order based upon the JSON file.
     * @throws IOException - IOException may be caused by reading files.
     * @throws ParseException - ParseException may be caused.
     */
    public List<Order> readOrdersFromJSON(File orderFile) throws IOException, ParseException{
        List<Order> allOrders = new ArrayList<>();
        JSONParser parser = new JSONParser();

        Object orderLevelObject = parser.parse(new FileReader(orderFile));

        if (orderLevelObject instanceof JSONArray){
            JSONArray finalArray = (JSONArray)orderLevelObject;

            for (Object obj : finalArray) {
                JSONObject orderInfo = (JSONObject) obj;
                JSONObject orderData;

                if (orderInfo.containsKey("order") && orderInfo.get("order") instanceof JSONObject) {
                    orderData = (JSONObject) orderInfo.get("order");
                } else {
                    orderData = orderInfo;
                }
                allOrders.add(jsonToOrder(orderData));
            }
        } else if (orderLevelObject instanceof JSONObject){
            JSONObject objectInfo = (JSONObject)orderLevelObject;

            if (objectInfo.containsKey("order") && objectInfo.get("order") instanceof JSONObject){
                JSONObject objectData = (JSONObject)objectInfo.get("order");
                allOrders.add(jsonToOrder(objectData));
            }
        }

        return allOrders;
    }


    /**
     * Writes an Order to JSON.
     *
     * @param theOrder - the order to be serialized to JSON.
     */
    void writeOrderToJSON(Order theOrder){
        File orderPath = new File("orders/completedOrder");
        if (!orderPath .exists()){
            orderPath.mkdirs();
            System.out.println("Create Directory");
        }
        File outputFile = new File(orderPath, "Order#" + theOrder.getOrderID() + ".json");
        try(FileWriter file = new FileWriter(outputFile)){
            file.write(formatForWriting(theOrder).toJSONString());
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
    protected JSONObject formatForWriting(Order incomingOrder){
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
     void writeAllOrderToFile(List<Order> allOrders){
        JSONArray allOrdersArray = new JSONArray();

        for (Order order : allOrders){
            JSONObject orderJSON = formatForWriting(order);
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


