package com.w4;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {


    public static void main(String[] args) {
        //Mock Database Initialization
        Warehouse warehouse = new Warehouse();
        Users users = new Users();
        defaultInitializer(warehouse);
        users.defaultInitializer();
        program(users,warehouse);
    }

    /**
     * Starts program with an initialized Warehouse obejct and an uninitialized users object and starts login/signup process.
     */
    public static void program(Users users,Warehouse warehouse){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press L/l to login or S/s to sign up: ");
        String loginOrSignUp = scanner.nextLine();
        try {
            if (loginOrSignUp.toUpperCase(Locale.ROOT).equals("L")) {
                login(users,warehouse);
            } else if (loginOrSignUp.toUpperCase(Locale.ROOT).equals("S")) {
                signUp(users,warehouse);
            } else {
                System.out.println("Wrong input,try again!");
                program(users,warehouse);
            }
        }
        catch (NullPointerException | InterruptedException e){
            System.out.println("Empty String,try L or S for input!");
        }
    }


    /**
     * Receives username and password and authenticates if they are signed up.
     */
    public static void login(Users user,Warehouse warehouse) throws InterruptedException {
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter your Username(default user: user): ");
        String username = scanner.nextLine();
        System.out.println("Please Enter your Password(default pass: pass): ");
        String password = scanner.nextLine();
        if(user.authenticate(username,password)){
            System.out.println("Welcome to The Online Shop!");
            showList(cart,warehouse);
        }
        else {System.out.println("Wrong username/password! try again."); program(user,warehouse);}
    }

    /**
     * Creates Users class and initialize with user details.
     */
    public static void signUp(Users user,Warehouse warehouse){
        user.signUp();
        program(user,warehouse);
    }
    /**
     * Shows two options: Shopping list and User's Cart.
     */
    public static void showList(Cart cart,Warehouse warehouse) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Choose one of the Options:
                1-Shop
                2-View Shopping Cart
                """);
        String option = scanner.nextLine();
        switch (option) {
            case "1" -> shop(cart,warehouse);
            case "2" -> manageCart(cart,warehouse);
            default -> {
                System.out.println("Wrong Option!");
                showList(cart,warehouse);
            }
        }
    }

    /**
     * Shows a menu of the shop items plus exit option and asks for option and then quantity of the item if user haven't chosen to exit.
     */
    public static void shop(Cart cart, Warehouse warehouse) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the items:");
        sleep(400);
        warehouse.viewWarehouse();
        System.out.println((warehouse.getItem().length + 1) + "-Exit Shopping List");
        System.out.println();
        String option = scanner.nextLine();
        int itemIndex = Integer.parseInt(option) - 1;

        if (itemIndex >= 0 && itemIndex < warehouse.getItem().length) {
            cart.addToCart(warehouse.getItem()[itemIndex],warehouse.getCategory()[itemIndex],warehouse.getPrice()[itemIndex],warehouse);
        }
        else if (Integer.parseInt(option) == warehouse.getItem().length + 1) {
            showList(cart,warehouse);
        }
        else {
            System.out.println("Wrong Option,Choose another one!");
            shop(cart, warehouse);
        }
    }

    public static void manageCart(Cart cart, Warehouse warehouse) throws InterruptedException {
        if(cart.checkIfCartIsNotEmpty()) {
            Scanner scanner = new Scanner(System.in);
            cart.viewCart();
            System.out.println("Enter D/d for deleting an item,Enter F/f for finalizing order or Enter B/b to go back to the Shop");
            String option = scanner.nextLine();
            switch (option.toUpperCase(Locale.ROOT)) {
                case "D" -> cart.deleteFromCart(warehouse);
                case "F" -> cart.finalizeOrder(warehouse);
                case "B" -> showList(cart,warehouse);
                default -> {
                    System.out.println("Wrong input,Try D,F,B.");
                    manageCart(cart, warehouse);
                }
            }
        }
        else if(!cart.checkIfCartIsNotEmpty()){
            System.out.println("Your Cart is empty,You will be redirected to the Menu.");
            sleep(650);
            showList(cart,warehouse);
        }
    }

    static void defaultInitializer(Warehouse warehouse){
        warehouse.addItem("Radio","Electronics",100000,"Plays Sounds",10);
        warehouse.addItem("TV","Electronics",200000,"Shows Pictures",20);
        warehouse.addItem("Sport Shoe","Shoes",20000,"It's the nicest Shoe!",40);
        warehouse.addItem("Formal Shoe","Shoes",70000,"It's the most uncomfortable shoe!",30);
        warehouse.addItem("Book","Reading",12000,"You can read this!",50);
        warehouse.addItem("Newspaper","Reading",6000,"You can read and watch this!",150);
    }

    static void colorSelector(String category,String item) throws InterruptedException {
        switch (category) {
            case "Electronics" -> {
                printRed(item);
                sleep(500);
            }
            case "Reading" -> {
                printGreen(item);
                sleep(500);
            }
            case "Shoes" -> {
                printYellow(item);
                sleep(500);
            }
        }
    }
    static void printRed(String input){
        System.out.println("------------------------------\n" + ANSI_RED + input + ANSI_RESET);
    }
    static void printGreen(String input){
        System.out.println("------------------------------\n" + ANSI_GREEN + input + ANSI_RESET);
    }
    static void printYellow(String input){
        System.out.println("------------------------------\n" + ANSI_YELLOW + input + ANSI_RESET);
    }

    public static final String ANSI_RESET =     "\u001B[0m";
    public static final String ANSI_RED =       "\u001B[31m";
    public static final String ANSI_GREEN =     "\u001B[32m";
    public static final String ANSI_YELLOW =    "\u001B[33m";

}
