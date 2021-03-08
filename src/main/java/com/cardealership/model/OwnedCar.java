package com.cardealership.model;

public class OwnedCar extends Car{
    private FinanceAccount financeAccount;


    public OwnedCar(long id, long userId, Ownership ownership, String make, String model,
                    String year, double price, FinanceAccount financeAccount) {
        super(id, userId, ownership, make, model, year, price);
        this.financeAccount = financeAccount;
    }

    public FinanceAccount getFinanceAccount() {
        return financeAccount;
    }

    public void setFinanceAccount(FinanceAccount financeAccount) {
        this.financeAccount = financeAccount;
    }

    @Override
    public String toString() {
        return String.format("" +
                        "id= '%4d'\t" +
                        "ownership = '%s'  \t" +
                        "make= '%s'\t" +
                        "model= '%s'\t" +
                        "year= '%s'\t" +
                        "price= $%10.2f  \t" +
                        "financingType= '%10s' \t" +
                        "currentBalance= $%10.2f  \t" +
                        "totalPayments= '%d'  \t" +
                        "paymentsMade= '%d'  \t" +
                        "paymentAmount= $%10.2f"
                , super.getId(), super.getOwnership(), super.getMake(), super.getModel()
                , super.getYear(), super.getPrice(), financeAccount.getFinancingType()
                , financeAccount.getCurrentBalance(), financeAccount.getTotalPayments()
                , financeAccount.getPaymentsMade(), financeAccount.getPaymentAmount()
        );
    }


}
