package com.twitter.controllers;

import com.twitter.Utilities;
import com.twitter.models.twits.Comment;
import com.twitter.models.twits.Twit;
import com.twitter.models.user.User;
import com.twitter.services.CommentServiceImpl;
import com.twitter.services.TwitServiceImpl;
import com.twitter.services.UserServiceImpl;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final User user;
    private final UserServiceImpl userService;
    private final TwitServiceImpl twitService;
    private final CommentServiceImpl commentService;
    private final Utilities utils;
    private final Scanner sc = new Scanner(System.in);

    public UserController(SessionFactory sessionFactory,User user) {
        userService = new UserServiceImpl();
        twitService = new TwitServiceImpl();
        commentService = new CommentServiceImpl();
        this.user = user;
        utils = new Utilities();
    }

    public void entry() {
        label:
        while (true) {
            System.out.println("Welcome To User Section.\nChoose an Option: ");

            ArrayList<String> menu = new ArrayList<>();
            menu.add("1-Twits");
            menu.add("2-Your Twits");
            menu.add("3-Your Profile");
            menu.add("4-Search for user");
            menu.add("0-Exit");

            utils.menuViewer(menu);

            String inp = sc.nextLine();
            switch (inp) {
                case "1":
                    twits();
                    break;
                case "2":
                    userTwits();
                    break;
                case "3":
                    profile();
                    break;
                case "4":
                    searchUser();
                case "0":
                    break label;
            }
        }
    }

    public void twits() {
        List<Twit> twits = twitService.findAll();
        label:
        while (true) {
            System.out.println("Welcome to twits sections");

            System.out.println("Choose an option: ");
            ArrayList<String> menu = new ArrayList<>();
            menu.add("1-View Twit");
            menu.add("2-Put comment under a twit");
            menu.add("3-view all twits");
            menu.add("0-Exit");
            utils.menuViewer(menu);
            String opt = sc.nextLine();
            switch (opt) {
                case "1": {
                    System.out.println("Enter twit ID: ");
                    Integer twitId = utils.intReceiver();
                    Twit toCheck = twitService.findById(twitId);
                    if (toCheck != null) {
                        utils.printGreen(toCheck.toString());
                        List<Comment> comments = commentService.findAllByTwit(toCheck);
                        utils.printRed("---------------------------------Comments-----------------------------------");
                        utils.iterateThrough(comments);
                    } else utils.printRed("Wrong ID");
                    break;
                }
                case "2": {
                    System.out.println("Enter twit ID: ");
                    Integer twitId = utils.intReceiver();
                    Twit toComment = twitService.findById(twitId);
                    if (toComment != null) {
                        commentOn(toComment);
                    } else utils.printRed("Wrong ID");
                    break;
                }
                case "3": {
                    utils.iterateThrough(twits);
                    break;
                }
                case "0":
                    break label;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
    }


    public void userTwits() {
        label:
        while (true) {
            List<Twit> usersTwits = twitService.findTwitsByUser(user);
            System.out.println("Welcome to your twits section");
            utils.iterateThrough(usersTwits);
            System.out.println("Choose an options: ");
            ArrayList<String> opts = new ArrayList<>();
            opts.add("1-New Twit");
            opts.add("2-Edit Twit");
            opts.add("3-View Comments of a twit");
            opts.add("0-Exit");
            utils.menuViewer(opts);
            String opt = sc.nextLine();
            switch (opt) {
                case "1":
                    newTwit();
                    break;
                case "2":
                    if (usersTwits.size() > 0) {
                        System.out.println("Enter twit ID: ");
                        Integer twitId = utils.intReceiver();
                        Twit toEditTwit = twitService.findById(twitId);
                        if (toEditTwit != null && usersTwits.contains(toEditTwit)) {
                            editTwit(toEditTwit);
                        } else System.out.println("Wrong ID");
                    } else System.out.println("No Twits by you yet");
                    break;
                case "3":
                    if (usersTwits.size() > 0) {
                        System.out.println("Enter twit ID: ");
                        Integer twitId = utils.intReceiver();
                        Twit toViewComments = twitService.findById(twitId);
                        if (toViewComments != null && usersTwits.contains(toViewComments)) {
                            viewComments(toViewComments);
                        } else System.out.println("Wrong ID");
                    }
                    break;
                case "0":
                    break label;
                default:
                    utils.printRed("Wrong option");
                    break;
            }
        }
    }

    public void newTwit() {
        System.out.println("Twit: ");
        String content = utils.contentReceiver();
        Twit newTwit = new Twit(content, user);
        Twit insertTwit = twitService.insert(newTwit);
        utils.printGreen("\n\n\n\nNew Twit Added with ID: " + insertTwit.getId(), 1000);
    }

    public void commentOn(Twit toCommentOn) {
        System.out.println("Comment: ");
        String content = utils.contentReceiver();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setOwnerTwit(toCommentOn);
        comment.setUser(user);
        Comment newComment = commentService.insert(comment);
        System.out.println("New Comment Added with ID: " + newComment.getId());
    }

    public void editTwit(Twit twit) {
        System.out.println("Enter new Content for the twit: ");
        String newContent = utils.contentReceiver();
        twit.setContent(newContent);
        Twit editedTwit = twitService.update(twit);
        System.out.println("Twit edited with ID: " + editedTwit.getId());
    }

    public void editComment(Comment comment) {
        System.out.println("Enter new Content for the twit: ");
        String newContent = utils.contentReceiver();
        comment.setContent(newContent);
        Comment editedComment = commentService.update(comment);
        System.out.println("Twit edited with ID: " + editedComment.getId());
    }

    public void viewComments(Twit twit) {
        twit.setComments(commentService.findAllByTwit(twit));
        utils.iterateThrough(twit.getComments());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void profile() {
        label:
        while (true) {
            System.out.println("Welcome to your profile");
            System.out.println("What do you want to change:");
            ArrayList<String> opts = new ArrayList<>();
            opts.add("1-Change firstname and lastname");
            opts.add("2-Change password");
            opts.add("3-View Profile");
            opts.add("4-Delete Account");
            opts.add("0-Exit");
            utils.menuViewer(opts);
            String opt = sc.nextLine();
            switch (opt) {
                case "1": {
                    System.out.println("firstname: ");
                    String newFirstName = sc.nextLine();
                    System.out.println("lastname: ");
                    String newLastName = sc.nextLine();
                    user.setFirstname(newFirstName);
                    user.setLastname(newLastName);
                    User changed = userService.update(user);
                    if (changed != null) {
                        utils.printGreen("Changes saved");
                    } else System.out.println("Something went wrong with changing");
                    break;
                }
                case "2": {
                    String password = utils.passwordReceiver();
                    user.setPassword(password);
                    User changed = userService.update(user);
                    if (changed != null) {
                        utils.printGreen("Changes saved");
                    } else System.out.println("Something went wrong with changing");
                    break;
                }
                case "3": {
                    utils.printGreen(user.toString());
                }
                case "4": {
                    System.out.println("Sure? (Y/N)");
                    String yOrN = sc.nextLine();
                    if (yOrN.equals("y")) {
                        userService.delete(user);
                        System.out.println("Goodbye");
                        break label;
                    } else System.out.println("Cancelled.");
                }
                case "0":
                    break label;
                default:
                    System.out.println("wrong option");
                    break;
            }
        }
    }

    public void searchUser() {
        whileLabel:
        while (true) {
            System.out.println("Enter username to search: ");
            String toSearch = sc.nextLine();
            List<User> users = userService.searchUsername(toSearch);
                    utils.iterateThrough(users);

            System.out.println("Enter Username you want to check: ");
            String usernameToView = sc.nextLine();
            User userToView = userService.findByUsername(usernameToView);
            if (userToView != null) {
                System.out.println("1-View Twits");
                System.out.println("2-View Comments");
                System.out.println("3-View Profile");
                System.out.println("0-Exit");
                String opt = sc.nextLine();
                switch (opt) {
                    case "1":
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
                        break;
                    case "2":
                        List<Comment> comments = commentService.findAllByUser(userToView);
                        utils.iterateThrough(comments);
                        break;
                    case "3":
                        utils.printGreen(userToView.toString());
                        break;
                    case "0":
                        break whileLabel;
                }
            } else {
                utils.printRed("Wrong ID");
                break;
            }
        }
    }
}
