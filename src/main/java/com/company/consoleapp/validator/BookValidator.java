package com.company.consoleapp.validator;

public class BookValidator {
    public static boolean invalidTitle(String title){
        return title.length() < 2;
    }

    public static boolean invalidDescription(String description){
        return description.length() < 2;
    }

    public static boolean invalidPrice (int price){
        return price < 0;
    }

    public static boolean invalidId (int id){
        return id < 0;
    }
}
