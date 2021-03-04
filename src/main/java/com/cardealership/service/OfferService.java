package com.cardealership.service;

import com.cardealership.dao.DAOUtilities;
import com.cardealership.dao.OfferDao;
import com.cardealership.model.Car;
import com.cardealership.model.Offer;
import com.cardealership.model.OfferStatus;
import com.cardealership.model.Ownership;
import com.cardealership.util.DealershipList;
import com.cardealership.util.OfferSearchCondition;
import com.cardealership.util.SearchQuery;

import java.util.Optional;

public class OfferService {
    private OfferDao offerDao = DAOUtilities.getOfferDao();
    private CarService carService = new CarService();

    // public accessors
    public boolean newOffer(long carId, long userId){
        return create(new Offer(carId, userId));
    }

    public Offer getOffer(Long offerId){
        return getById(offerId);
    }

    public DealershipList<Offer> getOpenOffers(){
        DealershipList<SearchQuery<OfferSearchCondition>> query = new DealershipList<>();
        query.add(new SearchQuery<>(OfferSearchCondition.OFFER_STATUS, OfferStatus.OPEN));
        Optional<DealershipList<Offer>> result = getAll();
        return result.orElse(null);
    }

    public DealershipList<Offer> getMyOffers(Long userId){
        DealershipList<SearchQuery<OfferSearchCondition>> query = new DealershipList<>();
        query.add(new SearchQuery<>(OfferSearchCondition.USER_ID, userId));
        Optional<DealershipList<Offer>> result = getUsersOffers(query);
        return result.orElse(null);
    }

    public boolean acceptOffer(Long offerId){
        Offer offer = getById(offerId);
        if(offer == null) return false;
        return accept(offer);
    }

    // private accessors for accessing the DAO
    private boolean accept(Offer offer){
        Car car = carService.getCar(offer.getCarId());
        if(car == null)
            return false;
        // we need to get all other offers on the car and reject or close them
        DealershipList<SearchQuery<OfferSearchCondition>> query = new DealershipList<>();
        query.add(new SearchQuery<>(OfferSearchCondition.CAR_ID, offer.getCarId()));
        Optional<DealershipList<Offer>> result = getAll(query);
        if(result.isPresent()){
            DealershipList<Offer> offers = result.get();
            for(int i = 0; i < offers.size(); i++){ // reject all of the open offers that aren't our offer
                if((offers.get(i).getId()) == offer.getId())
                    offers.get(i).setOfferStatus(OfferStatus.ACCEPTED); //accept the offer that was passed
                else
                    offers.get(i).setOfferStatus(OfferStatus.REJECTED); //reject all other offers
            }
            updateAll(offers);
            // update the car to reflect the new owner
            car.setUserId(offer.getUserId());
            car.setOwnership(Ownership.OWNED);
            carService.updateCar(car);
            return true;
        }
        return false;
    }

    private boolean create(Offer offer) {
        try{
            Car car = carService.getCar(offer.getCarId());
            if(car != null){     // Check to make sure the car exists
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

    private Offer getById(Long offerId){
        try{
            Optional<Offer> result = offerDao.getById(offerId);
            if(result.isPresent()) return result.get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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

    private Optional<DealershipList<Offer>> getAll(DealershipList<SearchQuery<OfferSearchCondition>> query) {
        try{
            Optional<DealershipList<Offer>> result = offerDao.getAll(query);
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

    private void updateAll(DealershipList<Offer> offers){
        try{
            offerDao.updateAll(offers);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
