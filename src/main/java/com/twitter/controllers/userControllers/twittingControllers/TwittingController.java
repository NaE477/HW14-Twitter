package com.twitter.controllers.userControllers.twittingControllers;

import com.twitter.Utilities;
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

    public TwittingController(SessionFactory sessionFactory, Integer userId) {
        twitService = new TwitServiceImpl(new TwitRepoImpl(sessionFactory));
        commentService = new CommentServiceImpl(new CommentRepoImpl(sessionFactory));
        sc = new Scanner(System.in);
        utils = new Utilities(sessionFactory);

        UserService userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        user = userService.findById(userId);
    }


    public void entry() {
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

    public void editTwit(Twit twit) {
        System.out.println("Enter new Content for the twit: ");
        String newContent = utils.contentReceiver();
        twit.setContent(newContent);
        Twit editedTwit = twitService.update(twit);
        System.out.println("Twit edited with ID: " + editedTwit.getId());
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
}
