package org.FoodHub;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
public class OrderManagerInterface {
    private OrderParser orderParser = new OrderParser();
    private OrderManager orderManager = new OrderManager();
    private Scanner s = new Scanner(System.in);

    public OrderManagerInterface() throws IOException, ParseException {
    }

    public void printUserOptions()  {
        String userMenu = """
                User Menu
                1. Add Order
                2. Cancel Order
                3. Start Incoming Order
                4. View Incoming Orders
                5. Complete Incoming Order
                6. View Incomplete Orders
                7. Export All Orders
                """;
    }

    public void loopMenu() {
        while(true) {
            printUserOptions();
            parseUserInput(getUserInput());
        }
    }

    private void parseUserInput(int userInput) {
        switch (userInput) {
            case 1:
                System.out.println("option1");
                break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            case 7: break;

        }
    }

    private int getUserInput() {
        return s.nextInt();
    }


}
