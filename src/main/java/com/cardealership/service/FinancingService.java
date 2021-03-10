package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.FinanceAccountDAO;
import com.cardealership.model.FinanceAccount;
import com.cardealership.model.FinancingType;
import com.cardealership.model.Offer;
import com.cardealership.util.DealershipList;

import java.util.Optional;

public class FinancingService {
    FinanceAccountDAO financeAccountDAO = DAOUtilities.getFinanceAccountDAO();
    CarService carService = new CarService();
    PaymentService paymentService = new PaymentService();

    public boolean newFinanceAccount(long carId, long userId, int financingTypeId){
        double priceOfCar = carService.getPriceById(carId);
        int totalPayments;
        if(financingTypeId != 0)
            totalPayments = financingTypeId * 12;
        else
            totalPayments = 1;
        double paymentAmount = priceOfCar / totalPayments; // get the amount of each monthly payment
        return create(new FinanceAccount(carId,userId,FinancingType.values()[financingTypeId],
                priceOfCar,totalPayments,paymentAmount));
    }

    public DealershipList<FinanceAccount> getMyFinanceAccounts(long userId){
        Optional<DealershipList<FinanceAccount>> result = getAllByUserId(userId);
        return result.orElse(null);
    }

    public FinanceAccount getCarFinanceReport(long carId){
        Optional<FinanceAccount> result = getByCarId(carId);
        return result.orElse(null);
    }

    public FinanceAccount getMyCarFinanceReport(long carId, long userId){
        Optional<FinanceAccount> result = getByCarId(carId);
        if(result.isPresent()){
            if(result.get().getUserId() == userId){
                return result.get();
            } else System.out.println("\t\tThis is not your car.");
        } else System.out.println("\t\tThis car does not have a financing account set up.");
        return null;
    }

    public boolean makeAPayment(long carId, long userId){

        FinanceAccount account = getCarFinanceReport(carId);
        if(account != null){
            // first, check to see if the current user matches
            if(account.getUserId() == userId) {
                // second we need to check if there are any remaining payments.
                if ((account.getTotalPayments() - account.getPaymentsMade()) != 0) {
                    if (paymentService.newPayment(account.getId(), account.getPaymentAmount())) {
                        // the payment was posted, we now need to update the account to reflect the changes.
                        // update the current balance by subtracting the payment
                        account.setCurrentBalance(account.getCurrentBalance() - account.getPaymentAmount());
                        // increment the number of payments made
                        account.setPaymentsMade(account.getPaymentsMade() + 1);
                        update(account);
                        return true;
                    } else {
                        System.out.println("\t\tThere was an issue making the payment");
                    }
                } else {
                    System.out.println("\t\tThere are no remaining payments for this account.");
                }
            } else {
                System.out.println("\t\t You are not the owner of this car.");
            }
        } else {
            System.out.println("\t\tCould not find the Finance Account for that car.\n" +
                    "\t\tPlease create a financing account and try again.");
        }
        return false;
    }

    public DealershipList<FinanceAccount> getAllOpenFinanceAccounts(){
        Optional<DealershipList<FinanceAccount>> result = getAllWithBalance();
        return result.orElse(null);
    }

    public boolean hasFinanceAccount(Offer offer){
        try{
            Optional<FinanceAccount> result = financeAccountDAO.getByCarId(offer.getCarId());
            return result.isPresent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean create(FinanceAccount financeAccount) {
        try{
            financeAccountDAO.create(financeAccount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Optional<FinanceAccount> getByCarId(long carId){
        try{
            Optional<FinanceAccount> result = financeAccountDAO.getByCarId(carId);
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<FinanceAccount>> getAllByUserId(long userId){
        try{
            Optional<DealershipList<FinanceAccount>> result = financeAccountDAO.getAllByUserId(userId);
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<FinanceAccount>> getAll(){
        try{
            Optional<DealershipList<FinanceAccount>> result = financeAccountDAO.getAll();
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<FinanceAccount>> getAllWithBalance(){
        try{
            Optional<DealershipList<FinanceAccount>> result = financeAccountDAO.getAllWithBalance();
            if(result.isPresent()) return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void update(FinanceAccount account){
        try {
            financeAccountDAO.update(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
