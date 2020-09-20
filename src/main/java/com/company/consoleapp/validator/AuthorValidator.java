package com.company.consoleapp.validator;

public class AuthorValidator {
    public static boolean invalidId(int id){
        return id < 0;
    }
    public static boolean invalidName(String name){
        return name.length() < 2;
    }
}
