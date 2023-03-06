package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.Role;
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
public class RoleDao {
    private SessionFactory sessionFactory;

    @Autowired
    public RoleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Role getEmployeeRole() {
        // Employee Id = 2
        return getRoleById(2);
    }

    private Role getRoleById(Integer roleId) {
        Session session;
        Optional<Role> role = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Role> cq = cb.createQuery(Role.class);
            Root<Role> root = cq.from(Role.class);
            Predicate predicate = cb.equal(root.get("id"), roleId);
            cq.select(root).where(predicate);
            role = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return role.isPresent() ? role.get() : null;
    }
}
