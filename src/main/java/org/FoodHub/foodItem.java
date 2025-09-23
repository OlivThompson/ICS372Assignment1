package org.FoodHub;

public class foodItem {
    private String food;
    private double price;

    public foodItem(String food){
        this.food = food;
    }
    public foodItem(String food, double price){
        this.food = food;
        this.price = price;
    }

    public void foodSetPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public String getName(){
        return food;
    }

}
