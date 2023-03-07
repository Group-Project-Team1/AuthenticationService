package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public RegistrationToken getRegistrationTokenByToken(String token) {
        Session session;
        Optional<RegistrationToken> registrationToken = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RegistrationToken> cq = cb.createQuery(RegistrationToken.class);
            Root<RegistrationToken> root = cq.from(RegistrationToken.class);
            Predicate predicate = cb.equal(root.get("token"), token);
            cq.select(root).where(predicate);
            registrationToken = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return registrationToken.isPresent() ? registrationToken.get() : null;
    }

    public boolean isExpired(RegistrationToken registrationToken) {
        Timestamp now = Timestamp.from(Instant.now());
        return registrationToken.getExpirationDate().before(now);
    }

    public RegistrationToken getLatestTokenByEmail(String email) {
        List<RegistrationToken> tokens = getTokensByEmailExpDateDesc(email);
        return tokens.isEmpty() ? null : tokens.get(0);
    }

    public List<RegistrationToken> getTokensByEmailExpDateDesc(String email) {
        Session session;
        List<RegistrationToken> registrationTokens = new ArrayList<>();
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RegistrationToken> cq = cb.createQuery(RegistrationToken.class);
            Root<RegistrationToken> root = cq.from(RegistrationToken.class);
            Predicate predicate = cb.equal(root.get("email"), email);
            cq.select(root).where(predicate);
            registrationTokens = session.createQuery(cq).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(registrationTokens, (t1, t2) -> t2.getExpirationDate().compareTo(t1.getExpirationDate()));
        return registrationTokens;
    }

    public RegistrationToken getRegistrationTokenByEmail(String email) {
        Session session;
        Optional<RegistrationToken> registrationToken = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RegistrationToken> cq = cb.createQuery(RegistrationToken.class);
            Root<RegistrationToken> root = cq.from(RegistrationToken.class);
            Predicate predicate = cb.equal(root.get("email"), email);
            cq.select(root).where(predicate);
            registrationToken = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return registrationToken.isPresent() ? registrationToken.get() : null;
    }
}
