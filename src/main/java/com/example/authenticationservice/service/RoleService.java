package com.example.authenticationservice.service;

import com.example.authenticationservice.dao.RoleDao;
import com.example.authenticationservice.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    public Role getEmployeeRole() {
        return roleDao.getEmployeeRole();
    }
}
