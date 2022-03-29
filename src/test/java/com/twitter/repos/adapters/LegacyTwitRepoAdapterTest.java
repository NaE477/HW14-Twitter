package com.twitter.repos.adapters;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.repos.legacy.TwitRepo;
import com.twitter.services.impls.TwitServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.TwitService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LegacyTwitRepoAdapterTest {
    private static LegacyTwitRepoAdapter adapter;
    private static UserService userService;

    @BeforeAll
    static void initialize() throws SQLException {
        //Connection connection = SessionFactorySingleton.getInstance().openStatelessSession().connection();
        Connection connection = SessionFactorySingleton.getInstance()
                    .getSessionFactoryOptions()
                    .getServiceRegistry()
                    .getService(ConnectionProvider.class)
                    .getConnection();
        adapter = new LegacyTwitRepoAdapter(new TwitRepo(connection));
        userService = new UserServiceImpl(new UsersRepoImpl());
    }

    @Test
    void ins() {
        //Arrange
        User user = new User(0,"user","user","username","userpass","usermail",new HashSet<>(),new HashSet<>());
        userService.insert(user);
        Twit twit = new Twit(0,"new Twit",user,new Date(123453154L),new HashSet<>(List.of()));
        //Act
        Twit newTwit = adapter.ins(twit);
        //Assert
        assertNotNull(newTwit);
    }

    /*@Test
    void readById() {
    }

    @Test
    void readAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void truncate() {
    }*/

    @AfterEach
    void wipe() {
        TwitService twitService = new TwitServiceImpl(new TwitRepoImpl());
        twitService.truncate();
        userService.truncate();
    }
}