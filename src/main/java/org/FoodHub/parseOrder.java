package org.FoodHub;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class parseOrder {
    private String filePath = "";
    private JSONObject objectInfo;

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
    public ArrayList<String> getItems(){
        ArrayList<String> items = new ArrayList<>();
        /// Create another JSONObject in order to get access into the (order's) data
        JSONObject objectItemDataAccess = (JSONObject)objectInfo.get("order");
        JSONArray itemsArray = (JSONArray)objectItemDataAccess.get("items");
        for (Object itemObject : itemsArray){
            JSONObject itemData = (JSONObject)itemObject;
            String itemName = (String)itemData.get("name");
            items.add(itemName);
        }
        return items;
    };
}
