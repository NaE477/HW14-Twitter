package com.twitter.repos.impls;

import com.twitter.models.user.User;
import com.twitter.repos.interfaces.UsersRepo;
import org.hibernate.SessionFactory;

import java.util.List;

public class UsersRepoImpl extends BaseRepositoryImpl<User> implements UsersRepo {

    public UsersRepoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User readById(Integer id) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session.get(User.class, id);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public User readByUsername(String username) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from User u where u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public User readByEmail(String email) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from User u where u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<User> searchUsername(String username) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from User u where u.email like %:username%", User.class)
                        .setParameter("username", username)
                        .list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<User> readAll() {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from User", User.class)
                        .list();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                String truncateStmt = "TRUNCATE users cascade ;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
