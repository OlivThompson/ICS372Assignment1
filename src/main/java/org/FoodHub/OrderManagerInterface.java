package org.FoodHub;
import java.util.Scanner;
public class OrderManagerInterface {
    private parseOrder orderParser = new parseOrder();
    private OrderManager orderManager = new OrderManager();
    private Scanner s = new Scanner(System.in);

    public void printUserOptions()  {
        String userMenu = """
                User Menu
                1. Add Order
                2. Cancel Order
                3. View Incoming Orders
                4. ?
                5. Export All Orders
                6. Help!
                7. Exit
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
            case 1: break;
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
