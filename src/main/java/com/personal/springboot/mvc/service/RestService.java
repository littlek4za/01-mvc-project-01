package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.dto.UserDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;

import java.util.List;

public interface RestService {
    UserDTO convertToUserDTO(User user);
    EmployeeDTO covertToEmployeeDTO(Employee employee);
    List<EmployeeDTO> findAllEmployeeDTO();
    Employee convertToEntity(EmployeeDTO employeeDTO);
    Employee saveEmployeeDTO(EmployeeDTO employeeDTO);
    Employee updateEmployee(EmployeeDTO employeeDTO);
    void deleteEmployee(int employeeId);
    Employee patchEmployee(int employeeId, EmployeeDTO patchDTO);
    void checkDuplicateUserName(String userName);
    void checkDuplicateEmail(String email);
    EmployeeDTO findEmployeeDTObyID(int employeeId);
}
