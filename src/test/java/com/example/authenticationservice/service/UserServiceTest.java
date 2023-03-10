package com.example.authenticationservice.service;


import com.example.authenticationservice.dao.RegistrationTokenDao;
import com.example.authenticationservice.dao.UserDao;
import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.Role;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.security.AuthUserDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Test
    void testLoadUserByUsername(){
        User expected = User.builder()
                .id(12345)
                .username("username")
                .password("password")
                .email("username@gmail.com")
                .activeFlag(true)
                .createDate(Timestamp.from(Instant.now()))
                .lastModificationDate(Timestamp.from(Instant.now()))
                .registrationTokens(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userDao.loadUserByUsername("username")).thenReturn(expected);
        AuthUserDetail actual = (AuthUserDetail) userService.loadUserByUsername("username");
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getId(), actual.getUserId());
    }

    @Test
    void testGetUserById(){
        User expected = User.builder()
                .id(12345)
                .username("username")
                .password("password")
                .email("username@gmail.com")
                .activeFlag(true)
                .createDate(Timestamp.from(Instant.now()))
                .lastModificationDate(Timestamp.from(Instant.now()))
                .registrationTokens(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userDao.getUserById(12345)).thenReturn(expected);
        User actual = userService.getUserById(12345);
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void testGetUserByEmail(){
        User expected = User.builder()
                .id(12345)
                .username("username")
                .password("password")
                .email("username@gmail.com")
                .activeFlag(true)
                .createDate(Timestamp.from(Instant.now()))
                .lastModificationDate(Timestamp.from(Instant.now()))
                .registrationTokens(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userDao.getUserByEmail("email@gmail.com")).thenReturn(expected);
        User actual = userService.getUserByEmail("email@gmail.com");
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void testGetUserByUsername(){
        User expected = User.builder()
                .id(12345)
                .username("username")
                .password("password")
                .email("username@gmail.com")
                .activeFlag(true)
                .createDate(Timestamp.from(Instant.now()))
                .lastModificationDate(Timestamp.from(Instant.now()))
                .registrationTokens(new HashSet<>())
                .roles(new HashSet<>())
                .build();
        Mockito.when(userDao.getUserByUsername("username")).thenReturn(expected);
        User actual = userService.getUserByUsername("username");
        assertEquals(expected.getUsername(), actual.getUsername());
    }

}
