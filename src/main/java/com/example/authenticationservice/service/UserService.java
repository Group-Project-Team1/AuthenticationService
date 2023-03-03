package com.example.authenticationservice.service;

import com.example.authenticationservice.dao.UserDao;
import com.example.authenticationservice.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public Boolean isHR(User user) {
        return userDao.isHR(user);
    }
}
