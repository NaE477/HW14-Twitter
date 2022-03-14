package com.twitter.controllers;

import com.twitter.Utilities;
import com.twitter.controllers.userControllers.commentingControllers.CommentingController;
import com.twitter.controllers.userControllers.profileController.ProfileController;
import com.twitter.controllers.userControllers.searchingControllers.SearchingController;
import com.twitter.controllers.userControllers.twittingControllers.TwittingController;
import com.twitter.models.twits.Comment;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class UserController {
    private final User user;
    private final CommentService commentService;
    private final SessionFactory sessionFactory;
    private final Utilities utils;
    private final Scanner sc = new Scanner(System.in);

    public UserController(SessionFactory sessionFactory, Integer userId) {
        this.sessionFactory = sessionFactory;
        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        this.user = userService.findById(userId);
        utils = new Utilities(sessionFactory);
    }

    public void entry() {
        label:
        while (true) {
            System.out.println("Welcome To User Section.\nChoose an Option: ");

            ArrayList<String> menu = new ArrayList<>();
            menu.add("1-Comment under other twits");
            menu.add("2-Twitting");
            menu.add("3-Your Profile");
            menu.add("4-Search for user");
            menu.add("0-Exit");

            utils.menuViewer(menu);

            String inp = sc.nextLine();
            switch (inp) {
                case "1":
                    comments();
                    break;
                case "2":
                    twitting();
                    break;
                case "3":
                    profile();
                    break;
                case "4":
                    searchUser();
                    break;
                case "0":
                    break label;
            }
        }
    }


    public void comments() {
        CommentingController commentingController = new CommentingController(sessionFactory,user.getId());
        commentingController.entry();
    }

    private void twitting() {
        TwittingController twittingController = new TwittingController(sessionFactory,user.getId());
        twittingController.entry();
    }

    private void profile() {
        ProfileController profileController = new ProfileController(sessionFactory,user.getId());
        profileController.entry();
    }

    private void searchUser() {
        SearchingController searchingController = new SearchingController(sessionFactory);
        searchingController.entry();
    }

    public void editComment(Comment comment) {
        System.out.println("Enter new Content for the comment: ");
        String newContent = utils.contentReceiver();
        comment.setContent(newContent);
        Comment editedComment = commentService.update(comment);
        System.out.println("Twit edited with ID: " + editedComment.getId());
    }
}
