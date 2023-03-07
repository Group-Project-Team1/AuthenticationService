package com.example.authenticationservice.exception;

public class DuplicateEmailForTokenException extends Exception{
    public DuplicateEmailForTokenException(String email) {
        super("The registration token for " + email + " already exists! Please try a new email.");
    }
}