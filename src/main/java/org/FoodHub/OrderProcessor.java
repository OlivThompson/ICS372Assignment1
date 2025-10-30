package org.FoodHub;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderProcessor {
    private final FileAccesser fileAccesser = new FileAccesser();

    public OrderProcessor(){
    }

    public void processAllOrder(){
        List<String> allFiles = fileAccesser.fetechOrderFolderList();

        if (allFiles.isEmpty()){
            System.out.println("No order to process");

            for (String file : allFiles){
                processSingleOrder(file);
            }

        }
    }

    public List<Order> processSingleOrder(String fileName){
        File orderFile = new File("orders" + File.separator + fileName);
        String fileExtension = fileAccesser.getExtension(fileName);

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
