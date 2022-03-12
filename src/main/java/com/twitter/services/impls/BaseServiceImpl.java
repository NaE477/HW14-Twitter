package com.twitter.services.impls;

import com.twitter.models.Identity;
import com.twitter.repos.impls.BaseRepositoryImpl;
import com.twitter.services.interfaces.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<T extends Identity, R extends BaseRepositoryImpl<T>> implements BaseService<T> {
    R repo;

    public T insert(T t) {
        return repo.ins(t);
    }

    public T findById(Integer id) {
        return repo.readById(id);
    }

    public List<T> findAll() {
        return repo.readAll();
    }

    public T update(T t) {
        return repo.update(t);
    }

    public void delete(T t) {
        repo.delete(t);
    }
}
