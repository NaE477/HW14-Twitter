package com.twitter.repos;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.repos.interfaces.UsersRepo;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwitRepoTest {
    private static TwitRepoImpl twitRepo;
    private static UsersRepoImpl usersRepo;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void initialize() {
        sessionFactory = SessionFactorySingletonTest.getInstance();
        twitRepo = new TwitRepoImpl(sessionFactory);
        usersRepo = new UsersRepoImpl(sessionFactory);
    }

    @Test
    void connectionTest() {
        //Arrange
        final SessionFactory[] sessionFactory = new SessionFactory[1];
        //Act

        //Assert
        assertDoesNotThrow(() -> {

            sessionFactory[0] = (SessionFactorySingleton.getInstance());
        });
        assertNotNull(sessionFactory[0]);
    }

    @Test
    void ins() {
        //Arrange
        User user = new User(0, "user", "user", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        usersRepo.ins(user);
        Twit twit = new Twit("content", user);

        //Act
        Twit newTwit = twitRepo.ins(twit);

        //Assert
        assertNotNull(newTwit);
        assertEquals("content", twitRepo.readById(newTwit.getId()).getContent());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0, "user", "user", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        UsersRepo usersRepo = new UsersRepoImpl(sessionFactory);
        usersRepo.ins(user);
        Twit twit = new Twit("content", user);
        Twit newTwit = twitRepo.ins(twit);

        //Act
        Twit toFind = twitRepo.readById(newTwit.getId());

        //Assert
        assertNotNull(toFind);
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0, "user", "user", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        usersRepo.ins(user);
        Twit twit = new Twit("content", user);
        twitRepo.ins(twit);

        //Act
        List<Twit> twits = twitRepo.readAll();

        //Assert
        assertNotNull(twits);
        assertEquals(1, twits.size());
    }

    @Test
    void update() {
        //Arrange
        User user = new User(0, "user", "user", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        usersRepo.ins(user);
        Twit twit = new Twit("content", user);
        twitRepo.ins(twit);

        //Act
        Twit toUpdate = twitRepo.readById(twit.getId());
        toUpdate.setContent("edit");
        Twit updatedTwit = twitRepo.update(toUpdate);

        //Assert
        assertNotNull(updatedTwit);
        assertEquals("edit", twitRepo.readById(updatedTwit.getId()).getContent());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0, "user", "user", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        usersRepo.ins(user);
        Twit twit = new Twit("content", user);
        Twit newTwit = twitRepo.ins(twit);

        //Act
        twitRepo.delete(newTwit);

        //Assert
        assertNull(twitRepo.readById(newTwit.getId()));
    }

    @AfterEach
    void truncate() {
        usersRepo.truncate();
        twitRepo.truncate();
    }
}