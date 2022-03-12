package com.twitter.repos;

import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;

import java.util.List;

public class TwitRepoImpl extends BaseRepositoryImpl<Twit> implements TwitRepo {

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

    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.getTransaction();
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
