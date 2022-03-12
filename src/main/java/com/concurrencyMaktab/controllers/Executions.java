package com.concurrencyMaktab.controllers;

import com.concurrencyMaktab.Utilities;
import com.concurrencyMaktab.models.User;
import com.concurrencyMaktab.services.UserService;

import java.sql.Connection;
import java.util.Locale;
import java.util.Scanner;

public class Executions {
    static Connection connection = ConClass.getInstance().getConnection();
    static Utilities utils = new Utilities(connection);
    static Scanner sc = new Scanner(System.in);
    static UserService userService = new UserService(connection);

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
        if (userService.login(username, password)) {
            User user = userService.findByUsername(username);
            UserController userController = new UserController(user);
            userController.entry();
        } else {
            utils.printRed("Wrong username/password");
        }
    }

    public static void signUpSection() {
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();
        String username = utils.usernameReceiver();
        String password = utils.passwordReceiver();
        String email = utils.emailReceiver();
        User singUpUser = new User(0, firstName, lastName, username, password, email);
        Integer newId = userService.singUp(singUpUser);
        utils.printGreen("New User made with ID: " + newId);
    }
}
