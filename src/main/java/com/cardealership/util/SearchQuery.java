package com.cardealership.util;

public class SearchQuery<E> {
    private E searchCondition;
    private Object value;

    public SearchQuery(E searchCondition, Object value) {
        this.searchCondition = searchCondition;
        this.value = value;
    }

    public E getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(E searchCondition) {
        this.searchCondition = searchCondition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
