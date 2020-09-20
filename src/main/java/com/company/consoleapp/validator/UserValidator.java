package com.company.consoleapp.validator;

public class UserValidator {
    public static boolean invalidLogin(String login) {
        return login.length() < 1;
    }

    public static boolean invalidPassword(String password) {
        return password.length() < 1;
    }

    public static boolean invalidName(String name) {
        return name.length() < 1;
    }

    public static boolean invalidAge(int age) {
        return age < 0;
    }

    public static boolean invalidId(int id) {
        return id < 0;
    }
}
