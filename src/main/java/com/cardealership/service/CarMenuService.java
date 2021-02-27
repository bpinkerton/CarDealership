package com.cardealership.service;

import com.cardealership.model.user.User;

import java.util.List;
import java.util.Scanner;

public class CarMenuService {
    static User currentUser;
    static Scanner scan;
    CarService carService = new CarService();

    public CarMenuService(User user, Scanner scanner){ currentUser=user; scan = scanner; }

    public void mainMenu(){
        System.out.println("------   Car Dealership Main Menu   ------");
        menuDriver();
    }

    private void menuDriver() {
        System.out.println("\t\tWhat would you like to do?");
        switch (currentUser.getAccountType()){
            case CUSTOMER:      customerMenu();
                break;
            case EMPLOYEE:      employeeMenu();
                break;
        }

        menuDriver(); // uncomment this line when you are ready for recursion :D
    }

    private void customerMenu() {
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "0": logOut();
                break;
        }
    }

    private void employeeMenu() {
        System.out.println("\t\t\t1) Add a New Car to the Lot");
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "1": addNewCar();
                break;
            case "0": logOut();
                break;
        }
    }

    private void addNewCar() {
        String make,model,year;
        double price;
        do{
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tMake: ");
            make = scan.nextLine();
            System.out.print("\t\t\tModel: ");
            model = scan.nextLine();
            System.out.print("\t\t\tYear: ");
            year = scan.nextLine();
            System.out.print("\t\t\tPrice: ");
            price = Double.parseDouble(scan.nextLine());
        }while(!carService.newCar(make,model,year,price));
        System.out.println("\t\tCar created successfully.");
    }


    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        System.exit(0);
    }
}
