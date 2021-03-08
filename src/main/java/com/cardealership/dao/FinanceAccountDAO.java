package com.cardealership.dao;

import com.cardealership.model.FinanceAccount;
import com.cardealership.model.FinancingType;
import com.cardealership.model.Offer;
import com.cardealership.model.OfferStatus;
import com.cardealership.util.DealershipList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FinanceAccountDAO implements Dao <FinanceAccount, Long> {
    @Override
    public Optional<FinanceAccount> getById(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM FINANCE_ACCOUNTS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new FinanceAccount(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        FinancingType.values()[rs.getInt("financing_type_id")],
                        rs.getDouble("starting_balance"),
                        rs.getDouble("current_balance"),
                        rs.getInt("total_payments"),
                        rs.getInt("payments_made"),
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

    public Optional<FinanceAccount> getByCarId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM FINANCE_ACCOUNTS WHERE carid=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new FinanceAccount(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        FinancingType.values()[rs.getInt("financing_type_id")],
                        rs.getDouble("starting_balance"),
                        rs.getDouble("current_balance"),
                        rs.getInt("total_payments"),
                        rs.getInt("payments_made"),
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
    public Optional<DealershipList<FinanceAccount>> getAll() throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<FinanceAccount> accounts = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM FINANCE_ACCOUNTS";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                accounts.add(new FinanceAccount(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        FinancingType.values()[rs.getInt("financing_type_id")],
                        rs.getDouble("starting_balance"),
                        rs.getDouble("current_balance"),
                        rs.getInt("total_payments"),
                        rs.getInt("payments_made"),
                        rs.getDouble("payment_amount")
                ));
            }
            return Optional.of(accounts);
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

    public Optional<DealershipList<FinanceAccount>> getAllByUserId(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<FinanceAccount> accounts = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM FINANCE_ACCOUNTS WHERE USERID=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                accounts.add(new FinanceAccount(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        FinancingType.values()[rs.getInt("financing_type_id")],
                        rs.getDouble("starting_balance"),
                        rs.getDouble("current_balance"),
                        rs.getInt("total_payments"),
                        rs.getInt("payments_made"),
                        rs.getDouble("payment_amount")
                ));
            }
            return Optional.of(accounts);
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

    public Optional<DealershipList<FinanceAccount>> getAllWithBalance() throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<FinanceAccount> accounts = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM FINANCE_ACCOUNTS WHERE CURRENT_BALANCE>?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,0);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                accounts.add(new FinanceAccount(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        FinancingType.values()[rs.getInt("financing_type_id")],
                        rs.getDouble("starting_balance"),
                        rs.getDouble("current_balance"),
                        rs.getInt("total_payments"),
                        rs.getInt("payments_made"),
                        rs.getDouble("payment_amount")
                ));
            }
            return Optional.of(accounts);
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
    public boolean create(FinanceAccount financeAccount) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO FINANCE_ACCOUNTS (" +
                    "carid," +
                    "userid," +
                    "financing_type_id," +
                    "starting_balance," +
                    "current_balance," +
                    "total_payments," +
                    "payments_made," +
                    "payment_amount)" +
                    " VALUES (?,?,?,?,?,?,?,?)";
            stmt = connection.prepareStatement(sql);

            stmt.setLong(1,financeAccount.getCarId());
            stmt.setLong(2,financeAccount.getUserId());
            stmt.setLong(3,financeAccount.getFinancingType().ordinal());
            stmt.setDouble(4,financeAccount.getStartingBalance());
            stmt.setDouble(5,financeAccount.getCurrentBalance());
            stmt.setInt(6,financeAccount.getTotalPayments());
            stmt.setInt(7,financeAccount.getPaymentsMade());
            stmt.setDouble(8,financeAccount.getPaymentAmount());
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
    public void update(FinanceAccount financeAccount) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE FINANCE_ACCOUNTS SET " +
                    "carid = ?," +
                    "userid = ?," +
                    "financing_type_id = ?," +
                    "starting_balance = ?," +
                    "current_balance = ?," +
                    "total_payments = ?," +
                    "payments_made = ?," +
                    "payment_amount = ?" +
                    "WHERE id = ?";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, financeAccount.getCarId());
            stmt.setLong(2, financeAccount.getUserId());
            stmt.setLong(3, financeAccount.getFinancingType().ordinal());
            stmt.setDouble(4, financeAccount.getStartingBalance());
            stmt.setDouble(5, financeAccount.getCurrentBalance());
            stmt.setInt(6,financeAccount.getTotalPayments());
            stmt.setInt(7,financeAccount.getPaymentsMade());
            stmt.setDouble(8, financeAccount.getPaymentAmount());
            stmt.setLong(9, financeAccount.getId());

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
        if (success == 0) throw new Exception("Update car failed.");
    }

    @Override
    public void remove(Long id) throws Exception {

    }
}
