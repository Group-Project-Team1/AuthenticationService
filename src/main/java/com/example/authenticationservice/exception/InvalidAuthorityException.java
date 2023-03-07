package com.example.authenticationservice.exception;

public class InvalidAuthorityException extends Exception{
    public InvalidAuthorityException(String username) {
        super(username + " is not a HR. Cannot create a new token.");
    }
}