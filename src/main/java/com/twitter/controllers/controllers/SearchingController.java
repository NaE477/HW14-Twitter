package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.TwitServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.TwitService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class SearchingController {
    private final UserService userService;
    private final TwitService twitService;
    private final CommentService commentService;
    private final Utilities utils;
    private final Scanner sc;

    public SearchingController(SessionFactory sessionFactory) {
        userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        twitService = new TwitServiceImpl(new TwitRepoImpl(sessionFactory));
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        utils = new Utilities(sessionFactory);
        sc = new Scanner(System.in);
    }

    public void entry() {
        whileLabel:
        while (true) {
            List<User> users = findUsersByUsername();
            utils.iterateThrough(users);
            if (users != null && users.size() > 0) {
                System.out.println("Enter Username you want to check or 0 to exit: ");
                String usernameToView = sc.nextLine();
                User userToView = userService.findByUsername(usernameToView);
                if (!usernameToView.equals("0") && userToView != null) {
                    String opt = searchMenu();
                    switch (opt) {
                        case "1":
                            viewUserTwits(userToView);
                            break;
                        case "2":
                            viewUserComments(userToView);
                            break;
                        case "3":
                            utils.printGreen(userToView.toString());
                            break;
                        case "0":
                            break whileLabel;
                    }
                } else if (usernameToView.equals("0")) {
                    break;
                } else {
                    utils.printRed("Wrong Username");
                }
            } else {
                System.out.println("No Username found");
                break;
            }
        }
    }

    private void viewUserTwits(User userToView) {
        List<Twit> twits = twitService.findTwitsByUser(userToView);
        utils.iterateThrough(twits);
        if (twits.size() > 0) {
            System.out.println("Choose Twit ID: ");
            Integer twitId = utils.intReceiver();
            Twit twitToView = twitService.findById(twitId);
            if (twitToView != null && twits.contains(twitToView)) {
                utils.printGreen(twitToView.toString());
            } else System.out.println("Wrong ID.");
        } else utils.printRed("User has no twits yet");
    }

    private void viewUserComments(User userToView) {
        List<Comment> comments = commentService.findAllByUser(userToView);
        utils.iterateThrough(comments);
    }

    private List<User> findUsersByUsername() {
        System.out.println("Enter username to search: ");
        String toSearch = sc.nextLine();
        return userService.searchUsername(toSearch);
    }

    private String searchMenu() {
        System.out.println("1-View Twits");
        System.out.println("2-View Comments");
        System.out.println("3-View Profile");
        System.out.println("0-Exit");
        return sc.nextLine();
    }
}
