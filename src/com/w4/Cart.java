package com.w4;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Cart {
    private String[] item = new String[5];
    private String[] category = new String[5];
    private int[] qty = new int[5];
    private int[] price = new int[5];
    private int i = 0;

    public boolean checkIfCartIsNotEmpty(){
        boolean flag = false;
        for (String i : item) {
            if (i != null) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void viewCart() throws InterruptedException {
        for (int i = 0; i < this.getItem().length; i++) {
            if(getItem()[i] != null) {
                Main.colorSelector(this.getCategory()[i], getItemDetails(i));
            }
        }
        System.out.println("Total Price: " + totalPrice() + "\n");
    }

    public String getItemDetails(int index){
        return "Item" + (index + 1) + "-" + getItem()[index] + "\n" +
                "Price: " + getPrice()[index] + "\n" +
                "Quantity: " + getQty()[index];
    }

    public int totalPrice(){
        int sum = 0;
        for (int i = 0; i < item.length; i++) {
            sum = sum + qty[i] * price[i];
        }
        return sum;
    }

    public void addToCart(String item,String category, int price,Warehouse warehouse) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int itemIndex = warehouse.searchByItem(item);
        int warehouseQty = warehouse.getQuantity()[itemIndex];
        if (!checkIfCartIsFull()) {
            if (!checkIfItemExistsInCart(item)) {
                if (warehouse.checkItemAvailability(item)) {
                    askForQuantityMessage(item,warehouseQty);
                    int orderQty = Integer.parseInt(scanner.nextLine());
                    if (orderQty > warehouseQty) {
                        System.out.println("Not enough item in Stock!");
                    }
                    else {
                        i++;
                        if (i > 5) {
                            i = 1;
                        }
                        initializeCart(item, category, orderQty, price);
                        warehouse.setQuantity(item, warehouse.getQuantity()[itemIndex] - orderQty);
                        itemAddedMessage(item,orderQty);
                    }
                    Main.shop(this, warehouse);
                }
                else if(!warehouse.checkItemAvailability(item)){
                    itemDoesNotExistMessage();
                    Main.shop(this,warehouse);
                }
            } else if (checkIfItemExistsInCart(item)) {
                askForQuantityMessage(item,warehouseQty);
                int orderQty = Integer.parseInt(scanner.nextLine());
                if (warehouse.checkItemAvailability(item)) {
                    int searchedItem = searchByItem(item);
                    this.qty[searchedItem] += orderQty;
                    warehouse.setQuantity(item, warehouse.getQuantity()[warehouse.searchByItem(item)] - warehouseQty);
                    itemAddedMessage(item, orderQty);
                    Main.shop(this, warehouse);
                }
                else if (!warehouse.checkItemAvailability(item)) {
                    itemDoesNotExistMessage();
                    Main.shop(this,warehouse);
                }
            }

        } else {
            System.out.println("Your cart limit is 5 and it's full! you will now be redirected to your shopping cart.");
            sleep(500);
            Main.manageCart(this, warehouse);
        }
    }

    public void finalizeOrder(Warehouse warehouse) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pay for order by Y/y or cancel payment by N/n:");
        String option = scanner.nextLine();
        if(option.toUpperCase(Locale.ROOT).equals("Y")){
            Arrays.fill(this.item,null);
            Arrays.fill(this.category,null);
            Arrays.fill(this.qty,0);
            Arrays.fill(this.price,0);
            i = 0;
            System.out.println("Your order was finalized! You will be redirected to The Mock online shop now!");
            sleep(900);
            Main.showList(this,warehouse);
        }
        else if(option.toUpperCase(Locale.ROOT).equals("N")){
            System.out.println("Finalization canceled. You will now be redirected to your Cart");
            sleep(900);
            Main.manageCart(this,warehouse);
        }
    }

    public void deleteFromCart(Warehouse warehouse) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the number of the item you want to delete:");
        viewCart();
        int option = Integer.parseInt(scanner. nextLine());
        String optionItem = getItem()[option - 1];
        int tempQty = getQty()[option - 1];
        emptyCart(option - 1);
        System.out.println(tempQty + "x " + optionItem + " were removed from your cart.");
        sleep(500);
        warehouse.setQuantity(optionItem,warehouse.getQuantity()[warehouse.searchByItem(optionItem)] + tempQty);
        Main.manageCart(this,warehouse);
    }

    public void initializeCart(String item,String category,int qty,int price){
        this.item[i - 1] = item;
        this.category[i - 1] = category;
        this.qty[i - 1] = qty;
        this.price[i - 1] = price;
    }

    public void emptyCart(int input){
        getItem()[input] = null;
        getPrice()[input] = 0;
        getQty()[input] = 0;
    }

    public boolean checkIfCartIsFull(){
        boolean flag = false;
        for (int i = 0; i < this.item.length; i++) {
            flag = getItem()[i] != null;
        }
        return flag;
    }

    public void askForQuantityMessage(String item,int quantity){
        System.out.println("How Many " + item + "s?" + "(In Stock:" + quantity + ")");
    }
    public void itemAddedMessage(String item,int quantity){
        System.out.println(quantity + "x" + item + "s were added to the Cart");
    }
    public void itemDoesNotExistMessage(){
        System.out.println("Item does not exist in the Warehouse. No Item was added to the cart");
    }

    public boolean checkIfItemExistsInCart(String item){
        boolean flag = false;
        for(int i = 0; i < this.item.length; i++) {
            if (getItem()[i] != null) {
                if (this.item[i].equals(item)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public int searchByItem(String item){
        int location = 0;
        for (int i = 0; i < getItem().length; i++) {
            if(getItem()[i].equals(item)){
                location = i;
                break;
            }
        }
        return location;
    }

    public String[] getItem() {
        return item;
    }

    public String[] getCategory() {
        return category;
    }

    public int[] getQty() {
        return qty;
    }

    public int[] getPrice() {
        return price;
    }
}
