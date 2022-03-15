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

import java.util.*;

public class FeedController {
    private final TwitService twitService;
    private final CommentService commentService;
    private final SessionFactory sessionFactory;
    private final Utilities utils;
    private final Scanner sc;

    private final User user;

    public FeedController(SessionFactory sessionFactory, Integer userId) {
        twitService = new TwitServiceImpl(new TwitRepoImpl(sessionFactory));
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        this.sessionFactory = sessionFactory;
        utils = new Utilities(sessionFactory);
        sc = new Scanner(System.in);

        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        user = userService.findById(userId);
    }

    public void entry() {
        label:
        while (true) {
            System.out.println("Welcome to feed");
            System.out.println("Choose an option: ");
            ArrayList<String> menu = new ArrayList<>();
            menu.add("1-View Twit With Comments");
            menu.add("2-Interact with Twit");
            menu.add("3-View all twits");
            menu.add("0-Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    viewTwit();
                    break;
                case "2":
                    observeTwit();
                    break;
                case "3":
                    viewAllTwits();
                    break;
                case "0":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    private void viewTwit() {
        System.out.println("Enter twit ID: ");
        Integer twitId = utils.intReceiver();
        Twit toCheck = twitService.findById(twitId);
        if (toCheck != null) {
            utils.printGreen(toCheck.toString());
            List<Comment> comments = commentService.findAllByTwit(toCheck);
            utils.printRed("---------------------------------Comments-----------------------------------");
            utils.iterateThrough(comments);
        } else utils.printRed("Wrong ID");
    }

    private void observeTwit() {
        System.out.println("Enter twit ID: ");
        Integer twitId = utils.intReceiver();
        Twit toComment = twitService.findById(twitId);
        if (toComment != null) {
            ObserveTwitController<Twit> observeTwitController = new ObserveTwitController<>(sessionFactory,toComment,user.getId());
            observeTwitController.viewTwit();
        } else utils.printRed("Wrong ID");
    }

    private void viewAllTwits() {
        utils.iterateThrough(twitService.findAll());
    }
}
