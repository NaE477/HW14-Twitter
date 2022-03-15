package com.twitter.controllers.controllers;

import com.twitter.controllers.Utilities;
import com.twitter.models.user.User;
import com.twitter.repos.impls.*;
import com.twitter.services.impls.*;
import com.twitter.services.interfaces.*;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class ProfileController {
    private final UserService userService;
    private final Scanner sc;
    private final Utilities utils;
    private final User user;

    public ProfileController(SessionFactory sessionFactory, Integer userId) {
        userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));
        sc = new Scanner(System.in);
        utils = new Utilities(sessionFactory);
        user = userService.findById(userId);
    }

    public void entry() {
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
                case "1":
                    changeInitials();
                    break;
                case "2":
                    changePassword();
                    break;
                case "3":
                    utils.printGreen(user.toString());
                    break;
                case "4":
                    deleteAccount();
                    break;
                case "0":
                    break label;
                default:
                    System.out.println("wrong option");
                    break;
            }
        }
    }

    private void changeInitials() {
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
    }

    private void changePassword() {
        String password = utils.passwordReceiver();
        user.setPassword(password);
        User changed = userService.update(user);
        if (changed != null) {
            utils.printGreen("Changes saved");
        } else System.out.println("Something went wrong with changing");
    }

    private void deleteAccount() {
        System.out.println("Sure? (Y/N)");
        String yOrN = sc.nextLine();
        if (yOrN.equals("y")) {
            userService.delete(user);
            System.out.println("Goodbye");
        } else System.out.println("Cancelled.");
    }
}
