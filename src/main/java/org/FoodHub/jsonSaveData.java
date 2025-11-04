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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Saves and loads order data.
 * Uses OrderManager to manage orders during save and load operations.
 * Handles file reading and writing, including error logging for corrupted data.
 */

public class jsonSaveData implements SaveState{
    private final OrderManager orderManager;
    jsonOrderParser orderParser = jsonOrderParser.getInstance();

    /**
     * Constructs a jsonSaveData instance
     *
     * @param om the OrderManager instance
     */
    public jsonSaveData(OrderManager om){
        this.orderManager = om;
    }

    /**
     * Saves all orders from the OrderManager to JSON file
     * @param om OrderManager containing orders to save
     * @param filePath the file where order data will be written
     */
    @Override
    public void save(OrderManager om, File filePath){
        JSONArray ordersArray = new JSONArray();
        JSONObject orderObj;

        for (Order o : om.getOrders()){
            orderObj = orderParser.formatForWriting(o);
            ordersArray.add(orderObj);
        }


        try{
            FileWriter write = new FileWriter(filePath);
            write.write(ordersArray.toJSONString());
            write.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Loads order data from JSON file into the OrderManager.
     *
     * @param filePath JSON file to load order data from
     * @param om OrderManager to add loaded orders to
     */
    @Override
    public void load(File filePath, OrderManager om) {
        try {
            if (!filePath.exists()){
                System.out.println("There is no save data, starting a new session");
            }
            else {
                List<Order> loadOrders = orderParser.loadToOrder(filePath);
                for (Order o : loadOrders) {
                    om.addOrder(o);
                }
            }
        }catch(IOException | ParseException | ParserConfigurationException | SAXException e){
            System.err.println("\nSavedDataForLoad.json Corrupted");
        }
    }

}
