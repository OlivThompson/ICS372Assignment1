package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import java.util.Map;

public class jsonSaveData implements SaveState{
    private final OrderManager orderManager;
    private final OrderParser orderParser = OrderParser.getInstance();

    public jsonSaveData(OrderManager om){
        this.orderManager = om;
    }
    @Override
    public void save(OrderManager om){
        JSONArray ordersArray = new JSONArray();
        JSONObject orderObj;

        for (Order o : om.getOrders()){
            orderObj = orderParser.formatForWriting(o);
            ordersArray.add(orderObj);
        }


        File exportToPath = new File("CurrentState");
        if  (!exportToPath.exists()){
            exportToPath.mkdirs();
            System.out.println("Created Directory");
        }
        try{
            FileWriter write = new FileWriter("Test.json");
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
                List<Order> loadOrders = orderParser.readOrdersFromJSON(filePath);
                for (Order o : loadOrders) {
                    om.addOrder(o);
                }
                System.out.println("Test Successfully");
            }
        }catch(IOException | ParseException e){
            e.printStackTrace();
        }
    }

}
