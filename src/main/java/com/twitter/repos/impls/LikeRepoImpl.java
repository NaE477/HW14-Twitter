package com.twitter.repos.impls;

import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Like;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.LikeRepo;
import org.hibernate.SessionFactory;

import java.util.List;

public class LikeRepoImpl extends BaseRepositoryImpl<Like> implements LikeRepo {
    public LikeRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Like readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.get(Like.class, id);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public List<Like> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Like",Like.class).list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                String truncateStmt = "TRUNCATE likes cascade ;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }

    @Override
    public <T extends BaseTwit> Like readByTwitAndUser(T twit, User user) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Like l where l.twit.id = :twitId and l.liker.id = :userId",Like.class)
                        .setParameter("twitId",twit.getId())
                        .setParameter("userId",user.getId())
                        .getSingleResult();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
