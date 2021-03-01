package com.cardealership.service;

import com.cardealership.model.car.Car;
import com.cardealership.model.user.User;
import com.cardealership.util.DealershipList;

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

    //TODO: add following customer menu options
    /*
        2) Make an Offer on a Car
        3) View my Offers
        5) View my Remaining Payments
        6) Make a Payment
     */
    private void customerMenu() {
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t4) View My Owned Cars");
        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "1": printUnownedCars();
                break;
            case "4": printMyCars();
                break;
            case "0": logOut();
                break;
        }
    }

    //TODO: add following employee menu options
    /*
        3) Remove a Car from the Lot
        4) View All Offers
        5) Accept or Reject an Offer
        6) View all Payments
     */

    private void employeeMenu() {
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t2) Add a New Car to the Lot");

        System.out.println("\t\t\t0) Exit");
        switch(scan.nextLine()){
            case "1": printUnownedCars();
                break;
            case "2": addNewCar();
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

    private void printUnownedCars(){
        DealershipList<Car> cars = carService.getUnownedCars();
        if(cars != null) System.out.println(cars);
        else System.out.println("No cars were found");
    }
    private void printMyCars(){
        DealershipList<Car> cars = carService.getMyCars(currentUser.getId());

        if(cars.size() > 0) System.out.println(cars);
        else System.out.println("\t\tNo cars were found");
    }


    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        System.exit(0);
    }
}
