package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    @Transactional
    public void testGetEmployeeRole() {
        Role role1 = roleDao.getEmployeeRole();
        assertEquals("Employee", role1.getRoleName());
    }
}
