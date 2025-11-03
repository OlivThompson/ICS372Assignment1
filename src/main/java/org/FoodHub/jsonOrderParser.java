package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class jsonOrderParser implements OrderParserInterface{
    /*
     * Use the Singleton Pattern into OrderParser because we just need to use one instance of it
     * */
    private static volatile jsonOrderParser instance;

    private jsonOrderParser(){

    }

    public static jsonOrderParser getInstance(){
        if (instance == null){
            synchronized (jsonOrderParser.class){
                if (instance == null){
                    instance = new jsonOrderParser();
                }
            }
        }
        return instance;
    }

    /*
     *   Converting Order Data from JSONObject into Order previously readOrderFromJson
     * */
    private OrderStatus parseOrderStatus(String status) {
        if (status == null) return OrderStatus.INCOMING;
        try {
            return OrderStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return OrderStatus.INCOMING;
        }
    }

    private OrderType parseOrderType(String type) {
        if (type == null) return null;
        try {
            return OrderType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Order jsonToOrder(JSONObject orderData) throws ParseException {
        String orderType = (String)orderData.get("type");
        Long orderDate = (Long)orderData.get("order_date");
        if (orderType == null){
            throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN, "Missing Data, Order will not be processed");
        }
        // Will check if the order has order_date, if not will create the time at the moment it is being processed
        if (orderDate == null){
            orderDate = Instant.now().toEpochMilli();
        }
        JSONArray itemsArray = (JSONArray)orderData.get("items");
        List<FoodItem> orderedItems = new ArrayList<>();

        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String name = (String)itemData.get("name");
            Long quantityLong = (Long)itemData.get("quantity");
            Double price = (Double)itemData.get("price");

            if (name == null || quantityLong == null || price == null){
                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN, "Missing Data, Order will not be processed");
            }

            int quantity = quantityLong != null ? quantityLong.intValue() : 0;
            double finalPrice = price != null ? price.doubleValue() : 0.0;
            orderedItems.add(new FoodItem(name, quantity, finalPrice));
        }

        String orderStatus;
        if (orderData.containsKey("order_status") && orderData.get("order_status") instanceof String){
            orderStatus = (String)orderData.get("order_status");
        }else{
            orderStatus = "Incoming";
        }

        OrderStatus enumStatus = parseOrderStatus(orderStatus);
        OrderType enumType = parseOrderType(orderType);
        if (enumType == null) enumType = OrderType.PICKUP; // Default fallback

        return new Order(orderedItems, enumStatus, orderDate, enumType);
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
    @Override
    public List<Order> loadToOrder(File orderFile) throws IOException, ParserConfigurationException, SAXException, ParseException {
        List<Order> allOrders = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(orderFile)) {
            Object orderLevelObject = parser.parse(reader);

            if (orderLevelObject instanceof JSONArray) {
                JSONArray finalArray = (JSONArray) orderLevelObject;

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
            } else if (orderLevelObject instanceof JSONObject) {
                JSONObject objectInfo = (JSONObject) orderLevelObject;

                if (objectInfo.containsKey("order") && objectInfo.get("order") instanceof JSONObject) {
                    JSONObject objectData = (JSONObject) objectInfo.get("order");
                    allOrders.add(jsonToOrder(objectData));
                }
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
     *
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
        orderObj.put("order_status", incomingOrder.getOrderStatus().name());
        orderObj.put("type", incomingOrder.getOrderType().name());
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
