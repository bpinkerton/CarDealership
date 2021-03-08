package com.cardealership.model;

public class Offer {
    private long id;
    private long carId;
    private long userId;

    private OfferStatus offerStatus;

    public Offer(){
        this.offerStatus = OfferStatus.OPEN;
    }

    public Offer(long carId, long userId){
        this();
        this.carId = carId;
        this.userId = userId;
    }

    public Offer(long id, long carId, long userId, OfferStatus offerStatus) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.offerStatus = offerStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    @Override
    public String toString() {
        return String.format("" +
                        "id= '%d'     \t\t" +
                        "carId = '%d'     \t\t" +
                        "userId= '%d'     \t\t" +
                        "offerStatus= '%s'"
                , id, carId, userId, offerStatus
        );
    }
}
