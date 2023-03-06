package com.example.authenticationservice.exception;

public class RegistrationTokenExpiredException extends Exception{
    public RegistrationTokenExpiredException(String token) {
        super("The registration token " + token + " has expired! Please request a new token from HR.");
    }
}
