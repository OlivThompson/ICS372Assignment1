package org.FoodHub;

public class foodItem {
    private String name;
    private double price;
    private int quantity;

    public foodItem(String food){
        this.name = food;
    }
    public foodItem(String name, int quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void foodSetPrice(double price){
        this.price = price;
    }

    public void setFoodQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String toString(){
        return name;
    }

}
