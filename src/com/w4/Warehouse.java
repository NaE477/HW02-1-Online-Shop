package com.w4;

import java.util.ArrayList;

public class Warehouse {
    private String[] item = new String[6];
    private String[] description = new String[6];
    private String[] category = new String[6];
    private int[] price = new int[6];
    private int[] quantity = new int[6];
    private int i = 0;

    public void addItem(String item,String category,int price,String description,int quantity) {
        this.item[i] = item;
        this.price[i] = price;
        this.description[i] = description;
        this.quantity[i] = quantity;
        this.category[i] = category;
        this.i++;
    }

    public boolean checkItemAvailability(String item){
        return getQuantity()[searchByItem(item)] > 0;
    }



    public String itemDetails(int index){
        return (index + 1) + "-" +
                getItem()[index] + "\nDescription: " +
                getDescription()[index] + "\nPrice: " +
                getPrice()[index] + "\nQuantity: " +
                getQuantity()[index] + "\n";
    }

    public void viewWarehouse() throws InterruptedException {
        for (int i = 0; i < this.getItem().length; i++) {
            Main.colorSelector(this.getCategory()[i],itemDetails(i));
        }
    }
    public String[] getCategory() {
        return category;
    }

    public String[] getItem() {
        return item;
    }

    public int[] getPrice() {
        return price;
    }

    public String[] getDescription() {
        return description;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(String item,int newQuantity) {
        this.quantity[searchByItem(item)] = newQuantity;
    }
    public  int searchByItem(String item){
        int location = 0;
        for (int i = 0; i < getItem().length; i++) {
            if(getItem()[i].equals(item)){
                location = i;
                break;
            }
        }
        return location;
    }
}
