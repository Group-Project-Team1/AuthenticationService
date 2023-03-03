package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationTokenDao {
    private SessionFactory sessionFactory;

    @Autowired
    public RegistrationTokenDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createToken(RegistrationToken registrationToken) {
        Session session;
        Integer tokenId = null;
        try{
            session = sessionFactory.getCurrentSession();
            tokenId = (Integer) session.save(registrationToken);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return tokenId;
    }
}
