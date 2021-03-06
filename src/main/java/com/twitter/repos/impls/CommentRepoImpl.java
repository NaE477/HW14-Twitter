package com.twitter.repos.impls;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.CommentRepo;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class CommentRepoImpl extends BaseRepositoryImpl<Comment> implements CommentRepo {

    public CommentRepoImpl() {
        super();
    }

    public CommentRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.get(Comment.class, id);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public List<Comment> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.createQuery("from Comment",Comment.class).list();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Comment> readAllByTwit(Twit twit){
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from Comment c where c.ownerTwit.id = :twitId",Comment.class)
                        .setParameter("twitId",twit.getId())
                        .list();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<Comment> readAllByUser(User user){
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from Comment c where c.user.id = :userId",Comment.class)
                        .setParameter("userId",user.getId())
                        .list();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
    }

    @Override
    public void delete(Comment comment) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session
                        .createQuery("update Comment c set c.isDeleted = true where c.id = :commentId")
                        .setParameter("commentId",comment.getId())
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(User user) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session
                        .createQuery("update Comment c set c.isDeleted = true where c.user.id = :userId")
                        .setParameter("userId",user.getId())
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                String truncateStmt = "TRUNCATE comments cascade ;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
