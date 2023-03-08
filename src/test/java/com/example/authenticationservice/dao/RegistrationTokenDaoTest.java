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
        String token1 = "team1-em1@gmail.com";
        RegistrationToken actual1 = registrationTokenDao.getRegistrationTokenByToken(token1);
        Integer expectedId1 = 2;
        String token2 = "team1-em2@gmail.com";
        RegistrationToken actual2 = registrationTokenDao.getRegistrationTokenByToken(token2);
        Integer expectedId2 = 4;
        String token3 = "randomtoken";
        RegistrationToken actual3 = registrationTokenDao.getRegistrationTokenByToken(token3);
        assertEquals(actual1.getId(), expectedId1);
        assertEquals(actual2.getId(), expectedId2);
        assertNull(actual3);
    }

    @Test
    @Transactional
    public void testIsExpired() {
        String token = "team1-em1@gmail.com";
        RegistrationToken rt = registrationTokenDao.getRegistrationTokenByToken(token);
        assertTrue(registrationTokenDao.isExpired(rt));
    }

    @Test
    @Transactional
    public void testGetRegistrationTokenByEmail() {
        String email1 = "em1@gmail.com";
        RegistrationToken actual1 = registrationTokenDao.getRegistrationTokenByEmail(email1);
        Integer expectedId1 = 2;
        String email2 = "em2@gmail.com";
        RegistrationToken actual2 = registrationTokenDao.getRegistrationTokenByEmail(email2);
        Integer expectedId2 = 4;
        String email3 = "randomemail@gmail.com";
        RegistrationToken actual3 = registrationTokenDao.getRegistrationTokenByEmail(email3);
        assertEquals(actual1.getId(), expectedId1);
        assertEquals(actual2.getId(), expectedId2);
        assertNull(actual3);
    }

    @Test
    @Transactional
    public void testGetTokensByEmailExpDateDesc() {
        String email = "user2@gmail.com";
        List<RegistrationToken> registrationTokens = registrationTokenDao.getTokensByEmailExpDateDesc(email);
        assertEquals(31, registrationTokens.get(0).getId());
        assertEquals(30, registrationTokens.get(1).getId());
        assertEquals(29, registrationTokens.get(2).getId());
        assertEquals(28, registrationTokens.get(3).getId());
        assertEquals(27, registrationTokens.get(4).getId());
    }
}
