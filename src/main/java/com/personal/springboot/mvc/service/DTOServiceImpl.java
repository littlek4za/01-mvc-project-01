package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.dto.UserDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOServiceImpl implements DTOService{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private EmployeeDAO employeeDAO;

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

    @Override
    public List<EmployeeDTO> findAllEmployeeDTO() {
        List<EmployeeDTO> employeesDTO = employeeDAO.findAllEmployee().stream()
                .map(employee -> covertToEmployeeDTO(employee))
                .toList();

        return employeesDTO;
    }

    @Override
    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());

        User user = new User();
        user.setUserName(employeeDTO.getUserDTO().getUserName());
        user.setEnable(employeeDTO.getUserDTO().getEnable());

        List<Role> roles = employeeDTO.getUserDTO().getRoles().stream()
                .map(roleName -> roleDAO.findRoleByName(roleName))
                .collect(Collectors.toList());
        user.setRoles(roles);

        employee.setUser(user);

        return employee; //no id is assigned and no password is assigned
    }

    @Override
    @Transactional
    public Employee saveEmployeeDTO(EmployeeDTO employeeDTO) {
        Employee newEmployee = convertToEntity(employeeDTO);

        newEmployee.getUser().setPassword(passwordEncoder.encode(employeeDTO.getUserDTO().getPassword()));
        newEmployee.setId(0);

        employeeDAO.save(newEmployee);

        return newEmployee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        Employee existingEmployee;
        try {
            existingEmployee = employeeDAO.findByIdWithUser(employeeDTO.getId());
        } catch (Exception e){
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeDTO.getId());
        }

//        if (existingEmployee == null) {
//            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeDTO.getId());
//        }

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());

        existingEmployee.getUser().setUserName(employeeDTO.getUserDTO().getUserName());
        existingEmployee.getUser().setEnable(employeeDTO.getUserDTO().getEnable());

        List<Role> roles = employeeDTO.getUserDTO().getRoles().stream()
                .map(roleName -> roleDAO.findRoleByName(roleName))
                .collect(Collectors.toList());

        existingEmployee.getUser().setRoles(roles);
        String newPassword = employeeDTO.getUserDTO().getPassword();
        if(newPassword!=null && !newPassword.isBlank()){
            existingEmployee.getUser().setPassword(passwordEncoder.encode(newPassword));
        }

        employeeDAO.update(existingEmployee);

        return existingEmployee;
    }
}
