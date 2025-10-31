package org.FoodHub;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    private final FileAccesser fileAccesser = new FileAccesser();

    public OrderProcessor(){
    }

    public List<Order> processAllOrder(){
        List<String> allFiles = fileAccesser.fetechOrderFolderList();
        List<Order> allOrder = new ArrayList<>();

        if (allFiles.isEmpty()) {
            System.out.println("No order to process");
            return allOrder;
        }
            for (String file : allFiles){
                allOrder.addAll(processSingleOrder(file));
            }
            return allOrder;
    }

    public List<Order> processSingleOrder(String fileName){
        File orderFile;
        String fileExtension;
        if (!fileName.equals("SavedDataForLoad.json")) {
            orderFile = new File("orders" + File.separator + fileName);
            fileExtension = fileAccesser.getExtension(fileName);
        }
        else{
            orderFile = new File(fileName);
            fileExtension = fileAccesser.getExtension(fileName);
        }

        OrderParserInterface parser = AbstractedOrderParserFactory.getParser(fileExtension);
        if (parser != null){
            try{
                return parser.loadToOrder(orderFile);
            }catch(IOException|ParseException e){
                e.printStackTrace();
            }
        }
        return List.of();
    }

    public void writeToJSON(Order theOrder){
        jsonOrderParser parser = jsonOrderParser.getInstance();
        parser.writeOrderToJSON(theOrder);
    }

    public void writeAllOrdersToFile(List<Order> allOrders){
        jsonOrderParser parser = jsonOrderParser.getInstance();
        parser.writeAllOrderToFile(allOrders);
    }


}
