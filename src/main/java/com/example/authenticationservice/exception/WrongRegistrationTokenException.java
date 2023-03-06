package com.example.authenticationservice.exception;

public class WrongRegistrationTokenException extends Exception{
    public WrongRegistrationTokenException(String token) {
        super("The registration token is not created for this user, please try a different token.");
    }
}
