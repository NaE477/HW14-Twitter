package com.twitter.repos;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class TwitRepoTest {
    private static TwitRepoImpl twitRepo;
    private static UsersRepoImpl usersRepo;

    @BeforeAll
    static void initialize() {
        twitRepo = new TwitRepoImpl();
        usersRepo = new UsersRepoImpl();
    }

    @Test
    void connectionTest() {
        //Arrange
        AtomicReference<SessionFactory> sessionFactory = null;
        //Act

        //Assert
        assertDoesNotThrow( () -> {
            assert false;
            sessionFactory.set(SessionFactorySingleton.getInstance());
        });
    }

    @Test
    void ins() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        usersRepo.ins(user);
        Twit twit = new Twit("content",user);

        //Act
        Twit newTwit = twitRepo.ins(twit);

        //Assert
        assertNotNull(newTwit);
        assertEquals("content",twitRepo.readById(newTwit.getId()).getContent());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        usersRepo.ins(user);
        Twit twit = new Twit("content",user);
        Twit newTwit = twitRepo.ins(twit);

        //Act
        Twit toFind = twitRepo.readById(newTwit.getId());

        //Assert
        assertNotNull(toFind);
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        usersRepo.ins(user);
        Twit twit = new Twit("content",user);
        twitRepo.ins(twit);

        //Act
        List<Twit> twits = twitRepo.readAll();

        //Assert
        assertNotNull(twits);
        assertEquals(1,twits.size());
    }

    @Test
    void update() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        usersRepo.ins(user);
        Twit twit = new Twit("content",user);
        twitRepo.ins(twit);

        //Act
        Twit toUpdate = new Twit("edit",user);
        Twit updatedTwit = twitRepo.update(toUpdate);

        //Assert
        assertNotNull(updatedTwit);
        assertEquals("edit",twitRepo.readById(updatedTwit.getId()).getContent());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        usersRepo.ins(user);
        Twit twit = new Twit("content",user);
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