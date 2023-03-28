package com.switchfully.eurder.internals;

import com.switchfully.eurder.internals.exceptions.InvalidFormatException;
import com.switchfully.eurder.internals.exceptions.MissingFieldException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {
    public static String verifyField(String field){
        if (field == null || field.isEmpty() || field.isBlank()){
            throw new MissingFieldException();
        }
        return field;
    }
    public static String verifyEmailFormat(String email){
        verifyField(email);
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[._-]?)+@[a-z]+[.][a-z]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new InvalidFormatException("Provided email is not valid: " + email);
        }
        return email;
    }
    public static String verifyPhoneNumber(String phoneNumber){
        verifyField(phoneNumber);
        Pattern pattern = Pattern.compile("^[+][0-9]{9,12}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()){
            throw new InvalidFormatException("Provided phone number is not valid: " + phoneNumber);
        }
        return phoneNumber;
    }
}
