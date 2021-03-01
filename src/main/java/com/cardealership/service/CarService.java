package com.cardealership.service;

import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.model.car.Car;
import com.cardealership.model.car.Ownership;
import com.cardealership.util.CarSearchCondition;
import com.cardealership.util.CarSearchQuery;
import com.cardealership.util.DealershipList;

import java.util.Optional;

public class CarService {
    private CarDao carDao = DAOUtilities.getCarDao();

    public boolean newCar(String make, String model, String year, double price){
        return addCar(new Car(make,model,year,price));
    }

    public DealershipList<Car> getAllCars(){
        Optional<DealershipList<Car>> result = getAll();
        return result.orElse(null);
    }

    public DealershipList<Car> getUnownedCars(){
        CarSearchQuery query = new CarSearchQuery();
        query.addCondition(CarSearchCondition.OWNERSHIP, Ownership.UNOWNED);
        Optional<DealershipList<Car>> result = getAll(query);
        return result.orElse(null);
    }

    public DealershipList<Car> getMyCars(long userId){
        CarSearchQuery query = new CarSearchQuery();
        query.addCondition(CarSearchCondition.OWNERSHIP, Ownership.OWNED);
        query.addCondition(CarSearchCondition.USER_ID, userId);
        Optional<DealershipList<Car>> result = getAll(query);
        return result.orElse(null);
    }

    private Optional<DealershipList<Car>> getAll(){
        try{
            Optional<DealershipList<Car>> result = carDao.getAll();
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
    private Optional<DealershipList<Car>> getAll(CarSearchQuery query){
        try{
            Optional<DealershipList<Car>> result = carDao.getAll(query);
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
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

    private void updateCar(Car car){
        try{
            carDao.update(car);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
