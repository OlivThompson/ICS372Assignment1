package org.FoodHub;

import java.io.File;
import java.util.Scanner;

public class UserInterface {
    OrderManager manage = new OrderManager();
    Scanner scnr = new Scanner(System.in);
    String file;

    public UserInterface(){
        while(true){
            System.out.println("\n1. View Incoming Order List");
            System.out.println("2. Add Order");
            System.out.println("3. Read Order");
            System.out.println("4. Start Order");
            System.out.println("5. Complete Order");
            System.out.println("6. Export Order");
            System.out.println("7. Export All Orders");
            System.out.println("8. Print Completed Orders");
            System.out.println("9. Print Incompleted Orders");
            System.out.println("10. Exit");
            int selector = scnr.nextInt();
            scnr.nextLine();
            if (selector == 10){
                break;
            }

            switch(selector) {
                case 1:
                    manage.printOrderList();
                    break;
                case 2:
                    System.out.printf("What order do you want to add: ");
                    file = scnr.nextLine();
                    manage.addOrder("orders" + File.separator + file + ".json");
                    break;
                case 3:
                    System.out.println("What order do you want to read: ");
                    file = scnr.next();
                    break;
            }
        }
    }


}
