package com.example.authenticationservice.exception;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String email) {
        super("Email " + email + " already exists! Please try a new email.");
    }
}