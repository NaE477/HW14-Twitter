package com.twitter.repos.impls;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.TwitRepo;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class TwitRepoImpl extends BaseRepositoryImpl<Twit> implements TwitRepo {

    public TwitRepoImpl() {
        super();
    }

    public TwitRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Twit readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                var twit = session.get(Twit.class, id);
                session.close();
                return twit;
            } catch (Exception e) {
                e.printStackTrace();
                session.close();
                return null;
            }
        }
    }

    public List<Twit> readByUser(User user) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                var list = session.createQuery("from Twit t where t.user.id = :userId", Twit.class)
                        .setParameter("userId", user.getId())
                        .list();
                session.close();
                return list;
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
    }

    public List<Twit> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                var list = session.createQuery("from Twit", Twit.class)
                        .list();
                session.close();
                return list;
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
    }

    public void delete(Twit twit) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session
                        .createQuery("update Twit t set t.isDeleted = true where t.id = :twitId")
                        .setParameter("twitId",twit.getId())
                        .executeUpdate();
                transaction.commit();
                session.close();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void delete(User user) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session
                        .createQuery("update Twit t set t.isDeleted = true where t.user.id = :userId")
                        .setParameter("userId",user.getId())
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.createSQLQuery("TRUNCATE twits cascade ;").executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
