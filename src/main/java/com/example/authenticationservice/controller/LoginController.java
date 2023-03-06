package com.example.authenticationservice.controller;


import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.Role;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.domain.request.RegisterRequest;
import com.example.authenticationservice.domain.response.RegisterResponse;
import com.example.authenticationservice.exception.*;
import com.example.authenticationservice.security.AuthUserDetail;
import com.example.authenticationservice.security.JwtProvider;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.RoleService;
import com.example.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("api/employee/")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private RegistrationTokenService registrationTokenService;
    private RoleService roleService;

    @Autowired
    public LoginController(
            AuthenticationManager authenticationManager, UserService userService,
            RegistrationTokenService registrationTokenService, RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.registrationTokenService = registrationTokenService;
        this.roleService = roleService;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request)
            throws DuplicateEmailException, DuplicateUsernameException,
                    RegistrationTokenExpiredException, RegistrationTokenNotExist,
                    WrongRegistrationTokenException{
        // Check if the email and username have been registered.
        User user = userService.getUserByEmail(request.getEmail());
        if(user != null){
            throw new DuplicateEmailException(request.getEmail());
        }
        user = userService.getUserByUsername(request.getUsername());
        if(user != null){
            throw new DuplicateUsernameException(request.getUsername());
        }

        // Verify if the token has been expired or not.
        RegistrationToken registrationToken = registrationTokenService.getRegistrationTokenByToken(request.getToken());
        if (registrationToken == null) {
            throw new RegistrationTokenNotExist(request.getToken());
        }
        if (registrationTokenService.isExpired(registrationToken)) {
            throw new RegistrationTokenExpiredException(request.getToken());
        }
        if (!request.getEmail().equals(registrationToken.getEmail())) {
            throw new WrongRegistrationTokenException(request.getToken());
        }

        // Create a new user.
        User newUser = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .activeFlag(true)
                .registrationTokens(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        // Add the registration token to the new user.
        newUser.getRegistrationTokens().add(registrationToken);

        // Add the user role to the new user.
        Role role = roleService.getEmployeeRole();
        newUser.getRoles().add(role);

        // Create a new user.
        Integer userId = userService.createNewUser(newUser);

        // Return a success message.
        User registeredUser = userService.getUserById(userId);
        return RegisterResponse.builder()
                .message("Successfully register a new user!")
                .user(registeredUser)
                .build();
    }
}

