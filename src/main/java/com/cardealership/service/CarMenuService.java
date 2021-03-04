package com.cardealership.service;

import com.cardealership.model.Car;
import com.cardealership.model.Offer;
import com.cardealership.model.User;
import com.cardealership.util.DealershipList;

import java.util.Scanner;

public class CarMenuService {
    static User currentUser;
    static Scanner scan;
    CarService carService = new CarService();
    OfferService offerService = new OfferService();

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
        5) View my Remaining Payments
        6) Make a Payment
     */
    private void customerMenu() {
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t2) Make an Offer on a Car");
        System.out.println("\t\t\t3) View my Offers");
        System.out.println("\t\t\t4) View My Owned Cars");
        System.out.println("\t\t\t0) Log Out");
        switch(scan.nextLine()){
            case "1": printUnownedCars();
                break;
            case "2": addNewOffer();
                break;
            case "3": viewMyOffers();
                break;
            case "4": printMyCars();
                break;
            case "0": logOut();
                break;
        }
    }

    private void viewMyOffers() {
        DealershipList<Offer> offers = offerService.getMyOffers(currentUser.getId());
        if(offers.size() > 0) System.out.println(offers);
        else System.out.println("\t\tNo offers were found.");
    }

    private void addNewOffer() {
            long carId;

            System.out.println("\t\tYou'd like make an offer.");
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tCarId: ");
            carId = Long.parseLong(scan.nextLine());
            if(offerService.newOffer(carId, currentUser.getId()))
                System.out.println("\t\tOffer created successfully.");
    }

    //TODO: add following employee menu options
    /*
        4) View All Offers
        5) Accept or Reject an Offer
        6) View all Payments
     */

    private void employeeMenu() {
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t2) Add a New Car to the Lot");
        System.out.println("\t\t\t3) Remove a Car from the Lot");
        System.out.println("\t\t\t4) View All Offers");

        System.out.println("\t\t\t0) Log Out");
        switch(scan.nextLine()){
            case "1": printUnownedCars();
                break;
            case "2": addNewCar();
                break;
            case "3": removeCar();
                break;
            case "4": printAllOffers();
                break;
            case "0": logOut();
                break;
        }
    }

    private void printAllOffers() {
        DealershipList<Offer> offers = offerService.getAllOffers();
        if(offers.size() > 0) System.out.println(offers);
        else System.out.println("\t\tNo offers were found");
    }

    private void removeCar() {
        long carId;

        System.out.println("\t\tYou'd like to remove a car.");
        System.out.println("\t\tPlease enter the following:");
        System.out.print("\t\t\tCarId: ");
        carId = Long.parseLong(scan.nextLine());
        if(carService.deleteCar(carId))
            System.out.println("\t\tCar deleted successfully.");
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
        if(cars.size() > 0) System.out.println(cars);
        else System.out.println("\t\tNo cars were found");
    }
    private void printMyCars(){
        DealershipList<Car> cars = carService.getMyCars(currentUser.getId());

        if(cars.size() > 0) System.out.println(cars);
        else System.out.println("\t\tNo cars were found");
    }


    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        currentUser = new User(); // reset the user session
        new AuthMenuService().mainMenu(); // call the auth menu
    }
}
