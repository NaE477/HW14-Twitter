package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.*;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.LikeRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.LikeServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.LikeService;
import com.twitter.services.interfaces.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class ObserveTwitController<T extends TwitProxy> {
    private final LikeService likeService;
    private final CommentService commentService;
    private final Utilities utils;
    private final Scanner sc;
    private final T twitProxy;
    private final User user;

    public ObserveTwitController(T twit, Integer userId) {
        commentService = new CommentServiceImpl(new CommentRepoImpl());
        likeService = new LikeServiceImpl(new LikeRepoImpl());
        utils = new Utilities();
        sc = new Scanner(System.in);
        this.twitProxy = twit;

        UserService userService = new UserServiceImpl(new UsersRepoImpl());
        user = userService.findById(userId);
    }

    public void viewTwit() {
        label:
        while (true) {
            ArrayList<String> menu = new ArrayList<>();
            menu.add(twitProxy.getTwit().toString());
            menu.add("L: Like/Dislike|C: Comments|R: Reply|N: Do Nothing/Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (opt) {
                case "L":
                    likeDislike();
                    break;
                case "C":
                    viewComments();
                    break;
                case "R":
                    commentOn();
                    break;
                case "N":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    private void viewComments() {
        twitProxy.initializeComments();
        List<Comment> comments = new ArrayList<>();
        twitProxy.getTwit().getComments()
                .stream()
                .filter(comment -> !comment.getIsDeleted())
                .forEach(comments::add);
        utils.iterateThrough(comments);
        System.out.println("Enter comment ID to reply to it or 0 to exit: ");
        int commentId = utils.intReceiver();
        Comment toReply = utils.findIdInCollection(comments,commentId);
        if (commentId != 0) {
            if (toReply != null) {
                ObserveCommentController<Comment> commentObserveCommentController = new ObserveCommentController<>(toReply,user.getId());
                commentObserveCommentController.viewComment();
            }
        }
    }

    private void likeDislike() {
        Set<User> likers = twitProxy.getTwit().getLikes().stream().map(Like::getLiker).collect(Collectors.toSet());
        if (likers.contains(user)) {
            dislike();
        } else {
            like();
        }
    }

    private void like() {
        Like like = new Like(twitProxy.getTwit(),user);
        likeService.insert(like);
        twitProxy.getTwit().getLikes().add(like);
        System.out.println("Liked");
    }

    private void dislike() {
        Like toRemove = likeService.findByTwitAndUser(twitProxy.getTwit(),user);
        likeService.delete(toRemove);
        twitProxy.getTwit().getLikes().remove(toRemove);
        System.out.println("Unliked");
    }

    private void commentOn() {
        Comment comment = new Comment();
        System.out.println("Comment: ");
        comment.setContent(utils.contentReceiver());
        comment.setOwnerTwit(twitProxy.getTwit());
        comment.setUser(user);
        Comment newComment = commentService.insert(comment);
        twitProxy.getTwit().getComments().add(newComment);
        System.out.println("New Comment Added with ID: " + newComment.getId());
    }
}
