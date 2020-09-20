package com.company.consoleapp.validator;

public class AddressValidator {
    public static boolean invalidStreet(String street){
        return street.length() < 2;
    }

    public static boolean invalidId(int id){
        return id < 0;
    }
}
