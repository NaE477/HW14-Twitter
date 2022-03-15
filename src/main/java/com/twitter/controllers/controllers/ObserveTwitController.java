package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.*;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ObserveTwitController<T extends Twit> {
    private final LikingController<Twit> twitLikingController;
    private final CommentService commentService;
    private final Utilities utils;
    private final Scanner sc;
    private final T twit;
    private final User user;

    public ObserveTwitController(SessionFactory sessionFactory, T twit, Integer userId) {
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        utils = new Utilities(sessionFactory);
        sc = new Scanner(System.in);
        this.twit = twit;

        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        user = userService.findById(userId);
        twitLikingController = new LikingController<>(sessionFactory,twit,user);
    }

    public void viewTwit() {
        label:
        while (true) {
            ArrayList<String> menu = new ArrayList<>();
            menu.add(twit.toString());
            menu.add("L: Like|D: Dislike|C: Comments|R: Reply|N: Do Nothing/Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (opt) {
                case "L":
                    twitLikingController.like();
                    break;
                case "D":
                    twitLikingController.dislike();
                    break;
                case "C":
                    viewComments(twit);
                case "R":
                    commentOn(twit);
                    break;
                case "N":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    private void viewComments(Twit twit) {
        ArrayList<String> comments = new ArrayList<>();
        twit
                .getComments()
                .stream()
                .filter(comment -> !comment.getIsDeleted())
                .forEach(comment -> comments.add(comment.toString()));
        utils.menuViewer(comments);
    }



    private void commentOn(Twit twit) {
        Comment comment = new Comment();
        System.out.println("Comment: ");
        comment.setContent(utils.contentReceiver());
        comment.setOwnerTwit(twit);
        comment.setUser(user);
        Comment newComment = commentService.insert(comment);
        System.out.println("New Comment Added with ID: " + newComment.getId());
    }
}
