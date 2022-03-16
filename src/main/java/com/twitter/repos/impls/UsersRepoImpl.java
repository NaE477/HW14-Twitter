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
                return null;
            }
        }
    }

    public List<User> searchUsername(String username) {
        try (var session = super.getSessionFactory().openSession()) {
            try {
                return session
                        .createQuery("from User u where u.email like :username", User.class)
                        .setParameter("username", "%" + username + "%")
                        .list();
            } catch (Exception e) {
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
                return null;
            }
        }
    }

    public void delete(User user) {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                session.createQuery("update Reply r set isDeleted = true where r.user.id = :userId").setParameter("userId",user.getId()).executeUpdate();
                session.createQuery("update Comment c set isDeleted = true where c.user.id = :userId").setParameter("userId",user.getId()).executeUpdate();
                session.createQuery("update Twit t set isDeleted = true where t.user.id = :userId").setParameter("userId",user.getId()).executeUpdate();
                session.createQuery("delete Like l where l.liker.id = :userId").setParameter("userId",user.getId()).executeUpdate();
                session.createQuery("delete User u where u.id = :userId").setParameter("userId",user.getId()).executeUpdate();
                transaction.commit();
            } catch (Exception e ){
                transaction.rollback();
            }
        }
    }

    public void truncate() {
        try (var session = super.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            try {
                String truncateStmt = "delete from users;";
                session.createNativeQuery(truncateStmt).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
            }
        }
    }
}
