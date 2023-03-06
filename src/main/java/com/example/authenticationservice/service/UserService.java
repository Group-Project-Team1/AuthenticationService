package com.example.authenticationservice.service;

import com.example.authenticationservice.dao.UserDao;
import com.example.authenticationservice.domain.entity.User;
import com.example.authenticationservice.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);

        if (user==null){
            throw new UsernameNotFoundException("Username does not exist");
        }

        return AuthUserDetail.builder() // spring security's userDetail
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    @Transactional
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Transactional
    public Boolean isHR(User user) {
        return userDao.isHR(user);
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    public Integer createNewUser(User user) {
        return userDao.createNewUser(user);
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        List<String> permissions;
        if(userDao.isHR(user)) {
            permissions = new ArrayList<String>();
            permissions.add("hr");
            permissions.add("employee");
        }
        else{
            permissions = new ArrayList<String>();
            permissions.add("employee");
        }

        for (String permission :  permissions){
            userAuthorities.add(new SimpleGrantedAuthority(permission));
        }

        return userAuthorities;
    }
}
