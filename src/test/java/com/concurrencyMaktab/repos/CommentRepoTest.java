package com.concurrencyMaktab.repos;

import com.concurrencyMaktab.controllers.ConClass;
import com.concurrencyMaktab.controllers.ConClassTest;
import com.concurrencyMaktab.models.Comment;
import com.concurrencyMaktab.models.Twit;
import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.services.TwitService;
import com.concurrencyMaktab.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepoTest {
    private static Connection connection;
    private static CommentRepo commentRepo;
    private static TwitService twitService;
    private static UserService userService;

    @BeforeAll
    static void initialize() {
        connection = ConClassTest.getInstance().getConnection();
        commentRepo = new CommentRepo(connection);
        twitService = new TwitService(connection);
        userService = new UserService(connection);
    }

    @Test
    void connectionTest() {
        //Arrange

        //Act

        //Assert
        assertDoesNotThrow(() -> connection = ConClass.getInstance().getConnection());
    }

    @Test
    void insertTest() {
        //Arrange
        User user = new User(0,"user","user","user","user","user@mail.com");
        Integer userId = userService.singUp(user);
        user.setId(userId);
        Twit twit = new Twit(0,"twit content",user,new Timestamp(123545968345L));
        Integer twitId = twitService.twit(twit);
        twit.setId(twitId);
        Comment comment = new Comment(0,"comment content",twit,user,new Timestamp(4356134523L));

        //Act
        Integer commentId = commentRepo.ins(comment);
        comment.setId(commentId);

        //Assert
        assertNotNull(commentId);
        assertNotEquals(0,comment.getId());
        assertEquals(commentId,comment.getId());
    }


    @Test
    void readTest() {
        //Arrange
        User user = new User(0,"user","user","user","user","user@mail.com");
        Integer userId = userService.singUp(user);
        user.setId(userId);
        Twit twit = new Twit(0,"twit content",user,new Timestamp(123545968345L));
        Integer twitId = twitService.twit(twit);
        twit.setId(twitId);
        Comment comment = new Comment(0,"comment content",twit,user,new Timestamp(4356134523L));
        Integer commentId = commentRepo.ins(comment);
        comment.setId(commentId);

        //Act
        Comment comment1 = commentRepo.read(commentId);

        //Assert
        assertNotNull(comment1);
        assertEquals("comment content",comment1.getContent());
    }

    @Test
    void readAllTest() {
        //Arrange
        User user = new User(0,"user","user","user","user","user@mail.com");
        Integer userId = userService.singUp(user);
        user.setId(userId);
        Twit twit = new Twit(0,"twit content",user,new Timestamp(123545968345L));
        Integer twitId = twitService.twit(twit);
        twit.setId(twitId);
        Comment comment = new Comment(0,"comment content",twit,user,new Timestamp(4356134523L));
        Integer commentId = commentRepo.ins(comment);
        comment.setId(commentId);

        //Act
        List<Comment> comments = commentRepo.readAll();

        //Assert
        assertNotNull(comments);
        assertEquals(1,comments.size());
        assertNotEquals(0,comments.size());
    }

    @Test
    void updateTest() {
        //Arrange
        User user = new User(0,"user","user","user","user","user@mail.com");
        Integer userId = userService.singUp(user);
        user.setId(userId);
        Twit twit = new Twit(0,"twit content",user,new Timestamp(123545968345L));
        Integer twitId = twitService.twit(twit);
        twit.setId(twitId);
        Comment comment = new Comment(0,"comment content",twit,user,new Timestamp(4356134523L));
        Integer commentId = commentRepo.ins(comment);
        comment.setId(commentId);

        //Act
        Comment comment1 = new Comment(commentId,"edited comment",twit,user,null);
        Integer updateId = commentRepo.update(comment1);
        Comment updatedComment = commentRepo.read(updateId);

        //Assert
        assertNotNull(updateId);
        assertNotNull(updatedComment);
        assertEquals("edited comment",updatedComment.getContent());
        assertNotEquals("comment content",updatedComment.getContent());
    }

    @Test
    void deleteTest() {
        //Arrange
        User user = new User(0,"user","user","user","user","user@mail.com");
        Integer userId = userService.singUp(user);
        user.setId(userId);
        Twit twit = new Twit(0,"twit content",user,new Timestamp(123545968345L));
        Integer twitId = twitService.twit(twit);
        twit.setId(twitId);
        Comment comment = new Comment(0,"comment content",twit,user,new Timestamp(4356134523L));
        Integer commentId = commentRepo.ins(comment);
        comment.setId(commentId);

        //Act
        Integer deleteId = commentRepo.delete(commentId);

        //Assert
        assertNotNull(deleteId);
        assertNull(commentRepo.read(commentId));
        assertEquals(0,commentRepo.readAll().size());
    }


    @AfterEach
    void cleanUp() {
        commentRepo.truncate();
        twitService.truncate();
        userService.truncate();
    }

}