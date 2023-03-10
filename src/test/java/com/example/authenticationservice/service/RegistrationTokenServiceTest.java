package com.example.authenticationservice.service;


import com.example.authenticationservice.dao.RegistrationTokenDao;
import com.example.authenticationservice.domain.entity.RegistrationToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationTokenServiceTest {
    @InjectMocks
    private RegistrationTokenService registrationTokenService;

    @Mock
    private RegistrationTokenDao registrationTokenDao;

    @Test
    void testGetRegistrationTokenByToken(){
        RegistrationToken expected = new RegistrationToken(12345, "token", "email@gmail.com", null, null);
        Mockito.when(registrationTokenDao.getRegistrationTokenByToken("token")).thenReturn(expected);
        RegistrationToken actual = registrationTokenService.getRegistrationTokenByToken("token");
        assertEquals(expected.getToken(), actual.getToken());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    void testGetLatestTokenByEmail(){
        RegistrationToken expected = new RegistrationToken(12345, "token", "email@gmail.com", null, null);
        Mockito.when(registrationTokenDao.getLatestTokenByEmail("email@gmail.com")).thenReturn(expected);
        RegistrationToken actual = registrationTokenService.getLatestTokenByEmail("email@gmail.com");
        assertEquals(expected.getToken(), actual.getToken());
        assertEquals(expected.getEmail(), actual.getEmail());
    }
}
