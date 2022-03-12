package com.twitter.repos.legacy;

import java.util.List;

public interface Repo<T>{
    Integer ins(T t);
    T read(Integer id);
    List<T> readAll();
    Integer update(T t);
    Integer delete(Integer id);
}
