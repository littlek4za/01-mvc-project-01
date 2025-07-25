package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.user.WebUser;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface WebUserService extends UserDetailsManager {

    public User findByUserName(String userName);

    void save(WebUser webUser);

    void update(WebUser webUser);

    List<User> findAllUser();

    List<Employee> findAllEmployee();

    WebUser toWebUser(Employee employee);

    Employee findEmployeeByIdWithUserInfo(int theId);

    void delete(int employeeId);

    List<Role> findAllRoles();

    Long findRoleIdByName(String roleName);

}
