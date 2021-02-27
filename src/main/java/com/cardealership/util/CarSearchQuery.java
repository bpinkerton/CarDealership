package com.cardealership.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CarSearchQuery extends HashMap<CarSearchCondition, Object> {

    Map<CarSearchCondition, Object> query;

    public CarSearchQuery() {
        this.query = new HashMap<>();
    }

    public CarSearchQuery(Map m, Map<CarSearchCondition, Object> query) {
        super(m);
        this.query = query;
    }

    public void addCondition(CarSearchCondition c, Object o){
        this.put(c,o);
    }
}
