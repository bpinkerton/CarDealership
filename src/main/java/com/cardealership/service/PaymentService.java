package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.PaymentDAO;
import com.cardealership.model.Payment;

public class PaymentService {
    private PaymentDAO paymentDAO = DAOUtilities.getPaymentDAO();

    public boolean newPayment(long financingAccountId, double paymentAmount){
        return create(new Payment(financingAccountId, paymentAmount));
    }

    private boolean create(Payment payment){
        try{
            paymentDAO.create(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
