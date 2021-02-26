package com.cardealership.dao;

import com.cardealership.model.car.Car;
import com.cardealership.model.car.FinancingType;
import com.cardealership.model.car.Ownership;
import com.cardealership.model.user.AccountType;
import com.cardealership.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CarDao implements Dao<Car> {
    //TODO: implement GET ALL methods for multiple search terms (make,model,year,price,ownership)
    @Override
    public Optional<Car> get(long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM CARS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new Car(
                        rs.getLong("id"),
                        rs.getLong("userid"),
                        Ownership.valueOf(rs.getString("ownership")),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getDouble("price"),
                        rs.getDouble("balanceremaining"),
                        FinancingType.valueOf(rs.getString("financingtype"))
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
    public void create(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO CARS (" +
                    "userid," +
                    "ownership," +
                    "make," +
                    "model," +
                    "year," +
                    "price," +
                    "balanceremaining," +
                    "financingtype)" +
                    " VALUES (?,?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, car.getUserId());
            stmt.setString(2, car.getOwnership().toString());
            stmt.setString(3, car.getMake());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getYear());
            stmt.setDouble(6, car.getPrice());
            stmt.setDouble(7, car.getBalanceRemaining());
            stmt.setString(8, car.getFinancingType().toString());

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
        if (success == 0) throw new Exception("Save car failed.");
    }

    @Override
    public void update(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE CARS SET " +
                    "userid = ?," +
                    "ownership = ?," +
                    "make = ?," +
                    "model = ?," +
                    "year = ?," +
                    "price = ?," +
                    "balanceremaining = ?," +
                    "financingtype = ? " +
                    "WHERE id= ?";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, car.getUserId());
            stmt.setString(2, car.getOwnership().toString());
            stmt.setString(3, car.getMake());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getYear());
            stmt.setDouble(6, car.getPrice());
            stmt.setDouble(7, car.getBalanceRemaining());
            stmt.setString(8, car.getFinancingType().toString());
            stmt.setLong(9, car.getId());

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
    public void delete(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM CARS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,car.getId());
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
        if (success == 0) throw new Exception("Delete car failed.");
    }
}
