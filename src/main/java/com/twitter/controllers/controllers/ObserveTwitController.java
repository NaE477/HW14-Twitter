package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.*;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.LikeRepoImpl;
import com.twitter.repos.impls.ReplyRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.LikeServiceImpl;
import com.twitter.services.impls.ReplyServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.LikeService;
import com.twitter.services.interfaces.ReplyService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ObserveTwitController<T extends BaseTwit> {
    private final CommentingController commentingController;
    private final CommentService commentService;
    private final ReplyService replyService;
    private final LikeService likeService;
    private final Utilities utils;
    private final Scanner sc;
    private final T twit;
    private final User user;

    public ObserveTwitController(SessionFactory sessionFactory, T twit, Integer userId) {
        commentingController = new CommentingController(sessionFactory,userId);
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        replyService = new ReplyServiceImpl(new ReplyRepoImpl(sessionFactory));
        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        likeService = new LikeServiceImpl(new LikeRepoImpl(sessionFactory));
        utils = new Utilities(sessionFactory);
        sc = new Scanner(System.in);
        this.twit = twit;
        user = userService.findById(userId);
    }

    public void viewTwit() {
        label:
        while (true) {
            ArrayList<String> menu = new ArrayList<>();
            menu.add(twit.toString());
            menu.add("L: Like|D: Dislike|C: Comment|N: Do nothing");
            utils.menuViewer(menu);
            String opt = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (opt) {
                case "L":
                    like();
                    break;
                case "D":
                    dislike();
                    break;
                case "C":
                    comment();
                    break;
                case "N":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    private void like() {
        if (!liked()) {
            Like newLike = new Like(twit,user);
            likeService.insert(newLike);
        } else System.out.println("Already liked this twit.");
    }

    private void dislike() {
        if (liked()) {
            Like toRemove = likeService.findByTwitAndUser(twit,user);
            if (toRemove != null) likeService.delete(toRemove);
        } else System.out.println("Haven't Liked this twit.");
    }

    private void comment() {
        if (twit instanceof Twit) commentOn((Twit) twit);
        else if (twit instanceof Comment) replyOn((Comment) twit);
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

    private void replyOn(Comment comment) {
        Reply reply = new Reply();
        System.out.println("Reply: ");
        reply.setContent(utils.contentReceiver());
        reply.setComment(comment);
        reply.setUser(user);
        Reply newReply = replyService.insert(reply);
        System.out.println("New Reply Added with ID: " + newReply.getId());
    }

    private Boolean liked() {
        return twit.getLikes()
                .stream()
                .map(Like::getLiker)
                .filter(liker -> liker.equals(user))
                .collect(Collectors.toList())
                .contains(user);
    }
}
