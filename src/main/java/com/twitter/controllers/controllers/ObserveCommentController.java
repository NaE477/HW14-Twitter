package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Like;
import com.twitter.models.twits.Reply;
import com.twitter.models.user.User;
import com.twitter.repos.impls.LikeRepoImpl;
import com.twitter.repos.impls.ReplyRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.LikeServiceImpl;
import com.twitter.services.impls.ReplyServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.LikeService;
import com.twitter.services.interfaces.ReplyService;
import com.twitter.services.interfaces.UserService;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ObserveCommentController<T extends Comment> {
    private final LikeService likeService;
    private final ReplyService replyService;
    private final Utilities utils;
    private final Scanner sc;
    private final T comment;
    private final User user;

    public ObserveCommentController(T comment, Integer userId) {
        replyService = new ReplyServiceImpl(new ReplyRepoImpl());
        likeService = new LikeServiceImpl(new LikeRepoImpl());
        utils = new Utilities();
        sc = new Scanner(System.in);
        this.comment = comment;

        UserService userService = new UserServiceImpl(new UsersRepoImpl());
        user = userService.findById(userId);
    }

    public void viewComment() {
        label:
        while (true) {
            ArrayList<String> menu = new ArrayList<>();
            menu.add(comment.toString());
            menu.add("L: Like/Dislike|C: Comments|R: Reply|N: Do Nothing/Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (opt) {
                case "L":
                    likeDislike();
                    break;
                case "C":
                    viewReplies();
                    break;
                case "R":
                    replyOn();
                    break;
                case "N":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }

    private void likeDislike() {
        Set<User> likers = comment.getLikes().stream().map(Like::getLiker).collect(Collectors.toSet());
        if (likers.contains(user)) {
            dislike();
        } else {
            like();
        }
    }

    private void like() {
        Like like = new Like(comment,user);
        likeService.insert(like);
        comment.getLikes().add(like);
        System.out.println("Liked");
    }

    private void dislike() {
        Like toRemove = likeService.findByTwitAndUser(comment,user);
        likeService.delete(toRemove);
        comment.getLikes().remove(toRemove);
        System.out.println("Unliked");
    }

    private void viewReplies() {
        ArrayList<String> replies = new ArrayList<>();
        comment.setReplies(replyService.findAllByComment(comment));
        comment.getReplies().stream().filter(reply -> !reply.getIsDeleted()).forEach(reply -> replies.add(reply.toString()));
        utils.menuViewer(replies);
    }


    private void replyOn() {
        Reply reply = new Reply();
        System.out.println("Reply: ");
        reply.setContent(utils.contentReceiver());
        reply.setComment(comment);
        reply.setUser(user);
        Reply newReply = replyService.insert(reply);
        comment.getReplies().add(newReply);
        System.out.println("New Reply Added with ID: " + newReply.getId());
    }

}
