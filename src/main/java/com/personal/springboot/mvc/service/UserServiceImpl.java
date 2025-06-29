package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private EmployeeDAO employeeDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, EmployeeDAO employeeDAO, BCryptPasswordEncoder passwordEncoder){
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.employeeDAO = employeeDAO;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    @Transactional
    @Override
    public void save(WebUser webUser) {
        User user = new User();
        Employee employee = new Employee();

        user.setUserName(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setEnable(true);
        employee.setFirstName(webUser.getFirstName());
        employee.setLastName(webUser.getLastName());
        employee.setEmail(webUser.getEmail());

        // assign default Role
        user.setRoles((Arrays.asList(roleDAO.findRoleByName("ROLE_EMPLOYEE"))));

        // save user first to generate user ID
        userDAO.save(user);

        // assign user to employee
        employee.setUser(user);

        //save employee
        employeeDAO.save(employee);

    }

    @Override
    // when someone tries to log in, Spring Security will call this method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        //tell spring the user details, need username, password and roles/authorities
        //return new user(not custom entity user, it is a spring built in class - user)
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
