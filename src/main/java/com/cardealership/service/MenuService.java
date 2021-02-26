package com.cardealership.service;

import com.cardealership.model.AccountType;
import com.cardealership.model.User;

import java.util.Scanner;

public class MenuService {
    static User currentUser; // static currentUser to store the user session
    static Scanner scan = new Scanner(System.in);
    UserService userService = new UserService();

    public MenuService(){
        currentUser = new User();
    }



    public void MainMenu(){
        System.out.println("------  Welcome to Car Dealership!  ------");
        MenuDriver();
    }

    /**
     * MenuDriver() should act as a constant driver for menu serving
     */
    public void MenuDriver(){
        if(!currentUser.getAccountType().equals(AccountType.UNREGISTERED))
            System.out.println("\t\t\tHello, " + currentUser.getFirstName() +"!");

        System.out.println("\t\tWhat would you like to do?");
        switch (currentUser.getAccountType()){
            case UNREGISTERED:  registrationMenu();
                break;
            case USER:          userMenu();
                break;
            case CUSTOMER:      customerMenu();
                break;
            case EMPLOYEE:      employeeMenu();
                break;
        }
        MenuDriver(); // uncomment this line when you are ready for recursion :D
    }

    private void registrationMenu() {
        System.out.println("\t\t\t1) Register");
        System.out.println("\t\t\t2) Login");
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "1": register();
                break;
            case "2": loginMenu();
                break;
            case "0": logOut();
                break;
        }
    }

    private void userMenu() {
        System.out.println("\t\t\t1) Register for Customer Account");
        System.out.println("\t\t\t2) Register for Employee Account");
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "1": register(AccountType.CUSTOMER);
                break;
            case "2": register(AccountType.EMPLOYEE);
                break;
            case "0": logOut();
                break;
        }
    }

    private void customerMenu() {
        System.out.println("\t\t\t1) View Dealership Cars"); // bidding functionality extracted to a CarMenuService
        System.out.println("\t\t\t2) View My Cars");    // view ownership and payments in CarMenuService
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "0": logOut();
                break;
        }
    }

    private void employeeMenu() {
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "0": logOut();
                break;
        }
    }

    private void register(){
        String firstName,lastName,email,password;
        do{
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tFirst Name: ");
            firstName = scan.nextLine();
            System.out.print("\t\t\tLast Name: ");
            lastName = scan.nextLine();
            System.out.print("\t\t\tEmail: ");
            email = scan.nextLine();
            System.out.print("\t\t\tPassword: ");
            password = scan.nextLine();
        }while(!userService.newUser(firstName,lastName,email,password));
        System.out.println("\t\tUser created successfully.");
        login(email,password);
    }

    private void register(AccountType accountType){
        userService.convertUser(currentUser,accountType);
        refreshSesssion();
    }

    private void loginMenu() {
        String email,password;
        do{
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tEmail: ");
            email = scan.nextLine();
            System.out.print("\t\t\tPassword: ");
            password = scan.nextLine();
        } while(!login(email,password));
        System.out.println("\t\tLogged in successfully.");
    }

    private boolean login(String email, String password){
        return (currentUser = userService.logIn(email,password)) != null;
    }

    private boolean refreshSesssion(){
        return (currentUser = userService.logIn(currentUser.getEmail(), currentUser.getPassword())) != null;
    }

    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        System.exit(0);
    }


}
