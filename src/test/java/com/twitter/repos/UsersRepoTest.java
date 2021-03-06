package com.twitter.repos;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.user.User;
import com.twitter.repos.impls.UsersRepoImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersRepoTest {
    private static UsersRepoImpl usersRepo;

    @BeforeAll
    static void initialize() {
        usersRepo = new UsersRepoImpl();
    }

    @Test
    void connectionTest() {
        //Arrange
        SessionFactory[] sessionFactory = {null};
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
        User user = new User(0, "fname", "lname", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());

        //Act
        User userId = usersRepo.ins(user);

        //Assert
        assertNotNull(userId);
        assertEquals(1, usersRepo.readAll().size());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0, "fname", "lname", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        User newUser = usersRepo.ins(user);

        //Act
        User userToFind = usersRepo.readById(newUser.getId());

        //Assert
        assertNotNull(userToFind);
        assertEquals(newUser.getId(), userToFind.getId());
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0, "fname", "lname", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        usersRepo.ins(user);

        //Act
        List<User> userList = usersRepo.readAll();

        //Assert
        assertNotNull(userList);
        assertEquals(1, userList.size());
    }

    @Test
    void update() {
        //Arrange
        User user = new User(0, "fname", "lname", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        User newUser = usersRepo.ins(user);

        //Act
        User toUpdate = new User(newUser.getId(), "edit", "editlname", "edit", "edit", "edit", new HashSet<>(), new HashSet<>());
        User updateUser = usersRepo.update(toUpdate);
        User updatedUser = usersRepo.readById(updateUser.getId());

        //Assert
        assertNotNull(updateUser);
        assertEquals("edit", updatedUser.getFirstname());
        assertEquals("editlname", updatedUser.getLastname());
        assertEquals("edit", updatedUser.getEmail());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0, "fname", "lname", "user", "pass", "user@mail.com", new HashSet<>(), new HashSet<>());
        User newUser = usersRepo.ins(user);

        //Act
        usersRepo.delete(newUser);

        //Assert
        assertNull(usersRepo.readById(newUser.getId()));
    }

    @AfterEach
    void truncate() {
        usersRepo.truncate();
    }
}