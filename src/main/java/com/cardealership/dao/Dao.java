package com.cardealership.dao;

import com.cardealership.util.DealershipList;

import java.util.Optional;

public interface Dao<T,I>{

    Optional<T> getById(I id) throws Exception;

    Optional<DealershipList<T>> getAll() throws Exception;

    boolean create(T t) throws Exception;

    void update(T t) throws Exception;

    void remove(I id) throws Exception;
}
