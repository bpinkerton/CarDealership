package com.cardealership.dao;

import com.cardealership.model.car.Car;
import com.cardealership.model.car.FinancingType;
import com.cardealership.model.car.Ownership;
import com.cardealership.util.CarSearchCondition;
import com.cardealership.util.CarSearchQuery;
import com.cardealership.util.DealershipList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class CarDao implements Dao<Car> {
    //TODO: implement UPDATE methods for multiple search terms (make,model,year,price,ownership)
    @Override
    public Optional<Car> get(long id){
        Connection connection = null;
        PreparedStatement stmt = null;

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

    public Optional<DealershipList<Car>> getAll(){
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Car> cars = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM CARS";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                cars.add(new Car(
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
            return Optional.of(cars);
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

    public Optional<DealershipList<Car>> getAll(CarSearchQuery conditions){
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Car> cars = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM CARS WHERE ");
            int numberOfConditions = conditions.size();
            if(numberOfConditions > 0){
                sql.append("(");
                int i = 0;
                for(CarSearchQuery.Entry<CarSearchCondition, Object> condition : conditions.entrySet()){
                    switch (condition.getKey()) {
                        case OWNERSHIP:
                            sql.append("ownership=?");
                            break;
                        case USER_ID:
                            sql.append("userid=?");
                            break;
                        case MAKE:
                            sql.append("make=?");
                            break;
                        case MODEL:
                            sql.append("model=?");
                            break;
                        case YEAR:
                            sql.append("year=?");
                            break;
                        case PRICE:
                            sql.append("price=?");
                    }
                    if(i < numberOfConditions - 1)
                        sql.append(" AND ");
                    i++;
                }
                sql.append(")");
                stmt = connection.prepareStatement(String.valueOf(sql));
                i = 0;
                for(Map.Entry<CarSearchCondition, Object> condition : conditions.entrySet()) {
                    switch (condition.getKey()) {
                        case OWNERSHIP:
                        case MAKE:
                        case MODEL:
                        case YEAR:
                            stmt.setString(++i, condition.getValue().toString());
                            break;
                        case USER_ID:
                            stmt.setLong(++i, Long.parseLong(condition.getValue().toString()));
                            break;
                        case PRICE:
                            stmt.setDouble(++i, Double.parseDouble(condition.getValue().toString()));
                    }
                }
            } else return getAll(); // call a no arg get if there are no search conditions

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                cars.add(new Car(
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
            return Optional.of(cars);
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
