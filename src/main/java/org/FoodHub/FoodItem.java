package org.FoodHub;

import java.io.Serializable;

/**
 * Class to store the attributes of an item of food.
 */
class FoodItem implements Serializable{
    /**
     * The attributes of an item of food.
     * name - name of food.
     * price - the price of the food.
     * quantity - how much/how many items of food.
     * serialVersionUID - Version for compatibility
     */
    private String name;
    private double price;
    private Long quantity;
    private static long serialVersionUID = 1L;

    /**
     * Creates a new FoodItem with the given attributes.
     *
     * @param name - name of the food.
     * @param quantity - quantity of the food.
     * @param price - price per-item of food.
     */
    FoodItem(String name, Long quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * @return the price of a FoodItem.
     */
    double getPrice(){
        return price;
    }

    /**
     * @return the name of a FoodItem.
     */
    String getName(){
        return name;
    }

    /**
     * @return the quantity of a FoodItem.
     */
    long getQuantity(){
        return this.quantity;
    }
}


