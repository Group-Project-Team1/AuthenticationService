package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
public class RegistrationTokenDaoTest {

    @Autowired
    private RegistrationTokenDao registrationTokenDao;

    @Test
    @Transactional
    public void testGetRegistrationTokenByToken(){
        String token1 = "team1-em1@gmail.com--1415907224";
        RegistrationToken actual1 = registrationTokenDao.getRegistrationTokenByToken(token1);
        Integer expectedId1 = 1;
        String token2 = "team1-em2@gmail.com--1032024156";
        RegistrationToken actual2 = registrationTokenDao.getRegistrationTokenByToken(token2);
        Integer expectedId2 = 2;
        String token3 = "randomtoken";
        RegistrationToken actual3 = registrationTokenDao.getRegistrationTokenByToken(token3);
        assertEquals(actual1.getId(), expectedId1);
        assertEquals(actual2.getId(), expectedId2);
        assertNull(actual3);
    }

    @Test
    @Transactional
    public void testIsExpired() {
        String token = "team1-em1@gmail.com--1415907224";
        RegistrationToken rt = registrationTokenDao.getRegistrationTokenByToken(token);
        assertFalse(registrationTokenDao.isExpired(rt));
    }

    @Test
    @Transactional
    public void testGetRegistrationTokenByEmail() {
        String email1 = "em1@gmail.com";
        RegistrationToken actual1 = registrationTokenDao.getRegistrationTokenByEmail(email1);
        Integer expectedId1 = 1;
        String email2 = "em2@gmail.com";
        RegistrationToken actual2 = registrationTokenDao.getRegistrationTokenByEmail(email2);
        Integer expectedId2 = 2;
        String email3 = "randomemail@gmail.com";
        RegistrationToken actual3 = registrationTokenDao.getRegistrationTokenByEmail(email3);
        assertEquals(actual1.getId(), expectedId1);
        assertEquals(actual2.getId(), expectedId2);
        assertNull(actual3);
    }

    @Test
    @Transactional
    public void testGetTokensByEmailExpDateDesc() {
        String email = "em1@gmail.com";
        List<RegistrationToken> registrationTokens = registrationTokenDao.getTokensByEmailExpDateDesc(email);
        assertEquals(1, registrationTokens.get(0).getId());
    }
}
