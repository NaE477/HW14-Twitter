package com.twitter.repos;

import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.TwitServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentRepoTest {
    private static CommentRepoImpl commentRepo;
    private static TwitServiceImpl twitService;
    private static UserServiceImpl userService;

    @BeforeAll
    static void initialize() {
        SessionFactory sessionFactory = SessionFactorySingletonTest.getInstance();
        commentRepo = new CommentRepoImpl(sessionFactory);

        twitService = new TwitServiceImpl(new TwitRepoImpl(sessionFactory));
        userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
    }

    @Test
    void connectionTest() {
        //Arrange
        final SessionFactory[] sessionFactory = {null, null, null, null};
        //Act

        //Assert
        assertDoesNotThrow(() -> {
            sessionFactory[0] = (SessionFactorySingleton.getInstance());
            sessionFactory[1] = (SessionFactorySingleton.getInstance());
            sessionFactory[2] = (SessionFactorySingleton.getInstance());
            sessionFactory[3] = (SessionFactorySingleton.getInstance());
        });
        assertNotNull(sessionFactory[0]);
        assertNotNull(sessionFactory[1]);
        assertNotNull(sessionFactory[2]);
        assertNotNull(sessionFactory[3]);
    }

    @Test
    void insertTest() {
        //Arrange
        User user = new User(0, "user", "user", "user", "user", "user@mail.com", new HashSet<>(), new HashSet<>());
        User newUser = userService.insert(user);
        Twit twit = new Twit("twit content", newUser);
        twitService.insert(twit);
        Comment comment = new Comment("comment content", user, twit);

        //Act
        Comment newComment = commentRepo.ins(comment);

        //Assert
        assertNotNull(newComment);
        assertNotEquals(0, comment.getId());
        assertEquals(comment.getId(), newComment.getId());
    }


    @Test
    void readTest() {
        //Arrange
        User user = new User(0, "user", "user", "user", "user", "user@mail.com", new HashSet<>(), new HashSet<>());
        User newUser = userService.insert(user);
        Twit twit = new Twit("twit content", user);
        twitService.insert(twit);
        Comment comment = new Comment("comment content", newUser, twit);
        Comment newComment = commentRepo.ins(comment);

        //Act
        Comment comment1 = commentRepo.readById(newComment.getId());

        //Assert
        assertNotNull(comment1);
        assertEquals("comment content", comment1.getContent());
    }

    @Test
    void readAllTest() {
        //Arrange
        User user = new User(0, "user", "user", "user", "user", "user@mail.com", new HashSet<>(), new HashSet<>());
        userService.insert(user);
        Twit twit = new Twit("twit content", user);
        twitService.insert(twit);
        Comment comment = new Comment("comment content", user, twit);
        commentRepo.ins(comment);

        //Act
        List<Comment> comments = commentRepo.readAll();

        //Assert
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertNotEquals(0, comments.size());
    }

    @Test
    void updateTest() {
        //Arrange
        User user = new User(0, "user", "user", "user", "user", "user@mail.com", new HashSet<>(), new HashSet<>());
        userService.insert(user);
        Twit twit = new Twit("twit content", user);
        twitService.insert(twit);
        Comment comment = new Comment("comment content", user, twit);
        Comment newComment = commentRepo.ins(comment);

        //Act
        Comment comment1 = commentRepo.readById(newComment.getId());
        comment1.setContent("edited comment");
        Comment updateComment = commentRepo.update(comment1);
        Comment updatedComment = commentRepo.readById(updateComment.getId());

        //Assert
        assertNotNull(updatedComment);
        assertNotNull(updatedComment);
        assertEquals("edited comment", updatedComment.getContent());
        assertNotEquals("comment content", updatedComment.getContent());
    }

    @Test
    void deleteTest() {
        //Arrange
        User user = new User(0, "user", "user", "user", "user", "user@mail.com", new HashSet<>(), new HashSet<>());
        userService.insert(user);
        Twit twit = new Twit("twit content", user);
        twitService.insert(twit);
        Comment comment = new Comment("comment content", user, twit);
        Comment newComment = commentRepo.ins(comment);

        //Act
        commentRepo.delete(newComment);

        //Assert
        assertNull(commentRepo.readById(newComment.getId()));
        assertEquals(0, commentRepo.readAll().size());
    }


    @AfterEach
    void cleanUp() {
        commentRepo.truncate();
        twitService.truncate();
        userService.truncate();
    }

}