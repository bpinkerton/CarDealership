package com.cardealership.dao;

import com.cardealership.model.Car;
import com.cardealership.model.FinancingType;
import com.cardealership.model.Ownership;
import com.cardealership.util.CarSearchCondition;
import com.cardealership.util.DealershipList;
import com.cardealership.util.SearchQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CarDao implements Dao<Car,Long> {
    @Override
    public Optional<Car> getById(Long id){
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
                        Ownership.values()[rs.getInt("ownershipid")],
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getDouble("price"),
                        rs.getDouble("balanceremaining"),
                        FinancingType.values()[rs.getInt("financingtypeid")]
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
                        Ownership.values()[rs.getInt("ownershipid")],
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getDouble("price"),
                        rs.getDouble("balanceremaining"),
                        FinancingType.values()[rs.getInt("financingtypeid")]
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

    public Optional<DealershipList<Car>> getAll(DealershipList<SearchQuery<CarSearchCondition>> conditions){
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Car> cars = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM CARS WHERE ");
            int numberOfConditions = conditions.size();
            if(numberOfConditions > 0){
                int currentIndex;
                for(currentIndex = 0; currentIndex < numberOfConditions; currentIndex++){
                    switch (conditions.get(currentIndex).getSearchCondition()) {
                        case OWNERSHIP:
                            sql.append("ownershipid=?");
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
                    if(currentIndex < numberOfConditions - 1)
                        sql.append(" AND ");
                }
                stmt = connection.prepareStatement(String.valueOf(sql));
                currentIndex = 0;
                for(currentIndex = 0; currentIndex < numberOfConditions; currentIndex++) {
                    switch (conditions.get(currentIndex).getSearchCondition()) {
                        case OWNERSHIP: stmt.setInt(++currentIndex, Integer.parseInt(conditions.get(currentIndex).getValue().toString()));
                            break;
                        case MAKE:
                        case MODEL:
                        case YEAR:
                            stmt.setString(++currentIndex, conditions.get(currentIndex).getValue().toString());
                            break;
                        case USER_ID:
                            stmt.setLong(++currentIndex, Long.parseLong(conditions.get(currentIndex).getValue().toString()));
                            break;
                        case PRICE:
                            stmt.setDouble(++currentIndex, Double.parseDouble(conditions.get(currentIndex).getValue().toString()));
                    }
                }
            } else return getAll(); // call a no arg get if there are no search conditions

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                cars.add(new Car(
                        rs.getLong("id"),
                        rs.getLong("userid"),
                        Ownership.values()[rs.getInt("ownershipid")],
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getDouble("price"),
                        rs.getDouble("balanceremaining"),
                        FinancingType.values()[rs.getInt("financingtypeid")]
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

    public Optional<DealershipList<Car>> getAllUnowned(){
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Car> cars = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM CARS WHERE \"ownershipid\" != 1";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                cars.add(new Car(
                        rs.getLong("id"),
                        rs.getLong("userid"),
                        Ownership.values()[rs.getInt("ownershipid")],
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getString("year"),
                        rs.getDouble("price"),
                        rs.getDouble("balanceremaining"),
                        FinancingType.values()[rs.getInt("financingtypeid")]
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
    public boolean create(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO CARS (" +
                    "userid," +
                    "ownershipid," +
                    "make," +
                    "model," +
                    "year," +
                    "price," +
                    "balanceremaining," +
                    "financingtypeid)" +
                    " VALUES (?,?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, car.getUserId());
            stmt.setInt(2, car.getOwnership().ordinal());
            stmt.setString(3, car.getMake());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getYear());
            stmt.setDouble(6, car.getPrice());
            stmt.setDouble(7, car.getBalanceRemaining());
            stmt.setInt(8, car.getFinancingType().ordinal());

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
    public void update(Car car) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE CARS SET " +
                    "userid = ?," +
                    "ownershipid = ?," +
                    "make = ?," +
                    "model = ?," +
                    "year = ?," +
                    "price = ?," +
                    "balanceremaining = ?," +
                    "financingtypeid = ? " +
                    "WHERE id= ?";

            stmt = connection.prepareStatement(sql);

            stmt.setLong(1, car.getUserId());
            stmt.setInt(2, car.getOwnership().ordinal());
            stmt.setString(3, car.getMake());
            stmt.setString(4, car.getModel());
            stmt.setString(5, car.getYear());
            stmt.setDouble(6, car.getPrice());
            stmt.setDouble(7, car.getBalanceRemaining());
            stmt.setInt(8, car.getFinancingType().ordinal());
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
    public void remove(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM CARS WHERE id=?";
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
        if (success == 0) throw new Exception("Delete car failed.");
    }
}
