package com.example.authenticationservice.service;

import com.example.authenticationservice.dao.RegistrationTokenDao;
import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.exception.DuplicateEmailForTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

@Service
public class RegistrationTokenService {
    private final Integer HOURS_TO_MILISECONDS = 60 * 60 * 1000;
    private final RegistrationTokenDao registrationTokenDao;

    @Autowired
    public RegistrationTokenService(RegistrationTokenDao registrationTokenDao) {
        this.registrationTokenDao = registrationTokenDao;
    }

    @Transactional
    public RegistrationToken createToken(String email, User user) throws DuplicateEmailForTokenException {
        RegistrationToken lastRegistrationToken = registrationTokenDao.getLatestTokenByEmail(email);
        if (lastRegistrationToken != null && !registrationTokenDao.isExpired(lastRegistrationToken)) {
            throw new DuplicateEmailForTokenException(email);
        }

        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setEmail(email);
        registrationToken.setUser(user);
        Random random = new Random();
        String token = "team1-" + email + "-" + String.valueOf(random.nextInt());
        registrationToken.setToken(token);
        registrationToken.setExpirationDate(
                new Timestamp(Timestamp.from(Instant.now()).getTime() + 3 * HOURS_TO_MILISECONDS)
        );
        Integer tokenId = registrationTokenDao.createToken(registrationToken);
        registrationToken.setId(tokenId);
        return registrationToken;
    }

    @Transactional
    public RegistrationToken getRegistrationTokenByToken(String token) {
        return registrationTokenDao.getRegistrationTokenByToken(token);
    }

    @Transactional
    public boolean isExpired(RegistrationToken registrationToken) {
        return registrationTokenDao.isExpired(registrationToken);
    }

    @Transactional
    public RegistrationToken getLatestTokenByEmail(String email) {
        return registrationTokenDao.getLatestTokenByEmail(email);
    }
}
