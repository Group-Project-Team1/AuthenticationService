package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.domain.response.RegistrationTokenResponse;
import com.example.authenticationservice.exception.DuplicateEmailForTokenException;
import com.example.authenticationservice.exception.InvalidAuthorityException;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/hr/")
public class HRController {
    private RegistrationTokenService registrationTokenService;
    private UserService userService;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public HRController(RegistrationTokenService registrationTokenService, UserService userService, RabbitTemplate rabbitTemplate) {
        this.registrationTokenService = registrationTokenService;
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("createToken")
    @PreAuthorize("hasAuthority('hr')")
    public RegistrationTokenResponse createRegistrationToken(@RequestParam("email") String email)
            throws InvalidAuthorityException{
        // Get the authorized user, check if he is a HR or not.
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByUsername(username);
        Boolean isHR = userService.isHR(currentUser);
        // if an HR, create a new token
        if (!isHR) {
            throw new InvalidAuthorityException(username);
        }

        try {
            RegistrationToken token = registrationTokenService.createToken(email, currentUser);

            String messageBody = "Link to registration page: localhost:9999/api/employee/register\n Token: " + token.getToken();
            Message message = new Message(messageBody.getBytes(StandardCharsets.UTF_8));
            message.getMessageProperties().getHeaders().put("subject", "This is your token for registration");
            message.getMessageProperties().getHeaders().put("recipient", email);
            rabbitTemplate.convertAndSend("email.direct", "emailKey", message);

            return RegistrationTokenResponse.builder()
                    .message("Created a new token...")
                    .registrationToken(token)
                    .build();

        } catch (DuplicateEmailForTokenException e) {
            RegistrationToken token = registrationTokenService.getLatestTokenByEmail(email);
            return RegistrationTokenResponse.builder()
                    .message(e.getMessage())
                    .registrationToken(token)
                    .build();
        }
    }
}
