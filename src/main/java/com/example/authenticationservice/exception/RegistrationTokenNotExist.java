package com.example.authenticationservice.exception;

public class RegistrationTokenNotExist extends Exception{
    public RegistrationTokenNotExist(String token) {
        super("The registration token " + token + " doesn't exist! Please try a new token.");
    }
}
