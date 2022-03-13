package com.twitter;


import com.twitter.controllers.SessionFactorySingleton;
import com.twitter.repos.impls.UsersRepoImpl;
import com.twitter.services.impls.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utilities {

    private final Scanner scanner = new Scanner(System.in);
    private final UserServiceImpl userService;

    public Utilities() {
        UsersRepoImpl usersRepo = new UsersRepoImpl(SessionFactorySingleton.getInstance());
        userService = new UserServiceImpl(usersRepo);
    }

    public void menuViewer(ArrayList<String> options) {
        System.out.println("\033[0;32m" + "           ----------------------------------------------");
        for (String opt : options) {
            System.out.println("           |" + opt);
        }
        System.out.println("           ----------------------------------------------" + "\033[0m");
        System.out.print("           Option: ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String usernameReceiver() {
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            if (userService.findByUsername(username) != null) {
                System.out.println("This username Already Exists! Try another one: ");
            } else {
                return username;
            }
        }
    }

    public String passwordReceiver() {
        return regexAdder(".{4,}", "Password", "At Least 4 Characters.");
    }

    public String emailReceiver() {
        while (true) {
            String email = regexAdder("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", "Email", "Example: naeim@gmail.com");
            if (userService.findByEmail(email) != null) {
                System.out.println("Email address already exists. Try another one.");
            } else return email;
        }
    }

    public String contentReceiver(){
        while (true){
            String comment = scanner.nextLine();
            if(comment.length() > 280){
                System.out.println("Comment Limit is 280 characters");
            } else return comment;
        }
    }

    public String regexAdder(String regex, String tag, String additionalInfo) {
        while (true) {
            System.out.print(tag + "(" + additionalInfo + "): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (checkRegex(input, regex)) {
                return input;
            } else {
                System.out.println("Wrong " + tag + " Format. Enter a Correct " + tag + " Format:");
            }
        }
    }

    public boolean checkRegex(String input, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    public int intReceiver() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("You should enter a number here: ");
            }
        }
    }

    public Integer threeDigitsReceiver() {
        while (true) {
            int input = intReceiver();
            if (input < 100 || input > 999) {
                System.out.println("Enter a three digit number.");
            } else return input;
        }
    }

    public <T> void iterateThrough(List<T> lists) {
        if (lists != null && lists.size() > 0) {
            for (T object : lists) {
                if (object != null)
                    System.out.println("\u001B[32m" + object + "\u001B[0m");
            }
        } else {
            printYellow("This list is empty.");
        }
    }

    public int yearReceiver() {
        while (true) {
            System.out.println("Year: ");
            int year = intReceiver();
            if (year > 2030 || year < 2020) System.out.println("Enter a year between 2020 and 2030");
            else return year;
        }
    }

    public int monthReceiver() {
        while (true) {
            System.out.print("Month: ");
            int monthNum = intReceiver();
            if (monthNum < 1 || monthNum > 12) {
                System.out.println("Enter a number between 1 and 12.");
            } else {
                return monthNum;
            }
        }
    }

    public int dayReceiver(int month) {
        while (true) {
            System.out.print("Day: ");
            int day = intReceiver();
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31 || day < 0) {
                    System.out.println("Enter a number between 1 and 31 for this month.");
                } else return day;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30 || day < 0) {
                    System.out.println("Enter a number between 1 and 30 for this month.");
                } else return day;
            } else if (month == 2) {
                if (day > 28 || day < 0) {
                    System.out.println("Enter a number between 1 and 28 for this month.");
                } else return day;
            }
        }
    }

    public void printRed(String input) {
        try {
            String ANSI_RED = "\u001B[31m";
            System.out.print("------------------------------\n" + ANSI_RED + input + ANSI_RESET + "\n------------------------------\n");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printGreen(String input) {
        try {
            String ANSI_GREEN = "\u001B[32m";
            System.out.print("------------------------------\n" + ANSI_GREEN + input + ANSI_RESET + "\n------------------------------\n");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void printGreen(String input,Integer waitTime){
        try {
            String ANSI_GREEN = "\u001B[32m";
            System.out.print("------------------------------\n" + ANSI_GREEN + input + ANSI_RESET + "\n------------------------------\n");
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printYellow(String input) {
        try {
            String ANSI_YELLOW = "\u001B[33m";
            System.out.print("------------------------------\n" + ANSI_YELLOW + input + ANSI_RESET + "\n------------------------------\n");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final String ANSI_RESET = "\u001B[0m";


}
