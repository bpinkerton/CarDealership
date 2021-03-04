package com.cardealership.model;

public class Car {
    private long id;
    private long userId;
    private Ownership ownership;

    private String make;
    private String model;
    private String year;
    private double price;
    private double balanceRemaining;
    private FinancingType financingType;

    public Car() {
        this.ownership = Ownership.UNOWNED;
        this.userId = 0;
        this.financingType = FinancingType.NONE;
    }

    public Car(long id, long userId, Ownership ownership, String make, String model,
               String year, double price, double balanceRemaining, FinancingType financingType) {
        this.id = id;
        this.userId = userId;
        this.ownership = ownership;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.balanceRemaining = balanceRemaining;
        this.financingType = financingType;
    }

    public Car(String make, String model, String year, double price) {
        this();
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.balanceRemaining = price;
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

    public double getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(double balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

    public FinancingType getFinancingType() {
        return financingType;
    }

    public void setFinancingType(FinancingType financingType) {
        this.financingType = financingType;
    }

    @Override
    public String toString() {
        if(!this.ownership.equals(Ownership.OWNED))
            return String.format("" +
                    "id= '%d'\t" +
                    "ownership = '%s'\t" +
                    "make= '%s'\t" +
                    "model= '%s'\t" +
                    "year= '%s'\t" +
                    "price= $%.2f\t"
                    , id, ownership, make, model, year, price
            );
            //            "id=" + id +
//                ",\t make='" + make + '\'' +
//                ",\t model='" + model + '\'' +
//                ",\t year='" + year + '\'' +
//                ",\t price=" + price;
        else
            return  String.format("" +
                    "id= '%d'\t" +
                    "ownership = '%s'\t" +
                    "make= '%s'\t" +
                    "model= '%s'\t" +
                    "year= '%s'\t" +
                    "price= $%.2f\t" +
                    "balance= $%.2f\t" +
                    "financing= '%s'"
                    , id, ownership, make, model, year, price, balanceRemaining, financingType
            );
//                    "id=" + id +
//                    ",\t ownership='" + ownership + '\'' +
//                    ",\t make='" + make + '\'' +
//                    ",\t model='" + model + '\'' +
//                    ",\t year='" + year + '\'' +
//                    ",\t price=$" + price +
//                    ",\t balance=$" + balanceRemaining +
//                    ",\t financing='" + financingType + '\'';

    }
}
