package com.twitter.repos.impls;

import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.interfaces.CommentRepo;
import org.hibernate.SessionFactory;

import java.util.List;

public class CommentRepoImpl extends BaseRepositoryImpl<Comment> implements CommentRepo, com.twitter.repos.interfaces.BaseRepository<Comment> {

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
                return null;
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
                e.printStackTrace();
                return null;
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
