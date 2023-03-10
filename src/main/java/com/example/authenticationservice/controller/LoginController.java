package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.Role;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.domain.request.LoginRequest;
import com.example.authenticationservice.domain.request.RegisterRequest;
import com.example.authenticationservice.domain.response.LoginResponse;
import com.example.authenticationservice.domain.response.RegisterResponse;
import com.example.authenticationservice.exception.*;
import com.example.authenticationservice.security.AuthUserDetail;
import com.example.authenticationservice.security.JwtProvider;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.RoleService;
import com.example.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("api/employee/")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private RegistrationTokenService registrationTokenService;
    private RoleService roleService;
    private JwtProvider jwtProvider;

    private RestTemplate restTemplate;

    @Autowired
    public LoginController(
            AuthenticationManager authenticationManager, UserService userService,
            RegistrationTokenService registrationTokenService, RoleService roleService,
            JwtProvider jwtProvider, RestTemplateBuilder restTemplateBuilder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.registrationTokenService = registrationTokenService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
        this.restTemplate = restTemplateBuilder.build();
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
                .createDate(Timestamp.from(Instant.now()))
                .lastModificationDate(Timestamp.from(Instant.now()))
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

        // Use RestTemplate to add the call that single endpoint from the CompositeService.
        String email = registeredUser.getEmail();
        String URL = "http://localhost:8083/composite-service/auth/user-register/" + userId + "/" + email;
        Authentication authentication;
        //Try to authenticate the user using the username and password
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = "Bearer:" + jwtProvider.createToken(authUserDetail);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, jwtEntity, String.class);
        System.out.println(response);
        return RegisterResponse.builder()
                .message("Successfully register a new user!")
                .user(registeredUser)
                .build();
    }

    @PostMapping("auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws AuthenticationException{

        Authentication authentication;
        //Try to authenticate the user using the username and password
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();
    }
}

