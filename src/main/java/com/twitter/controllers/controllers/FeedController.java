package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.twits.TwitProxy;
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

import java.util.*;

public class FeedController {
    private final TwitService twitService;
    private final CommentService commentService;
    private final Utilities utils;
    private final Scanner sc;

    private final User user;

    public FeedController(Integer userId) {
        twitService = new TwitServiceImpl(new TwitRepoImpl());
        commentService = new CommentServiceImpl(new CommentRepoImpl());
        utils = new Utilities();
        sc = new Scanner(System.in);

        UserService userService = new UserServiceImpl(new UsersRepoImpl());
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
            if (!toCheck.getIsDeleted()) {
                utils.printGreen(toCheck.toString());
                List<Comment> comments = commentService.findAllByTwit(toCheck);
                utils.printRed("---------------------------------Comments-----------------------------------");
                utils.iterateThrough(comments);
            } else utils.printRed("Twit is deleted");
        } else utils.printRed("Wrong ID");
    }

    private void observeTwit() {
        System.out.println("Enter twit ID: ");
        Integer twitId = utils.intReceiver();
        TwitProxy toObserve = new TwitProxy(twitId,false);
        if (toObserve.getTwit() != null) {
            if (!toObserve.getTwit().getIsDeleted()) {
                ObserveTwitController<TwitProxy> observeTwitController = new ObserveTwitController<>(toObserve, user.getId());
                observeTwitController.viewTwit();
            } else utils.printRed("Twit is deleted");
        } else utils.printRed("Wrong ID");
    }

    private void viewAllTwits() {
        utils.iterateThrough(twitService.findAll());
    }
}
