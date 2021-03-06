package com.cardealership.menu;

import com.cardealership.model.AccountType;
import com.cardealership.model.User;
import com.cardealership.service.UserService;

import java.util.Scanner;

public class AuthMenuService { // AuthMenuService acts as the parent service for a user's session
    static User currentUser; // static currentUser to store the user session
    static Scanner scan;
    UserService userService;

    public AuthMenuService(){ currentUser = new User(); scan = new Scanner(System.in); userService = new UserService(); }

    public void mainMenu(){
        System.out.println("------  Welcome to Car Dealership!  ------");
        menuDriver();
    }

    /**
     * MenuDriver() should act as a constant driver for menu serving
     */
    public void menuDriver(){
        switch (currentUser.getAccountType()){
            case UNREGISTERED:  registrationMenu();
                break;
            case USER:          userMenu();
                break;
            case CUSTOMER:
            case EMPLOYEE:      new CarMenuService(currentUser,scan).mainMenu();
                break;
        }
        menuDriver(); // uncomment this line when you are ready for recursion :D
    }

    private void registrationMenu() {
        System.out.println("\n\t\tWhat would you like to do?");
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
        System.out.println("\t\tWhat would you like to do?");
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
        refreshSession();
    }

    private void loginMenu() {
        String email,password;
        System.out.println("\t\tPlease enter the following:");
        System.out.print("\t\t\tEmail: ");
        email = scan.nextLine();
        System.out.print("\t\t\tPassword: ");
        password = scan.nextLine();
        login(email,password);
    }

    private void login(String email, String password){
        User temp;
        if((temp = userService.logIn(email,password)) != null){
            currentUser = temp;
            System.out.println("\t\tLogged in successfully.");
            System.out.println("\t\t\tHello, " + currentUser.getFirstName() +"!");
        }else
            System.out.println("\t\tCould not validate your credentials.");
    }

    private void refreshSession(){
        currentUser = userService.getUserByEmail(currentUser.getEmail());
    }

    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        System.exit(0);
    }


}
