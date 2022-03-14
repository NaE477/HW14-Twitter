package com.twitter.services.interfaces;

import com.twitter.models.Identity;

import java.util.List;

public interface BaseService<T extends Identity> {
    T insert(T t);

    T findById(Integer id);

    List<T> findAll();

    T update(T t);

    void delete(T t);

    void truncate();
}
