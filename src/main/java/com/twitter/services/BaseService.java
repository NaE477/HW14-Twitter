package com.twitter.services;

import com.twitter.models.Identity;
import com.twitter.repos.BaseRepositoryImpl;

public interface BaseService<T extends Identity,R extends BaseRepositoryImpl<T>> {
    T ins(T t);
    T update(T t);
    void delete(T t);
    T findById(Integer id);
}
