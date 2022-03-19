package com.twitter.controllers.controllers;

import com.twitter.services.interfaces.UserService;
import com.twitter.services.impls.UserServiceImpl;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.controllers.Utilities;
import com.twitter.models.user.User;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class UserController {
    private final User user;
    private final Utilities utils;
    private final Scanner sc = new Scanner(System.in);

    public UserController(Integer userId) {
        UserService userService = new UserServiceImpl(new UsersRepoImpl());
        this.user = userService.findById(userId);
        utils = new Utilities();
    }

    public void entry() {
        label:
        while (true) {
            System.out.println("Welcome To User Section.\nChoose an Option: ");

            ArrayList<String> options = new ArrayList<>();
            options.add("1-Twitter Feed");
            options.add("2-Your Twits");
            options.add("3-Your Profile");
            options.add("4-Search for user");
            options.add("0-Exit");

            utils.menuViewer(options);

            String inp = sc.nextLine();
            switch (inp) {
                case "1":
                    feed();
                    break;
                case "2":
                    twitting();
                    break;
                case "3":
                    profile();
                    break;
                case "4":
                    searchUser();
                    break;
                case "0":
                    break label;
            }
        }
    }


    public void feed() {
        FeedController feedController = new FeedController(user.getId());
        feedController.entry();
    }

    private void twitting() {
        TwittingController twittingController = new TwittingController(user.getId());
        twittingController.entry();
    }

    private void profile() {
        ProfileController profileController = new ProfileController(user.getId());
        profileController.entry();
    }

    private void searchUser() {
        SearchingController searchingController = new SearchingController(user.getId());
        searchingController.entry();
    }
}
