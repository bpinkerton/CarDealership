package com.cardealership.dao;

import java.util.Optional;

public interface Dao<T>{

    Optional<T>  get(long id) throws Exception;

    void create(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;
}
