package org.FoodHub;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * Interface for parsing order data from files.
 */
public interface OrderParserInterface {
    /**
     * Loads orders from a file and returns as a list.
     *
     * @param orderFile file from where orders are loaded from.
     * @return list of orders parsed from the file
     * @throws IOException on I/O errors
     * @throws ParserConfigurationException parser config errors
     * @throws SAXException XML parsing errors
     * @throws ParseException JSON parsing errors
     */
    List<Order> loadToOrder(File orderFile) throws IOException, ParserConfigurationException, SAXException, ParseException;
}
