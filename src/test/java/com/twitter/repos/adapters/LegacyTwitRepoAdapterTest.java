package com.twitter.repos.adapters;

import com.twitter.models.twits.Like;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.SessionFactorySingletonTest;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.repos.legacy.TwitRepo;
import com.twitter.services.impls.UserServiceImpl;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LegacyTwitRepoAdapterTest {
    private static LegacyTwitRepoAdapter adapter;
    private static UserService userService;

    @BeforeAll
    static void initialize() {
        Connection connection = null;
        try {
            connection = SessionFactorySingletonTest.getInstance()
                    .getSessionFactoryOptions()
                    .getServiceRegistry()
                    .getService(ConnectionProvider.class)
                    .getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new LegacyTwitRepoAdapter(new TwitRepo(connection));
        userService = new UserServiceImpl(new UsersRepoImpl(SessionFactorySingletonTest.getInstance()));
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

    @Test
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
    }

    @AfterEach
    void wipe() {
        userService.truncate();
        adapter.truncate();
    }
}