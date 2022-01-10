package com.w4;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Users {
    private String[] username = new String[100];
    private String[] password = new String[100];
    private String[] firstname = new String[100];
    private String[] lastname = new String[100];
    private String[] phoneNumber = new String[100];
    private String[] emailAddress = new String[100];
    private String[] region = new String[100];
    private String[] city = new String[100];
    private String[] avenue = new String[100];
    private String[] postalCode = new String[100];
    private int i = 1;



    public void signUp(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please fill each field and press Enter: ");
        System.out.println("Username: ");
        this.username[i] = primaryAdder(this.username);
        System.out.println("Password: ");
        this.password[i] = scanner.nextLine();
        System.out.println("Firstname: ");
        this.firstname[i] = scanner.nextLine();
        System.out.println("Lastname: ");
        this.lastname[i] = scanner.nextLine();
        System.out.println("Phone Number: (Example: +989123456789/09123456789)");
        this.phoneNumber[i] = regexValidatorInitializer(phoneNumberRegexPattern);
        System.out.println("Email Address: (Example: ali@hoseini.com)");
        this.emailAddress[i] = regexValidatorInitializer(emailRegexPattern);
        System.out.println("Region: ");
        this.region[i] = scanner.nextLine();
        System.out.println("City: ");
        this.city[i] = scanner.nextLine();
        System.out.println("Avenue: ");
        this.avenue[i] = scanner.nextLine();
        System.out.println("Postal Code: ");
        this.postalCode[i] = scanner.nextLine();

        i++;
    }

    /**
     * Checks if a specific username matches the password in two arraylists of username and password with same size.
     */
    public boolean authenticate(String user, String pass){
        boolean flag = false;
        for (int i = 0; i < username.length; i++) {
            if(username[i] != null) {
                String validUser = username[i];
                String validPass = password[i];
                if (validUser.equals(user) && validPass.equals(pass)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public String primaryAdder(String[] target){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String output = null;
        while(true){
            if(checkExistence(input,target)){
                System.out.println("This " + input + " Already Exists! Try another one: ");
                primaryAdder(target);
            }
            else {
                output = input;
            }
            break;
        }
        return output;
    }

    public boolean checkExistence(String input,String[] target){
        boolean flag = false;
        for(int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                if (target[i].equals(input)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public String regexValidatorInitializer(String[] regex){
        String output = null;
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(true){
            if(checkRegex(input,regex[0])){
                output = input;
            }
            else{
                System.out.println("Wrong " + regex[1] + " Format. Enter a Correct " + regex[1] + " Format:");
                regexValidatorInitializer(regex);
            }
            break;
        }
        return output;
    }

    public boolean checkRegex(String input, String regexPattern){
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    public String[] getUsername() {
        return username;
    }

    public String[] getPassword() {
        return password;
    }

    private String[] emailRegexPattern = new String[]{"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$","Email"};
    private String[] phoneNumberRegexPattern = new String[]{"(\\+989|9|09)(12|19|35|36|37|38|39|32)\\d{7}","Phone Number"};

    public void defaultInitializer() {
        this.username[0] = "user";
        password[0] = "pass";
        firstname[0] = "user";
        lastname[0] = "admin";
        phoneNumber[0] = "09356637058";
        emailAddress[0] = "user@admin.com";
        region[0] = "Tehran";
        city[0] = "Tehran";
        avenue[0] = "Enghelab";
        postalCode[0] = "1111111111";
    }
}