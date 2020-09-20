package com.company.consoleapp.validator;

public class OrderValidator {
    public static boolean invalidId(int id){
        return id < 0;
    }
}
