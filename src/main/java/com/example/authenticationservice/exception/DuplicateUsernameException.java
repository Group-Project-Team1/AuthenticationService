package com.example.authenticationservice.exception;

public class DuplicateUsernameException extends Exception{
    public DuplicateUsernameException(String username) {
        super("Username " + username + " already exists! Please try a new username.");
    }
}
