package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.controllers.ConClass;
import com.concurrencyMaktab.controllers.ConClassTest;
import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwitRepoTest {
    private static Connection connection;
    private static TwitRepo twitRepo;
    private static UsersRepo usersRepo;

    @BeforeAll
    static void initialize() {
        connection = ConClassTest.getInstance().getConnection();
        twitRepo = new TwitRepo(connection);
        usersRepo = new UsersRepo(connection);
    }

    @Test
    void connectionTest() {
        //Arrange

        //Act

        //Assert
        assertDoesNotThrow( () -> connection = ConClass.getInstance().getConnection() );
    }

    @Test
    void ins() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);
        user.setId(userId);
        Twit twit = new Twit(0,"content",user,new Timestamp(12353256L));

        //Act
        Integer insId = twitRepo.ins(twit);

        //Assert
        assertNotNull(insId);
        assertEquals("content",twitRepo.read(insId).getContent());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);
        user.setId(userId);
        Twit twit = new Twit(0,"content",user,new Timestamp(12353256L));
        Integer insId = twitRepo.ins(twit);

        //Act
        Twit toFind = twitRepo.read(insId);

        //Assert
        assertNotNull(toFind);
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);
        user.setId(userId);
        Twit twit = new Twit(0,"content",user,new Timestamp(12353256L));
        Integer insId = twitRepo.ins(twit);

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
        Integer userId = usersRepo.ins(user);
        user.setId(userId);
        Twit twit = new Twit(0,"content",user,new Timestamp(12353256L));
        Integer insId = twitRepo.ins(twit);

        //Act
        Twit toUpdate = new Twit(insId,"edit",user,new Timestamp(5467389434L));
        Integer updateId = twitRepo.update(toUpdate);

        //Assert
        assertNotNull(updateId);
        assertEquals("edit",twitRepo.read(updateId).getContent());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0,"user","user","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);
        user.setId(userId);
        Twit twit = new Twit(0,"content",user,new Timestamp(12353256L));
        Integer insId = twitRepo.ins(twit);

        //Act
        Integer deleteId = twitRepo.delete(insId);

        //Assert
        assertNotNull(deleteId);
        assertNull(twitRepo.read(insId));
        assertNull(twitRepo.read(deleteId));
    }

    @AfterEach
    void truncate() {
        usersRepo.truncate();
        twitRepo.truncate();
    }
}