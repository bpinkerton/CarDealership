package com.cardealership.model;

import com.enterprise.annotations.TestClass;
import com.enterprise.annotations.TestMethod;

public class Payment {
    private long id;
    private long financingAccountId;
    private double paymentAmount;

    public Payment(){};

    public Payment(long id, long financingAccountId, double paymentAmount) {
        this.id = id;
        this.financingAccountId = financingAccountId;
        this.paymentAmount = paymentAmount;
    }

    public Payment(long financingAccountId, double paymentAmount) {
        this.financingAccountId = financingAccountId;
        this.paymentAmount = paymentAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFinancingAccountId() {
        return financingAccountId;
    }

    public void setFinancingAccountId(long financingAccountId) {
        this.financingAccountId = financingAccountId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", financingAccountId=" + financingAccountId +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
