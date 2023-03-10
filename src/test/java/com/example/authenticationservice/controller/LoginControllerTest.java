package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.request.LoginRequest;
import com.example.authenticationservice.domain.response.LoginResponse;
import com.example.authenticationservice.domain.response.MessageResponse;
import com.example.authenticationservice.security.JwtProvider;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.RoleService;
import com.example.authenticationservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest()
@Slf4j
public class LoginControllerTest {

    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private RegistrationTokenService registrationTokenService;

    @Mock
    private RoleService roleService;

    @Mock
    private JwtProvider jwtProvider;


    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    private MockMvc mockMvc;

    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.loginController = new LoginController(
                authenticationManager, userService, registrationTokenService,
                roleService, jwtProvider, restTemplateBuilder);
    }

    @Test
    public void testRegister() {

    }

    @Test
    public void testLogin() {

    }



}
