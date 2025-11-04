package org.FoodHub;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes orders that are loaded from files and written in JSON
 */
public class OrderProcessor {
    private final FileAccesser fileAccesser = new FileAccesser();

    public OrderProcessor(){
    }

    /**
     * Processes all order files in the orders directory.
     * @return list of all successfully processed orders
     * @throws IOException if file operations fail
     */
    public List<Order> processAllOrder() throws IOException {
        List<String> allFiles = fileAccesser.fetchOrderFolderList();
        List<Order> allOrder = new ArrayList<>();

        if (allFiles.isEmpty()) {
            System.out.println("No order to process");
            return allOrder;
        }
        for (String file : allFiles){
            try {
                allOrder.addAll(processSingleOrder(file));
                fileAccesser.moveProcessedFile(file);
            } catch (Exception e){
                String errorMessage = e.toString();
                System.err.println("Failed to process order: " + file + " for " + errorMessage);
                try{
                    fileAccesser.moveErrorFile(file);
                } catch(IOException e2){
                    e2.printStackTrace();
                }
            }
        }
        return allOrder;
    }

    /**
     * Uses parser to convert file contents to Order objects.
     * @param fileName name of the file to process
     * @return list of orders parsed from the file
     * @throws IOException on file read errors
     * @throws ParseException on JSON parsing errors
     * @throws ParserConfigurationException on parser config error
     * @throws SAXException on XML parsing errors
     */
    public List<Order> processSingleOrder(String fileName) throws IOException,ParseException,ParserConfigurationException,SAXException{
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
            return parser.loadToOrder(orderFile);
        }
        return List.of();
    }
    /**
     * Writes a single order to a JSON file.
     *
     * @param theOrder the order to write
     */
    public void writeToJSON(Order theOrder){
        jsonOrderParser parser = jsonOrderParser.getInstance();
        parser.writeOrderToJSON(theOrder);
    }
    /**
     * Writes a list of all orders to a JSON file.
     *
     * @param allOrders list of all orders to write
     */
    public void writeAllOrdersToFile(List<Order> allOrders){
        jsonOrderParser parser = jsonOrderParser.getInstance();
        parser.writeAllOrderToFile(allOrders);
    }


}
