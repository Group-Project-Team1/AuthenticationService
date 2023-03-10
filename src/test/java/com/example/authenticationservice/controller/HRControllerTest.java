package com.example.authenticationservice.controller;

import com.example.authenticationservice.security.JwtProvider;
import com.example.authenticationservice.service.RegistrationTokenService;
import com.example.authenticationservice.service.RoleService;
import com.example.authenticationservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest()
@Slf4j
public class HRControllerTest {
    @Mock
    private RegistrationTokenService registrationTokenService;

    @Mock
    private UserService userService;

    private RabbitTemplate rabbitTemplate;

    private HRController hrController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.hrController = new HRController(
                registrationTokenService, userService, rabbitTemplate
        );
    }

    @Test
    public void testTokenGeneration() {

    }

}
