package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.OfferDao;
import com.cardealership.model.Car;
import com.cardealership.model.Offer;
import com.cardealership.model.Ownership;
import com.cardealership.util.DealershipList;
import com.cardealership.util.OfferSearchCondition;
import com.cardealership.util.SearchQuery;

import java.util.Optional;

public class OfferService {
    private OfferDao offerDao = DAOUtilities.getOfferDao();
    private CarService carService = new CarService();

    public boolean newOffer(long carId, long userId){
        return addOffer(new Offer(carId, userId));
    }

    private boolean addOffer(Offer offer) {
        try{
            Optional<Car> result = carService.getCar(offer.getCarId());
            if(result.isPresent()){     // Check to make sure the car exists
                Car car = result.get();
                if(!car.getOwnership().equals(Ownership.OWNED)){    // Only move forward if the car is not owned
                    if(offerDao.create(offer)){     // if we successfully created the offer
                        car.setOwnership(Ownership.PENDING);
                        carService.updateCar(car);  // update the car's ownership on the db
                        return true;
                    }
                }
                else
                    System.out.println("\t\tThe car is already owned.");
            }
            else
                System.out.println("\t\tCouldn't find the car. Please check the id.");
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public DealershipList<Offer> getAllOffers(){
        Optional<DealershipList<Offer>> result = getAll();
        return result.orElse(null);
    }

    public DealershipList<Offer> getMyOffers(Long userId){
        DealershipList<SearchQuery<OfferSearchCondition>> query = new DealershipList<>();
        query.add(new SearchQuery<>(OfferSearchCondition.USER_ID, userId));
        Optional<DealershipList<Offer>> result = getUsersOffers(query);
        return result.orElse(null);
    }

    private Optional<DealershipList<Offer>> getAll() {
        try{
            Optional<DealershipList<Offer>> result = offerDao.getAll();
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<DealershipList<Offer>> getUsersOffers(DealershipList<SearchQuery<OfferSearchCondition>> query) {
        try{
            Optional<DealershipList<Offer>> result = offerDao.getAll(query);
            if(result.isPresent()) return result;
        } catch(Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
