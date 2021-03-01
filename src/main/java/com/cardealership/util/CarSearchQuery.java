package com.cardealership.util;

import java.util.HashMap;
import java.util.Map;


public class CarSearchQuery extends HashMap<CarSearchCondition, Object> {

    Map<CarSearchCondition, Object> query;

    public CarSearchQuery() {
        this.query = new HashMap<>();
    }

    public void addCondition(CarSearchCondition c, Object o){
        this.put(c,o);
    }

}
