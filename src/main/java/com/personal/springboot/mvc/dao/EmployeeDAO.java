package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;

import java.util.List;

public interface EmployeeDAO {

    void save(Employee employee);

    void update(Employee employee);

    void deleteById(int employeeId);

    List<Employee> findAllEmployee();

    Employee findByIdWithUser(int theId);

    List<String> findAllEmail();

    Employee findEmployeeByUser(User user);
}
