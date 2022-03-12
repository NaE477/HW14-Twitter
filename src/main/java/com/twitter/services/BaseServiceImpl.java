package com.twitter.services;

import com.twitter.models.Identity;
import com.twitter.repos.BaseRepositoryImpl;
import org.hibernate.SessionFactory;

public abstract class BaseServiceImpl<T extends Identity,R extends BaseRepositoryImpl<T>> implements BaseService<T,R> {
    private final SessionFactory sessionFactory;

    public BaseServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
