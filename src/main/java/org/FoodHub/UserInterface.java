package org.FoodHub;

import java.util.Scanner;

public class UserInterface {
    OrderManager manage = new OrderManager();
    Scanner scnr = new Scanner(System.in);
    String file;

    public UserInterface(){
        while(true){
            System.out.println("\n1. View Order List");
            System.out.println("2. Start Order");
            System.out.println("3. Read Order");
            System.out.println("4. Complete Order");
            System.out.println("5. Export Order");
            System.out.println("6. Export All Orders");
            System.out.println("7. Print Completed Orders");
            System.out.println("8. Print Incompleted Orders");
            System.out.println("9. Exit");
            int selector = scnr.nextInt();
            if (selector == 8){
                break;
            }

            switch(selector) {
                case 1:
                    manage.printOrderList();
                    break;
                case 2:
                    System.out.printf("What order do you want to start: ");
                    file = scnr.next();
                    manage.startIncomingOrder(file);
                case 3:
                    System.out.println("What order do you want to read: ");
                    file = scnr.next();
                    break;
            }
        }
    }


}
