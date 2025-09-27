package org.FoodHub;

/**
 * Class to store the attributes of an item of food.
 */
public class FoodItem {
    /**
     * The attributes of an item of food.
     */
    private String name;
    private double price;
    private Long quantity;

    /**
     * Creates a new FoodItem with the given attributes.
     *
     * @param name - name of the food.
     * @param quantity - quantity of the food.
     * @param price - price per-item of food.
     */
    public FoodItem(String name, Long quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Sets the price of a FoodItem.
     *
     * @param price - the new price of the FoodItem.
     */
    public void foodSetPrice(double price){
        this.price = price;
    }

    /**
     * Sets the quantity of a FoodItem.
     *
     * @param quantity - new quantity of the FoodItem.
     */
    public void setFoodQuantity(long quantity){
        this.quantity = quantity;
    }

    /**
     * @return the price of a FoodItem.
     */
    public double getPrice(){
        return price;
    }

    /**
     * @return the name of a FoodItem.
     */
    public String getName(){
        return name;
    }

    /**
     * @return the quantity of a FoodItem.
     */
    public long getQuantity(){
        return this.quantity;
    }
}


