package com.cardealership.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtilities {
    //TODO: extract connection information into secure location
    private static final String CONNECTION_USERNAME = "postgres";
    private static final String CONNECTION_PASSWORD = "1324Pass";
    private static final String URL = "jdbc:postgresql://localhost:5432/CarDealership";

    private static Connection connection;
    private static UserDao userDao;
    private static CarDao carDao;
    private static OfferDao offerDao;
    private static FinanceAccountDAO financeAccountDAO;
    private static PaymentDAO paymentDAO;

    public static UserDao getUserDao(){
        if(userDao == null) userDao = new UserDao();
        return userDao;
    }

    public static CarDao getCarDao(){
        if(carDao == null) carDao = new CarDao();
        return carDao;
    }

    public static OfferDao getOfferDao(){
        if(offerDao == null) offerDao = new OfferDao();
        return offerDao;
    }

    public static FinanceAccountDAO getFinanceAccountDAO(){
        if(financeAccountDAO == null) financeAccountDAO = new FinanceAccountDAO();
        return financeAccountDAO;
    }

    public static PaymentDAO getPaymentDAO(){
        if(paymentDAO == null) paymentDAO = new PaymentDAO();
        return paymentDAO;
    }

    static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
        }

        //If connection was closed then retrieve a new connection
        if (connection.isClosed()){
            connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
        }
        return connection;
    }

}
