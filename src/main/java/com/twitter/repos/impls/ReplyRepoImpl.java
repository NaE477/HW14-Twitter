package com.twitter.repos.impls;

import com.twitter.models.twits.Reply;
import com.twitter.repos.interfaces.ReplyRepo;
import org.hibernate.SessionFactory;

import java.util.List;

public class ReplyRepoImpl extends BaseRepositoryImpl<Reply> implements ReplyRepo {
    public ReplyRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Reply readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.get(Reply.class, id);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public List<Reply> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Reply",Reply.class).list();
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
                String truncateStmt = "TRUNCATE replies cascade ;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
