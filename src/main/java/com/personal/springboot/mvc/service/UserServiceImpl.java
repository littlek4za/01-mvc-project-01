package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO){
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }


    @Override
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }


    @Override
    // when someone tries to log in, Spring Security will call this method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        //tell spring the user details, need username, password and roles/authorities
        //return new user(not custom entity user, its a spring built in class - user)
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    // Spring Security uses GrantedAuthority to represent roles or permissions
    // convert custom role object into a list of SimpleGrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
