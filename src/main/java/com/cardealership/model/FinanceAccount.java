package com.cardealership.model;

public class FinanceAccount {
    private long id;
    private long carId;
    private long userId;
    private FinancingType financingType;
    private double startingBalance;
    private double currentBalance;
    private int totalPayments;
    private int paymentsMade;
    private double paymentAmount;

    public FinanceAccount(long id, long carId, long userId, FinancingType financingType,
                          double startingBalance, double currentBalance, int totalPayments,
                          int paymentsMade, double paymentAmount) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.financingType = financingType;
        this.startingBalance = startingBalance;
        this.currentBalance = currentBalance;
        this.totalPayments = totalPayments;
        this.paymentsMade = paymentsMade;
        this.paymentAmount = paymentAmount;
    }

    public FinanceAccount(long carId, long userId, FinancingType financingType,
                          double startingBalance, double currentBalance, int totalPayments,
                          int paymentsMade, double paymentAmount) {
        this.carId = carId;
        this.userId = userId;
        this.financingType = financingType;
        this.startingBalance = startingBalance;
        this.currentBalance = currentBalance;
        this.totalPayments = totalPayments;
        this.paymentsMade = paymentsMade;
        this.paymentAmount = paymentAmount;
    }

    public FinanceAccount(long carId, long userId, FinancingType financingType, double priceOfCar,
                          int totalPayments, double paymentAmount) {
        this.carId = carId;
        this.userId = userId;
        this.financingType = financingType;
        this.startingBalance = priceOfCar;
        this.currentBalance = priceOfCar;
        this.totalPayments = totalPayments;
        this.paymentAmount = paymentAmount;
        this.paymentsMade = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public FinancingType getFinancingType() {
        return financingType;
    }

    public void setFinancingType(FinancingType financingType) {
        this.financingType = financingType;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(int totalPayments) {
        this.totalPayments = totalPayments;
    }

    public int getPaymentsMade() {
        return paymentsMade;
    }

    public void setPaymentsMade(int paymentsMade) {
        this.paymentsMade = paymentsMade;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return String.format(
                "id= '%4d'\t" +
                "carId= '%4d'\t" +
                "userId= '%4d'\t" +
                "financingType= '%10s'  \t" +
                "startingBalance= $%10.2f  \t" +
                "currentBalance= $%10.2f  \t" +
                "totalPayments= '%d'\t" +
                "paymentsMade= '%d'\t" +
                "paymentAmount= $%10.2f"
                ,id,carId,userId,financingType,startingBalance,currentBalance,totalPayments,paymentsMade,paymentAmount
        );
    }
}
