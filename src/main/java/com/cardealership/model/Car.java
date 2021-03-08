package com.cardealership.model;

public class Car {
    private long id;
    private long userId;
    private Ownership ownership;

    private String make;
    private String model;
    private String year;
    private double price;

    //TODO: extract balanceRemaining and financingType into Financing model

    public Car() {
        this.ownership = Ownership.UNOWNED;
        this.userId = 0;
    }

    public Car(long id, long userId, Ownership ownership, String make, String model,
               String year, double price) {
        this.id = id;
        this.userId = userId;
        this.ownership = ownership;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public Car(String make, String model, String year, double price) {
        this();
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public void setOwnership(Ownership ownership) {
        this.ownership = ownership;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("" +
                "id= '%4d'\t" +
                "ownership = '%s'  \t" +
                "make= '%s'\t" +
                "model= '%s'\t" +
                "year= '%s'\t" +
                "price= $%10.2f\t"
                , id, ownership, make, model, year, price
        );
    }
}
