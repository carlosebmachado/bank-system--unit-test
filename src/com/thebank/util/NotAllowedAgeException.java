package com.thebank.util;

public class NotAllowedAgeException extends Exception {

    public static String INVALID_AGE_MESSAGE = "Invalid age. Age must be between 18 and 65 years old.";

    public NotAllowedAgeException(String msg) {
        super(msg);
    }

}
