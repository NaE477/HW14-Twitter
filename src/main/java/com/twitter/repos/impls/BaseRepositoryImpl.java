package com.twitter.repos.impls;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.Identity;
import com.twitter.repos.interfaces.BaseRepository;
import com.twitter.services.impls.BaseServiceImpl;
import lombok.Getter;
import org.hibernate.SessionFactory;

@Getter
public abstract class BaseRepositoryImpl<T extends Identity> implements BaseRepository<T> {
    private SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    public BaseRepositoryImpl() {
    }

    public BaseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T ins(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.save(t);
                transaction.commit();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return null;
            }
        }
    }

    @Override
    public T update(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.update(t);
                transaction.commit();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(T t) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.delete(t);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
