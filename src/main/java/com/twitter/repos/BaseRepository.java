package com.twitter.repos;

import com.twitter.models.Identity;

import java.util.List;

public interface BaseRepository<T extends Identity>{
    T ins(T t);

    T readById(Integer id);
    List<T> readAll();

    T update(T t);

    void delete(T t);
    void truncate();
}
