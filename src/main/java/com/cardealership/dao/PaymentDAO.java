package com.cardealership.dao;

import com.cardealership.model.Car;
import com.cardealership.model.Ownership;
import com.cardealership.model.Payment;
import com.cardealership.util.DealershipList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PaymentDAO implements Dao <Payment, Long> {
    @Override
    public Optional<Payment> getById(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM PAYMENTS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new Payment(
                        rs.getLong("id"),
                        rs.getLong("financing_account_id"),
                        rs.getDouble("payment_amount")
                ));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<DealershipList<Payment>> getAll() throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Payment> payments = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM PAYMENTS";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                payments.add(new Payment(
                        rs.getLong("id"),
                        rs.getLong("financing_account_id"),
                        rs.getDouble("payment_amount")
                ));
            }
            return Optional.of(payments);
        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }


    @Override
    public boolean create(Payment payment) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO PAYMENTS (" +
                    "financing_account_id," +
                    "payment_amount)" +
                    " VALUES (?,?)";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, payment.getFinancingAccountId());
            stmt.setDouble(2, payment.getPaymentAmount());

            success = stmt.executeUpdate();
            if(success != 0)
                return true;
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void update(Payment payment) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE PAYMENTS SET " +
                    "financing_account_id = ?" +
                    "payment_amount = ?" +
                    "WHERE id = ?";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, payment.getFinancingAccountId());
            stmt.setDouble(2, payment.getPaymentAmount());
            stmt.setLong(3,payment.getId());

            success = stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (success == 0) throw new Exception("Update payment failed.");
    }

    @Override
    public void remove(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM PAYMENTS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            success = stmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (success == 0) throw new Exception("Delete payment failed.");
    }
}
