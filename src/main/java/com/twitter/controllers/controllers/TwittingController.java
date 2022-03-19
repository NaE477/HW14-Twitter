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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwittingController {
    private final TwitService twitService;
    private final CommentService commentService;
    private final User user;
    private final Scanner sc;
    private final Utilities utils;

    public TwittingController(Integer userId) {
        twitService = new TwitServiceImpl(new TwitRepoImpl());
        commentService = new CommentServiceImpl(new CommentRepoImpl());
        sc = new Scanner(System.in);
        utils = new Utilities();

        UserService userService = new UserServiceImpl(new UsersRepoImpl());
        user = userService.findById(userId);
    }


    public void entry() {
        label:
        while (true) {
            List<Twit> usersTwits = twitService.findAllByUser(user);
            System.out.println("Welcome to your twits section");
            utils.iterateThrough(usersTwits);
            System.out.println("Choose an options: ");
            ArrayList<String> opts = new ArrayList<>();
            opts.add("1-New Twit");
            opts.add("2-Edit Twit");
            opts.add("3-Edit Comment");
            opts.add("4-View/Reply Comments of your twit");
            opts.add("0-Exit");
            utils.menuViewer(opts);
            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    newTwit();
                    break;
                case "2":
                    controlEditTwit();
                    break;
                case "3":
                    controlEditComment();
                case "4":
                    controlViewComments();
                    break;
                case "0":
                    break label;
                default:
                    utils.printRed("Wrong option");
                    break;
            }
        }
    }

    private void newTwit() {
        System.out.println("Twit: ");
        String content = utils.contentReceiver();
        Twit newTwit = new Twit(content, user);
        Twit insertTwit = twitService.insert(newTwit);
        utils.printGreen("New Twit Added with ID: " + insertTwit.getId(), 1000);
    }

    private void controlEditTwit() {
        List<Twit> twitsByUser = twitService.findAllByUser(user);
        utils.iterateThrough(twitsByUser);
        System.out.println("Enter twit ID: ");
        Integer twitId = utils.intReceiver();
        var twitToEdit = utils.findIdInCollection(twitsByUser, twitId);
        if (twitToEdit != null) editTwit(twitToEdit);
        else System.out.println("Wrong ID");
    }

    private void editTwit(Twit twit) {
        System.out.println("Enter new Content for the twit: ");
        String newContent = utils.contentReceiver();
        twit.setContent(newContent);
        Twit editedTwit = twitService.update(twit);
        System.out.println("Twit edited with ID: " + editedTwit.getId());
    }

    private void controlEditComment() {
        List<Comment> commentsByUser = commentService.findAllByUser(user);
        utils.iterateThrough(commentsByUser);
        System.out.println("Enter comment ID: ");
        Integer commentId = utils.intReceiver();
        var commentToEdit = utils.findIdInCollection(commentsByUser,commentId);
        if(commentToEdit != null) editComment(commentToEdit);
        else System.out.println("Wrong ID");
    }

    private void editComment(Comment comment) {
        System.out.println("Enter new Content for the comment: ");
        String newContent = utils.contentReceiver();
        comment.setContent(newContent);
        Comment editedComment = commentService.update(comment);
        System.out.println("Comment edited with ID: " + editedComment.getId());
    }

    private void controlViewComments() {
        List<Twit> twitsByUser = twitService.findAllByUser(user);
        System.out.println("Enter Your Twit ID: ");
        Integer twitId = utils.intReceiver();
        var twitToViewComments = utils.findIdInCollection(twitsByUser,twitId);
        if(twitToViewComments != null) {
            viewComments(twitToViewComments);
        } else System.out.println("Wrong ID");
    }

    private void viewComments(Twit twit) {
        List<Comment> comments = commentService.findAllByTwit(twit);
        utils.iterateThrough(comments);
        System.out.println("Enter Comment ID to reply or 0 to Exit: ");
        int commentId = utils.intReceiver();
        Comment commentToObserve = utils.findIdInCollection(comments,commentId);
        if(commentId != 0) {
            if (commentToObserve != null) {
                ObserveCommentController<Comment> observeCommentController = new ObserveCommentController<>(commentToObserve, user.getId());
                observeCommentController.viewComment();
            } else System.out.println("Wrong ID");
        }
    }


}
