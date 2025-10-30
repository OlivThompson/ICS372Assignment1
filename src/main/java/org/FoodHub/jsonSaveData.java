package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class jsonSaveData implements SaveState{
    private final OrderManager orderManager;
    jsonOrderParser orderParser = jsonOrderParser.getInstance();

    public jsonSaveData(OrderManager om){
        this.orderManager = om;
    }
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

    @Override
    public void load(File filePath, OrderManager om){
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
        }catch(IOException | ParseException e){
            System.err.println("\nSavedDataForLoad.json Corrupted");
        }
    }

}
