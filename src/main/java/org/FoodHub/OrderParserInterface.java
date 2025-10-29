package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface OrderParserInterface {
    List<Order> loadToOrder(File orderFile) throws IOException, ParseException;
}
