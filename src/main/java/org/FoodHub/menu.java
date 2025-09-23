package org.FoodHub;

import java.util.ArrayList;

public class menu {
    ArrayList<foodItem> restrauntMenu = new ArrayList<foodItem>();

    /// Create foodItem Objects with data (our precondition is to have a menu of food already)
    /// Create the food items and assign them to an ArrayList of Objects
    foodItem burger = new foodItem("Burger", 8.99);
    foodItem fries = new foodItem("Fries", 3.99);
    foodItem milkshake = new foodItem("Milkshake", 8.95);


    public menu() {
        restrauntMenu.add(burger);
        restrauntMenu.add(fries);
        restrauntMenu.add(milkshake);
    }

    public void addToMenu(String foodName, double price){
        foodItem newItem = new foodItem(foodName, price);
        restrauntMenu.add(newItem);
    }

    /// this getMenu method is for functionality of accessing items through orderManager class
    public ArrayList<foodItem> getMenu(){
        return restrauntMenu;
    }

    public String toString(){
        /// Want to loop through the array and format it into a string
        String formattedString = "";
        System.out.printf("%14s\n", "Menu");
        System.out.println("-----------------------------");
        int counter = 1;
        for (foodItem theItem : restrauntMenu){
            String formattingTheString = String.format("\t%d. %-15s $%.2f\n",counter, theItem.getName(), theItem.getPrice());
            formattedString += formattingTheString;
            counter++;
        }

        return formattedString;
    }

}
