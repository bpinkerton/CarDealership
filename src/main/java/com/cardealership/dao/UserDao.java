package com.cardealership.dao;

import com.cardealership.model.AccountType;
import com.cardealership.model.User;
import com.cardealership.util.DealershipList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements Dao<User,Long>{

    @Override
    public Optional<User> getById(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM USERS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new User(
                        rs.getLong("id"),
                        AccountType.values()[rs.getInt("accounttypeid")],
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password")
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
    public Optional<DealershipList<User>> getAll() {
        return Optional.empty();
    }

    public Optional<User> get(String email) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM USERS WHERE email=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new User(
                        rs.getLong("id"),
                        AccountType.values()[rs.getInt("accounttypeid")],
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password")
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
    public boolean create(User user) throws Exception{
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO USERS (" +
                    "accounttypeid," +
                    "firstname," +
                    "lastname," +
                    "email," +
                    "password)" +
                    " VALUES (?,?,?,?,?)";
            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, user.getAccountType().ordinal());
            stmt.setString(2,user.getFirstName());
            stmt.setString(3,user.getLastName());
            stmt.setString(4,user.getEmail());
            stmt.setString(5,user.getPassword()); //TODO: password encryption

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
    public void update(User user) throws Exception{
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE USERS SET " +
                    "accounttypeid = ?," +
                    "firstname = ?," +
                    "lastname = ?," +
                    "email = ?," +
                    "password = ? " +
                    "WHERE id= ?";

            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, user.getAccountType().ordinal());
            stmt.setString(2,user.getFirstName());
            stmt.setString(3,user.getLastName());
            stmt.setString(4,user.getEmail());
            stmt.setString(5,user.getPassword()); //TODO: password encryption
            stmt.setLong(6,user.getId());

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
        if (success == 0) throw new Exception("Update user failed.");
    }

    @Override
    public void remove(Long id) throws Exception{
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM USERS WHERE id=?";
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
        if (success == 0) throw new Exception("Delete user failed.");
    }
}
