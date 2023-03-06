package com.example.authenticationservice.AOP;


import com.example.authenticationservice.domain.response.ErrorResponse;
import com.example.authenticationservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    // hierarchical order does NOT matter unlike the try/catch block
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message("Using ExceptionHandler for handling Exception")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = {
        DuplicateEmailException.class,
        DuplicateUsernameException.class,
        RegistrationTokenExpiredException.class,
        RegistrationTokenNotExist.class,
        WrongRegistrationTokenException.class
    })
    public ResponseEntity<ErrorResponse> handleRegistrationException(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                    .message(e.getMessage())
                    .build(),
                HttpStatus.BAD_REQUEST
        );
    }

//    @ExceptionHandler(value = {AuthenticationException.class})
//    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e){
//        return new ResponseEntity<>(
//                ErrorResponse.builder()
//                        .message("Incorrect credentials, please try again.")
//                        .build(),
//                HttpStatus.BAD_REQUEST
//        );
//    }

}