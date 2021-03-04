package com.cardealership.dao;

import com.cardealership.model.Offer;
import com.cardealership.model.OfferStatus;
import com.cardealership.util.DealershipList;
import com.cardealership.util.OfferSearchCondition;
import com.cardealership.util.SearchQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OfferDao implements Dao<Offer,Long>{
    @Override
    public Optional<Offer> getById(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM OFFERS WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new Offer(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        OfferStatus.values()[rs.getInt("offer_Status_type_id")]
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
    public Optional<DealershipList<Offer>> getAll() throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Offer> offers = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM OFFERS";
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                 offers.add(new Offer(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        OfferStatus.values()[rs.getInt("offer_Status_type_id")]
                ));
            }
            return Optional.of(offers);
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

    public Optional<DealershipList<Offer>> getAll(DealershipList<SearchQuery<OfferSearchCondition>> conditions) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        DealershipList<Offer> offers = new DealershipList<>();

        try{
            connection = DAOUtilities.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM OFFERS WHERE ");
            int numberOfConditions = conditions.size();
            if(numberOfConditions > 0){
                int currentIndex;
                for(currentIndex = 0; currentIndex < numberOfConditions; currentIndex++){
                    switch(conditions.get(currentIndex).getSearchCondition()){
                        case CAR_ID:
                            sql.append("carid=?");
                            break;
                        case USER_ID:
                            sql.append("userid=?");
                            break;
                        case OFFER_STATUS:
                            sql.append("offer_Status_type_id=?");
                            break;
                    }
                    if(currentIndex < numberOfConditions - 1)
                        sql.append(" AND ");
                }
                stmt = connection.prepareStatement(String.valueOf(sql));

                for(currentIndex = 0; currentIndex < numberOfConditions; currentIndex++) {
                    switch (conditions.get(currentIndex).getSearchCondition()) {
                        case CAR_ID:
                        case USER_ID:
                            stmt.setLong(++currentIndex, Long.parseLong(conditions.get(currentIndex).getValue().toString()));
                            break;
                        case OFFER_STATUS:
                            stmt.setInt(++currentIndex, Integer.parseInt(conditions.get(currentIndex).getValue().toString()));
                            break;
                    }
                }
            } else return getAll();

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                offers.add(new Offer(
                        rs.getLong("id"),
                        rs.getLong("carid"),
                        rs.getLong("userid"),
                        OfferStatus.values()[rs.getInt("offer_Status_type_id")]
                ));
            }
            return Optional.of(offers);
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
    public boolean create(Offer offer) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO OFFERS (" +
                    "carid," +
                    "userid," +
                    "offer_status_type_id)" +
                    " VALUES (?,?,?)";
            stmt = connection.prepareStatement(sql);

            stmt.setLong(1,offer.getCarId());
            stmt.setLong(2,offer.getUserId());
            stmt.setInt(3,offer.getOfferStatus().ordinal());

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
    public void update(Offer offer) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "UPDATE OFFERS SET " +
                    "carid = ?," +
                    "userid = ?," +
                    "offer_status_type_id = ? " +
                    "WHERE id = ?";
            stmt = connection.prepareStatement(sql);

            stmt.setLong(1,offer.getCarId());
            stmt.setLong(2,offer.getUserId());
            stmt.setInt(3,offer.getOfferStatus().ordinal());
            stmt.setLong(4,offer.getId());

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
        if (success == 0) throw new Exception("Update offer failed: " + offer);
    }

    public void updateAll(DealershipList<Offer> offers) throws Exception {
        // TODO refactor exception handling to continue the update even if one errors out
        for(int i = 0; i < offers.size(); i++){
            update(offers.get(i));
        }
    }

    @Override
    public void remove(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try{
            connection = DAOUtilities.getConnection();
            String sql = "DELETE FROM OFFERS WHERE id=?";
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
        if (success == 0) throw new Exception("Delete offer failed.");
    }
}
