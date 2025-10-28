/*package org.FoodHub;*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/*
import java.util.Map;

public class SaveData implements SaveState{
    @Override
    public void save(Map<String, Restaurant> restaurants){

    }



*/

    /// Converts all current data into a json file
/*
    public SaveData(Map<String, Restaurant> restaurants) {
        JSONObject restaurantObject = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONObject orderObj = new JSONObject();

        JSONArray objArray = new JSONArray();
        JSONArray objOrdersArray = new JSONArray();


        for (Restaurant r : restaurants.values()){
            restaurantObject.put("Restaurant", r.getName());
            objArray.add(restaurantObject);
        }
        for (Restaurant r : restaurants.values()){
            for (Order o : r.getAllOrder()){
                orderObj.put("OrderID", o.getOrderID());
                orderObj.put("Order Status", o.getStatus());
            }
            obj.put("Resturant", orderObj);
        }

        File exportToPath = new File("Restaurants");
        if  (!exportToPath.exists()){
            exportToPath.mkdirs();
            System.out.println("Created Directory");
        }

        try{
            FileWriter write = new FileWriter("Test.json");
            write.write(obj.toJSONString());
            write.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
*/

//}
