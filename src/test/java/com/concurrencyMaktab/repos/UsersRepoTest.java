package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.controllers.ConClass;
import com.concurrencyMaktab.controllers.ConClassTest;
import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.services.TwitService;
import com.concurrencyMaktab.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersRepoTest {
    private static Connection connection;
    private static UsersRepo usersRepo;

    @BeforeAll
    static void initialize() {
        connection = ConClassTest.getInstance().getConnection();
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
        User user = new User(0,"fname","lname","user","pass","user@mail.com");

        //Act
        Integer userId = usersRepo.ins(user);

        //Assert
        assertNotNull(userId);
        assertEquals(1,usersRepo.readAll().size());
    }

    @Test
    void read() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);

        //Act
        User userToFind = usersRepo.read(userId);

        //Assert
        assertNotNull(userToFind);
        assertEquals(userId,userToFind.getId());
    }

    @Test
    void readAll() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);

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
        Integer userId = usersRepo.ins(user);

        //Act
        User toUpdate = new User(userId,"edit","editlname","edit","edit","edit");
        Integer updateId = usersRepo.update(toUpdate);
        User updated = usersRepo.read(updateId);

        //Assert
        assertNotNull(updateId);
        assertEquals("edit",updated.getFirstname());
        assertEquals("editlname",updated.getLastname());
        assertEquals("edit",updated.getEmail());
    }

    @Test
    void delete() {
        //Arrange
        User user = new User(0,"fname","lname","user","pass","user@mail.com");
        Integer userId = usersRepo.ins(user);

        //Act
        Integer deleteId = usersRepo.delete(userId);

        //Assert
        assertNotNull(deleteId);
        assertNull(usersRepo.read(userId));
    }

    @AfterEach
    void truncate() {
        usersRepo.truncate();
    }
}