package com.example.authenticationservice.dao;

import com.example.authenticationservice.domain.entity.RegistrationToken;
import com.example.authenticationservice.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void testLoadUserByUsername(){
        String username1 = "em1";
        User u1 = userDao.loadUserByUsername(username1);
        Integer expectedId1 = 4;
        String username2 = "em2";
        User u2 = userDao.loadUserByUsername(username2);
        Integer expectedId2 = 5;
        String username3 = "randomname";
        User u3 = userDao.loadUserByUsername(username3);
        assertEquals(u1.getId(), expectedId1);
        assertEquals(u2.getId(), expectedId2);
        assertNull(u3);
    }

    @Test
    @Transactional
    public void testGetUserById() {
        Integer id1 = 4;
        User u1 = userDao.getUserById(id1);
        String expectedUsername1 = "em1";
        Integer id2 = 5;
        User u2 = userDao.getUserById(id2);
        String expectedUsername2 = "em2";
        Integer id3 = 1521323;
        User u3 = userDao.getUserById(id3);
        assertEquals(u1.getUsername(), expectedUsername1);
        assertEquals(u2.getUsername(), expectedUsername2);
        assertNull(u3);
    }

    @Test
    @Transactional
    public void testGetUserByEmail() {
        String email1 = "em1@gmail.com";
        User user1 = userDao.getUserByEmail(email1);
        Integer expectedId1 = 4;
        String email2 = "em2@gmail.com";
        User user2 = userDao.getUserByEmail(email2);
        Integer expectedId2 = 5;
        String email3 = "randomemail@gmail.com";
        User user3 = userDao.getUserByEmail(email3);
        assertEquals(user1.getId(), expectedId1);
        assertEquals(user2.getId(), expectedId2);
        assertNull(user3);
    }

    @Test
    @Transactional
    public void testGetUserByUsername(){
        String username1 = "em1";
        User u1 = userDao.getUserByUsername(username1);
        Integer expectedId1 = 4;
        String username2 = "em2";
        User u2 = userDao.getUserByUsername(username2);
        Integer expectedId2 = 5;
        String username3 = "randomname";
        User u3 = userDao.getUserByUsername(username3);
        assertEquals(u1.getId(), expectedId1);
        assertEquals(u2.getId(), expectedId2);
        assertNull(u3);
    }

    @Test
    @Transactional
    public void testIsHR(){
        User u1 = userDao.getUserById(1);
        User u2 = userDao.getUserById(4);
        assertTrue(userDao.isHR(u1));
        assertFalse(userDao.isHR(u2));
    }

}
