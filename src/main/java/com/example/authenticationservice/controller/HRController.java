package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.domain.response.RegistrationTokenResponse;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hr/")
public class HRController {
    private RegistrationTokenService registrationTokenService;
    private UserService userService;

    @Autowired
    public HRController(RegistrationTokenService registrationTokenService, UserService userService) {
        this.registrationTokenService = registrationTokenService;
        this.userService = userService;
    }

    @GetMapping("createToken")
    public RegistrationTokenResponse createRegistrationToken(@RequestParam("email") String email) {
        // TODO: get the current login user.

        // Check the role of the user, if he/she is a HR.
        User currentUser = userService.getUserById(1);
        Boolean isHR = userService.isHR(currentUser);
        // if an HR, create a new token
        if (isHR) {
            RegistrationToken token = registrationTokenService.createToken(email, currentUser);
            // TODO: send the token to the given email.
            return RegistrationTokenResponse.builder()
                    .message("Created a new token...")
                    .registrationToken(token)
                    .build();
        }
        return RegistrationTokenResponse.builder()
                .message("This employee is not an HR, cannot create registration token...")
                .registrationToken(null)
                .build();
    }
}
