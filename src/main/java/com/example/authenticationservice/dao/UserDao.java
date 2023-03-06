package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.domain.entity.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class UserDao {
    private SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getUserById(Integer id) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("id"), id);
            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user.isPresent() ? user.get() : null;
    }

    public Boolean isHR(User user) {
        return true;
    }

    public User getUserByEmail(String email) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("email"), email);
            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user.isPresent() ? user.get() : null;
    }

    public User getUserByUsername(String username) {
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("username"), username);
            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user.isPresent() ? user.get() : null;
    }

    public Integer createNewUser(User user) {
        Session session;
        Integer userId = null;
        try{
            session = sessionFactory.getCurrentSession();
            userId = (Integer)session.save(user);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userId;
    }
}
