package com.twitter.repos;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.user.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersRepoTest {
    private static SessionFactory sessionFactory;
    private static UsersRepoImpl usersRepo;

    @BeforeAll
    static void initialize() {
        sessionFactory = SessionFactorySingleton.getInstance();
        usersRepo = new UsersRepoImpl(sessionFactory);
    }

    @Test
    void connectionTest() {
        //Arrange

        //Act

        //Assert
        assertDoesNotThrow( () -> sessionFactory = SessionFactorySingleton.getInstance());
    }

    @Test
    void ins() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");

        //Act
        User userId = usersRepo.ins(user);

        //Assert
        assertNotNull(userId);
        assertEquals(1,usersRepo.readAll().size());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        User newUser = usersRepo.ins(user);

        //Act
        User userToFind = usersRepo.readById(newUser.getId());

        //Assert
        assertNotNull(userToFind);
        assertEquals(newUser.getId(),userToFind.getId());
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        usersRepo.ins(user);

        //Act
        List<User> userList = usersRepo.readAll();

        //Assert
        assertNotNull(userList);
        assertEquals(1,userList.size());
    }

    @Test
    void update() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        User newUser = usersRepo.ins(user);

        //Act
        User toUpdate = new User(newUser.getId(),"edit","editlname","edit","edit","edit");
        User updateUser = usersRepo.update(toUpdate);
        User updatedUser = usersRepo.readById(updateUser.getId());

        //Assert
        assertNotNull(updateUser);
        assertEquals("edit",updatedUser.getFirstname());
        assertEquals("editlname",updatedUser.getLastname());
        assertEquals("edit",updatedUser.getEmail());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
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