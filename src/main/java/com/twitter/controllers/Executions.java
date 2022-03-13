package com.twitter.controllers;

import com.twitter.Utilities;
import com.twitter.models.user.User;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.UserServiceImpl;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Executions {
    static SessionFactory sessionFactory = SessionFactorySingleton.getInstance();
    static Utilities utils = new Utilities();
    static Scanner sc = new Scanner(System.in);
    static UserServiceImpl userService = new UserServiceImpl(new UsersRepoImpl(sessionFactory));

    public static void main(String[] args) {
        label:
        while (true) {
            System.out.println("Welcome to Twitter App :))");
            System.out.println("Enter L/l or S/s to login or sign up: ");
            String inp = sc.nextLine().toUpperCase(Locale.ROOT);
            switch (inp) {
                case "L":
                    loginSection();
                    break;
                case "S":
                    signUpSection();
                    break;
                case "E":
                    break label;
                default:
                    System.out.println("Wrong option.");
                    break;
            }
        }
    }

    public static void loginSection() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User userToLogin = auth(username,password);
        if (userToLogin != null) {
            UserController userController = new UserController(sessionFactory,userToLogin.getId());
            userController.entry();
        } else {
            utils.printRed("Wrong username/password");
        }
    }

    public static User auth(String username,String password) {
        User userToLogin = userService.findByUsername(username);
        return (userToLogin != null && userToLogin.getPassword().equals(password)) ? userToLogin : null;
    }

    public static void signUpSection() {
        String[] initials = initialReceiver();
        User singUpUser = new User(0, initials[0], initials[1], initials[2], initials[3], initials[4]);
        User newUser = userService.insert(singUpUser);
        utils.printGreen("New User made with ID: " + newUser.getId());
    }

    public static String[] initialReceiver(){
        System.out.print("First Name: "); String firstName = sc.nextLine();
        System.out.print("Last Name: "); String lastName = sc.nextLine();
        String username = utils.usernameReceiver();
        String password = utils.passwordReceiver();
        String email = utils.emailReceiver();
        return new String[]{firstName,lastName,username,password,email};
    }
}
