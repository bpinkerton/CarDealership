package com.cardealership.service;

import com.cardealership.dao.CarDao;
import com.cardealership.dao.DAOUtilities;
import com.cardealership.model.Car;
import com.cardealership.model.OwnedCar;
import com.cardealership.model.Ownership;
import com.cardealership.util.CarSearchCondition;
import com.cardealership.util.DealershipList;
import com.cardealership.util.SearchQuery;
import com.enterprise.annotations.TestClass;
import com.enterprise.annotations.TestMethod;

import java.util.Optional;
import java.util.Stack;

public class CarService {
    private CarDao carDao = DAOUtilities.getCarDao();


    // public accessors
    public boolean newCar(String make, String model, String year, double price){
        return create(new Car(make,model,year,price));
    }

    public Car getCar(long carId){
        Optional<Car> result = get(carId);
        return result.orElse(null);
    }

    public double getPriceById(long carId){
        Optional<Car> result = get(carId);
        return result.map(Car::getPrice).orElse(-1.0);
    }

    public DealershipList<Car> getAllCars(){
        Optional<DealershipList<Car>> result = getAll();
        return result.orElse(null);
    }

    public DealershipList<Car> getUnownedCars(){
        Optional<DealershipList<Car>> result = getAllUnowned();
        return result.orElse(null);
    }

    public DealershipList<OwnedCar> getMyCars(long userId){

        Optional<DealershipList<OwnedCar>> result = getAllByOwnerId(userId);
        return result.orElse(null);
    }

    public void updateCar(Car car){
        Optional<Car> result = get(car.getId());
        result.ifPresent(value -> update(car));
    }

    public boolean deleteCar(Long carId){
        try{
            Optional<Car> result = get(carId);
            if(result.isPresent()){         // make sure that the car exists.
                Car car = result.get();
                if(car.getOwnership().equals(Ownership.UNOWNED)){ // only allow deletion if the car is unowned
                    return delete(car);
                }else{
                    System.out.printf("\t\tThe car is %s and can't be deleted.\n", car.getOwnership());
                    return false;
                }
            } else{
                System.out.println("\t\tCould not find the car.");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // private accessors for accessing the DAO
    private boolean create(Car car){
        try{
            carDao.create(car);
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private Optional<Car> get(long carId){
        try{
            Optional<Car> result = carDao.getById(carId);
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
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

    private Optional<DealershipList<OwnedCar>> getAllByOwnerId(long ownerId){
        try{
            Optional<DealershipList<OwnedCar>> result = carDao.getAllByOwnerId(ownerId);
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<Car>> getAll(DealershipList<SearchQuery<CarSearchCondition>> query){
        try{
            Optional<DealershipList<Car>> result = carDao.getAll(query);
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<Car>> getAllUnowned() {
        try{
            Optional<DealershipList<Car>> result = carDao.getAllUnowned();
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void update(Car car){
        try{
            carDao.update(car);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean delete(Car car){
        try{
            carDao.remove(car.getId());
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
