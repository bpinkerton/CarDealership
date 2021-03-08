package com.cardealership.service;

import com.cardealership.model.*;
import com.cardealership.util.DealershipList;

import java.util.Scanner;

public class CarMenuService {
    static User currentUser;
    static Scanner scan;
    CarService carService = new CarService();
    OfferService offerService = new OfferService();
    FinancingService financingService = new FinancingService();

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
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t2) Make an Offer on a Car");
        System.out.println("\t\t\t3) View my Offers");
        System.out.println("\t\t\t4) View My Owned Cars");
        System.out.println("\t\t\t5) Create a Financing Account");
        System.out.println("\t\t\t6) View Remaining Payments for a Car");
        System.out.println("\t\t\t7) Make a Car Payment");
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
            case "5": financeAccountPrompt();
                break;
            case "6": printCarFinancingReport();
                break;
            case "7": makeAPayment();
                break;
            case "0": logOut();
                break;
        }
    }

    private void makeAPayment() {
        long carId;

        System.out.println("\t\tPlease enter the following:");
        System.out.print("\t\t\tCarId: ");
        carId = Long.parseLong(scan.nextLine());
        if(financingService.makeAPayment(carId,currentUser.getId())){
            System.out.println("\t\tPayment made successfully!");
            System.out.println(financingService.getCarFinanceReport(carId));
        }
    }

    private void printCarFinancingReport() {
        long carId;
        FinanceAccount report;

        System.out.println("\t\tPlease enter the following:");
        System.out.print("\t\t\tCarId: ");
        carId = Long.parseLong(scan.nextLine());
        if((report = financingService.getCarFinanceReport(carId)) != null)
            System.out.println(report);
        else
            System.out.println("\t\tCould not find the Finance Account for that Car.\n" +
                    "\t\tPlease create a financing account and try again.\n");
    }

    private void financeAccountPrompt() {
        long offerId;
        Offer offer;

        System.out.println("\t\tYou'd like to create a financing account to make payments on a car.");
        System.out.println("\t\tKeep in mind this requires an accepted offer to proceed.");
        System.out.println("\t\tWould you like to proceed? (y/n)");
        if(scan.nextLine().equalsIgnoreCase("y")){
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tOfferId: ");
            offerId = Long.parseLong(scan.nextLine());
            if((offer = offerService.getOffer(offerId)) != null){
                if(offer.getUserId() == currentUser.getId()) {
                    if (offer.getOfferStatus().equals(OfferStatus.ACCEPTED)) {
                        if (!financingService.hasFinanceAccount(offer)) {
                            createFinancingAccount(offer);
                        } else {
                            System.out.println("\t\tThis car already has an associated financing account.");
                        }
                    } else {
                        System.out.println("\t\tThis offer has not been accepted. Please check the status.");
                    }
                } else {
                    System.out.println("\t\tYou are not the owner of this offer.");
                }
            } else {
                System.out.println("\t\tCouldn't find the offer, please try again.");
            }

        }
    }

    private void createFinancingAccount(Offer offer){
        int financingTypeId;
        boolean valid = false;
        do {
            System.out.println("\t\tPlease select from the following Financing Types: ");
            System.out.println("\t\t\t0) None - Full Payment Upfront ");
            System.out.println("\t\t\t1) One Year - Payment will be split across 12 monthly payments ");
            System.out.println("\t\t\t2) Two Year - Payment will be split across 24 monthly payments ");
            System.out.println("\t\t\t3) Three Year - Payment will be split across 36 monthly payments ");
            System.out.print("\t\t\tPlease enter your selection (0/1/2/3): ");
            financingTypeId = Integer.parseInt(scan.nextLine());
            switch(financingTypeId){
                case 0:
                case 1:
                case 2:
                case 3:
                    valid = true;
                    break;
                default:
                    System.out.println("\t\tPlease enter a valid entry.");
                    break;
            }
        } while (!valid);
        if(financingService.newFinanceAccount(offer.getCarId(), offer.getUserId(), financingTypeId))
            System.out.println("\t\tFinancing account created successfully.");
    }

    private void viewMyOffers() {
        DealershipList<Offer> offers = offerService.getMyOffers(currentUser.getId());
        if(offers.size() > 0) System.out.println(offers);
        else System.out.println("\t\tNo offers were found.");
    }

    private void addNewOffer() {
            long carId;

            System.out.println("\t\tYou'd like to make an offer.");
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tCarId: ");
            carId = Long.parseLong(scan.nextLine());
            if(offerService.newOffer(carId, currentUser.getId()))
                System.out.println("\t\tOffer created successfully.");
    }

    private void employeeMenu() {
        System.out.println("\t\t\t1) View Cars on the Lot");
        System.out.println("\t\t\t2) Add a New Car to the Lot");
        System.out.println("\t\t\t3) Remove a Car from the Lot");
        System.out.println("\t\t\t4) View All Offers"); //TODO: view all open offers instead?
        System.out.println("\t\t\t5) Accept an Offer");
        System.out.println("\t\t\t6) View All Remaining Car Payments");
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
            case "5": acceptOffer();
                break;
            case "6": printAllFinancingAccounts();
                break;
            case "0": logOut();
                break;
        }
    }

    private void printAllFinancingAccounts() {
        DealershipList<FinanceAccount> accounts = financingService.getAllOpenFinanceAccounts();
        if(accounts.size() > 0) System.out.println(accounts);
        else System.out.println("\t\tNo offers were found");
    }

    private void acceptOffer() {
        long offerId;

        System.out.println("\t\tYou'd like to accept an Offer.");
        System.out.println("\t\tThis will REJECT all other Offers for this car.");
        System.out.println("\t\tWould you like to proceed? (y/n)");
        if(scan.nextLine().equalsIgnoreCase("y")){
            System.out.println("\t\tPlease enter the following:");
            System.out.print("\t\t\tOfferId: ");
            offerId = Long.parseLong(scan.nextLine());
            if(offerService.acceptOffer(offerId))
                System.out.println("\t\tOffer accepted successfully.");
        }
    }

    private void printAllOffers() {
        DealershipList<Offer> offers = offerService.getOpenOffers();
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
        DealershipList<OwnedCar> cars = carService.getMyCars(currentUser.getId());

        if(cars.size() > 0) System.out.println(cars);
        else System.out.println("\t\tNo cars were found");
    }


    private void logOut(){
        System.out.println("------           Goodbye!           ------");
        currentUser = new User(); // reset the user session
        new AuthMenuService().mainMenu(); // call the auth menu
    }
}
