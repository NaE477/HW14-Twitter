package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Like;
import com.twitter.models.twits.Reply;
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

public class ObserveCommentController<T extends Comment> {
    private final LikingController<Comment> commentLikingController;
    private final ReplyService replyService;
    private final Utilities utils;
    private final Scanner sc;
    private final T comment;
    private final User user;

    public ObserveCommentController(SessionFactory sessionFactory, T comment, Integer userId) {
        replyService = new ReplyServiceImpl(new ReplyRepoImpl(sessionFactory));
        utils = new Utilities(sessionFactory);
        sc = new Scanner(System.in);
        this.comment = comment;

        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        user = userService.findById(userId);
        commentLikingController = new LikingController<>(sessionFactory,comment,user);
    }

    public void viewTwit() {
        label:
        while (true) {
            ArrayList<String> menu = new ArrayList<>();
            menu.add(comment.toString());
            menu.add("L: Like|D: Dislike|C: Comments|R: Reply|N: Do Nothing/Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (opt) {
                case "L":
                    commentLikingController.like();
                    break;
                case "D":
                    commentLikingController.dislike();
                    break;
                case "C":
                    viewReplies(comment);
                case "R":
                    replyOn(comment);
                    break;
                case "N":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }



    private void viewReplies(Comment comment) {
        ArrayList<String> replies = new ArrayList<>();
        comment.getReplies().stream().filter(reply -> !reply.getIsDeleted()).forEach(reply -> replies.add(reply.toString()));
        utils.menuViewer(replies);
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

}
