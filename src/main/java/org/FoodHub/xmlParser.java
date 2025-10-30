package org.FoodHub;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class xmlParser implements OrderParserInterface{
    /*
    * Singleton Pattern for the xmlParser
    *
    * */
    private static volatile xmlParser instance;

    private xmlParser(){}

    public static xmlParser getInstance(){
        if (instance == null){
            synchronized (xmlParser.class){
                if (instance == null){
                    instance = new xmlParser();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Order> loadToOrder(File filePath) {
        List<Order> allOrder = new ArrayList<>();
        List<FoodItem> allFood = new ArrayList<>();
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            NodeList orderList = document.getElementsByTagName("Order");

            for (int i = 0; i < orderList.getLength(); i++){

                Element order = (Element)orderList.item(i);
                String orderID = order.getAttribute("id");
                String orderType = order.getElementsByTagName("OrderType").item(0).getTextContent();

                NodeList itemList = order.getElementsByTagName("Item");
                for (int j = 0; j < itemList.getLength(); j++){
                    Element item = (Element)itemList.item(j);
                    String type = item.getAttribute("type");
                    String priceString = item.getElementsByTagName("Price").item(0).getTextContent();
                    String quantityString = item.getElementsByTagName("Quantity").item(0).getTextContent();
                    double price = Double.parseDouble(priceString);
                    int quantity = Integer.parseInt(quantityString);
                    FoodItem currentFood = new FoodItem(type, quantity, price);
                    allFood.add(currentFood);
                }

                Instant orderedTime = Instant.now();
                Long convertedToMili = orderedTime.toEpochMilli();
                Order currentO = new Order(allFood, "Incoming", convertedToMili,orderType);
                allOrder.add(currentO);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return allOrder;
    }


}
