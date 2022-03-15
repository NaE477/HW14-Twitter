package com.twitter.repos.impls;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.TwitRepo;
import org.hibernate.SessionFactory;

import java.util.List;

public class TwitRepoImpl extends BaseRepositoryImpl<Twit> implements TwitRepo {

    public TwitRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Twit readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.get(Twit.class, id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Twit> readByUser(User user) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Twit t where t.user.id = :userId", Twit.class)
                        .setParameter("userId", user.getId())
                        .list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Twit> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Twit", Twit.class)
                        .list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void delete(Twit twit) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session
                        .createQuery("update Twit t set t.isDeleted = true where t.id = :twitId",Twit.class)
                        .setParameter("twitId",twit.getId())
                        .executeUpdate();
                transaction.commit();
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
                        .createQuery("update Twit t set t.isDeleted = true where t.user.id = :userId",Twit.class)
                        .setParameter("userId",user.getId())
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                String truncateStmt = "TRUNCATE twits cascade ;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
