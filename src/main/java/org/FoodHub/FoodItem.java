package org.FoodHub;

public class FoodItem {
    private String name;
    private double price;
    private Long quantity;

    public FoodItem(String name, Long quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void foodSetPrice(double price){
        this.price = price;
    }

    public void setFoodQuantity(long quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public long getQuantity(){
        return this.quantity;
    }

    @Override
    public String toString(){
        return String.format("Name: %s, Price: %f, Quantity: %d", this.name, this.price, this.quantity);
    }

}

