package org.FoodHub;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for managing and providing order parsers
 */
public class AbstractedOrderParserFactory {
    private static final Map<String, OrderParserInterface> registeredParser = new ConcurrentHashMap<>();

    static{
        registerParser("JSON", jsonOrderParser.getInstance());
        registerParser("XML", xmlParser.getInstance());
    }

    /**
     * Registers a parser instance for the given format.
     * @param format the string format.
     * @param parserInstance parser instance associated with format.
     */
    public static void registerParser(String format, OrderParserInterface parserInstance){
        registeredParser.put(format.toUpperCase(), parserInstance);
    }

    /**
     * Retrieves the parser instance
     *
     * @param format string format to get the parser
     * @return the parser instance for the format
     */
    public static OrderParserInterface getParser(String format){
        OrderParserInterface parser = registeredParser.get(format.toUpperCase());

        if (parser == null){
            System.out.println("Format not supported");
            System.out.println("Available Format: " + registeredParser.keySet());
        }
        return parser;
    }


}
