package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebUserServiceImpl implements WebUserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private EmployeeDAO employeeDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebUserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, EmployeeDAO employeeDAO, BCryptPasswordEncoder passwordEncoder) {
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

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public void update(WebUser webUser) {
        System.out.println("WebUser Info: " + webUser);
        Employee existingEmployee = employeeDAO.findByIdWithUser(webUser.getEmployeeId());
        User existingUser = existingEmployee.getUser();
        existingUser.setUserName(webUser.getUserName());
        existingUser.setEnable(webUser.getEnabled());

        //Ensure ROLE_EMPLOYEE always applied during update, basic role that cannot be removed
        List<Long> roleIds = Optional.ofNullable(webUser.getRoleIds()).orElseGet(ArrayList::new);
        Long employeeRoleId = roleDAO.findRoleByName("ROLE_EMPLOYEE").getId();
        if(!roleIds.contains(employeeRoleId)){
            roleIds.add(employeeRoleId);
        }
        List<Role> roles = roleIds.stream().map(roleId -> roleDAO.findRoleById(roleId)).collect(Collectors.toList());
        existingUser.setRoles(roles);

        existingEmployee.setFirstName(webUser.getFirstName());
        existingEmployee.setLastName(webUser.getLastName());
        existingEmployee.setEmail(webUser.getEmail());
        System.out.println("Updating User Info: " + existingUser);
        userDAO.update(existingUser);
        System.out.println("Updating Employee Info: " + existingEmployee);
        employeeDAO.update(existingEmployee);
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.findAllUser();
    }

    public List<Employee> findAllEmployee() {
        return employeeDAO.findAllEmployee();
    }

    @Override
    public WebUser toWebUser(Employee employee) {
        WebUser webUser = new WebUser();
        webUser.setUserName(employee.getUser().getUserName());
        webUser.setFirstName(employee.getFirstName());
        webUser.setLastName(employee.getLastName());
        webUser.setEmail(employee.getEmail());
        webUser.setEnabled(employee.getUser().getEnable());
        webUser.setEmployeeId(employee.getId());

        List<Long> roleIds = employee.getUser().getRoles().stream()
                .map(role -> role.getId()).collect(Collectors.toList());
        webUser.setRoleIds(roleIds);
        System.out.println("Check role gather from form: " + roleIds);
        return webUser;
    }

    public Employee findEmployeeByIdWithUserInfo(int theId) {
        return employeeDAO.findByIdWithUser(theId);
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int employeeId) {
        Employee employee = employeeDAO.findByIdWithUser(employeeId);
        User user = employee.getUser();
        Collection<Role> roles = user.getRoles();
        for (Role role : roles) {
            if ("ROLE_ADMIN".equals(role.getName())) {
                throw new AccessDeniedException("Cannot delete an Admin user.");
            }
        }

        Long userId = employee.getUser().getId();
        employeeDAO.deleteById(employeeId);
        userDAO.deleteById(userId);
    }

    @Override
    // when someone tries to log in, Spring Security will call this method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        //tell spring the user details, need username, password and roles/authorities
        //return new user(not custom entity user, it is a spring built in class - user)
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getEnable(),
                true,
                true,
                true,
                mapRolesToAuthorities(user.getRoles())); //use the custom method, just put in our roles, and it will convert
    }

    // Spring Security uses GrantedAuthority to represent roles or permissions
    // custom method to convert custom role object into a list of SimpleGrantedAuthority
    // something like a collection of new SimpleGrantedAuthority("ROLE_XXX")
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public List<Role> findAllRoles() {
        return roleDAO.findAllRole();
    }

    public Long findRoleIdByName(String roleName) {
        return roleDAO.findIdByName(roleName);
    }

    @Override
    public Employee findByUser(User user) {
        return employeeDAO.findEmployeeByUser(user);
    }

}
