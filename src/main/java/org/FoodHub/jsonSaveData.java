package org.FoodHub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.util.Map;

public class SaveData implements SaveState{
    @Override
    void save(){

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

}
