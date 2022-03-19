package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.twits.BaseTwit;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.twits.TwitProxy;
import com.twitter.models.user.User;
import com.twitter.repos.impls.CommentRepoImpl;
import com.twitter.repos.impls.ReplyRepoImpl;
import com.twitter.repos.impls.TwitRepoImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.CommentServiceImpl;
import com.twitter.services.impls.ReplyServiceImpl;
import com.twitter.services.impls.TwitServiceImpl;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.services.interfaces.CommentService;
import com.twitter.services.interfaces.ReplyService;
import com.twitter.services.interfaces.TwitService;
import com.twitter.services.interfaces.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchingController {
    private final UserService userService;
    private final TwitService twitService;
    private final CommentService commentService;
    private final ReplyService replyService;
    private final User user;

    private final Utilities utils;
    private final Scanner sc;

    public SearchingController(Integer userId) {
        userService = new UserServiceImpl(new UsersRepoImpl());
        twitService = new TwitServiceImpl(new TwitRepoImpl());
        commentService = new CommentServiceImpl(new CommentRepoImpl());
        replyService = new ReplyServiceImpl(new ReplyRepoImpl());
        user = userService.findById(userId);
        utils = new Utilities();
        sc = new Scanner(System.in);
    }

    public void entry() {
        label:
        while (true) {
            List<User> users = findUsersByUsername();
            utils.iterateThrough(users);
            if (users.size() > 0) {
                System.out.println("Enter Username you want to check or 0 to exit: ");
                String usernameToView = sc.nextLine();
                User userToView = userService.findByUsername(usernameToView);
                if (!usernameToView.equals("0") && userToView != null) {
                    System.out.println("1-View Twits");
                    System.out.println("2-View Comments");
                    System.out.println("2-View All Activity");
                    System.out.println("4-View Profile");
                    System.out.println("5-Follow/Unfollow");
                    System.out.println("0-Exit");
                    String opt = sc.nextLine();
                    switch (opt) {
                        case "1":
                            viewUserTwits(userToView);
                            break;
                        case "2":
                            viewUserComments(userToView);
                            break;
                        case "3":
                            viewAllActivityTwits(userToView);
                        case "4":
                            utils.printGreen(userToView.toString());
                            break;
                        case "5":
                            followUnfollow(userToView);
                        case "0":
                            break label;
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

    private void viewAllActivityTwits(User userToView) {
        List<BaseTwit> wholeTwits = twitService.findAllByUser(userToView).stream().map(twit -> (BaseTwit) twit).collect(Collectors.toList());
        wholeTwits.addAll(commentService.findAllByUser(userToView).stream().map(comment -> (BaseTwit) comment).collect(Collectors.toList()));
        wholeTwits.addAll(commentService.findAllByUser(userToView).stream().map(comment -> (BaseTwit) comment).collect(Collectors.toList()));
        wholeTwits.addAll(replyService.findAllByUser(userToView).stream().map(comment -> (BaseTwit) comment).collect(Collectors.toList()));
        if (wholeTwits.size() > 0)
            wholeTwits.stream().sorted(Comparator.comparing(BaseTwit::getTwitTime)).forEach(System.out::println);
        else System.out.println("No twits by the user");
    }

    private void viewUserTwits(User userToView) {
        List<Twit> twits = twitService.findAllByUser(userToView)
                .stream()
                .filter(twit -> !twit.getIsDeleted())
                .sorted(Comparator.comparing(BaseTwit::getTwitTime))
                .collect(Collectors.toList());
        utils.iterateThrough(twits);
        if (twits.size() > 0) {
            System.out.println("Choose Twit ID: ");
            Integer twitId = utils.intReceiver();
            TwitProxy twitToViewProxy = new TwitProxy(twitId,false);
            if (twitToViewProxy.getTwit() != null && twits.contains(twitToViewProxy.getTwit())) {
                ObserveTwitController<TwitProxy> observeTwitController = new ObserveTwitController<>(twitToViewProxy, user.getId());
                observeTwitController.viewTwit();
            } else System.out.println("Wrong ID.");
        } else utils.printRed("User has no twits yet");
    }

    private void viewUserComments(User userToView) {
        List<Comment> comments = commentService.findAllByUser(userToView)
                .stream()
                .filter(comment -> !comment.getIsDeleted())
                .sorted(Comparator.comparing(BaseTwit::getTwitTime))
                .collect(Collectors.toList());
        utils.iterateThrough(comments);
        if (comments.size() > 0) {
            System.out.println("Choose Comment ID: ");
            Integer commentId = utils.intReceiver();
            Comment commentToView = commentService.findById(commentId);
            if (commentToView != null && comments.contains(commentToView)) {
                ObserveCommentController<Comment> observeTwitController = new ObserveCommentController<>(commentToView, user.getId());
                observeTwitController.viewComment();
            } else System.out.println("Wrong ID.");
        } else utils.printRed("User has no twits yet");
    }

    private List<User> findUsersByUsername() {
        System.out.println("Enter username to search: ");
        String toSearch = sc.nextLine();
        return userService.searchUsername(toSearch);
    }

    private void followUnfollow(User toFollow) {
        Set<User> followers = toFollow.getFollowers();
        if (followers.contains(user)) {
            followers.remove(user);
            System.out.println("Unfollowed");
        } else {
            followers.add(user);
            System.out.println("Followed");
        }
        toFollow.setFollowers(followers);
        userService.update(toFollow);
    }
}
