package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.dto.UserDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOServiceImpl implements DTOService{
    @Override
    public UserDTO convertToUserDTO(User user) {
        List<String> rolesName = user.getRoles().stream()
                .map(role -> role.getName())
                .toList();
        UserDTO userDTO = new UserDTO(user.getId(), user.getUserName(), user.getEnable(), rolesName);

    return userDTO;
    }

    @Override
    public EmployeeDTO covertToEmployeeDTO(Employee employee) {

        UserDTO userDTO = convertToUserDTO(employee.getUser());
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), userDTO);
        return employeeDTO;
    }
}
