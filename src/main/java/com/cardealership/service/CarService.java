package com.cardealership.service;

import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.model.car.Car;

public class CarService {
    private CarDao carDao = DAOUtilities.getCarDao();

    public boolean newCar(String make, String model, String year, double price){
        return addCar(new Car(make,model,year,price));
    }

    private boolean addCar(Car car){
        try{
            carDao.create(car);
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void updateCar(Car car){
        try{
            carDao.update(car);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
