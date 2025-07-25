package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.dto.UserDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.rest.exception.DuplicateEmailException;
import com.personal.springboot.mvc.rest.exception.DuplicateUsernameException;
import com.personal.springboot.mvc.service.exception.EmployeeNotFoundException;
import com.personal.springboot.mvc.service.exception.NoPasswordUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestServiceImpl implements RestService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private UserDAO userDAO;

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

        List<Role> roles = newEmployee.getUser().getRoles();
        boolean hasEmployeeRole = roles.stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getName()));
        if (!hasEmployeeRole) {
            roles.add(roleDAO.findRoleByName("ROLE_EMPLOYEE"));
        }
        newEmployee.getUser().setRoles(roles);
        employeeDAO.save(newEmployee);

        return newEmployee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(EmployeeDTO employeeDTO) {
        Employee existingEmployee;
        try {
            existingEmployee = employeeDAO.findByIdWithUser(employeeDTO.getId());
        } catch (Exception e) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeDTO.getId());
        }

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());

        existingEmployee.getUser().setUserName(employeeDTO.getUserDTO().getUserName());
        existingEmployee.getUser().setEnable(employeeDTO.getUserDTO().getEnable());

        List<Role> roles = employeeDTO.getUserDTO().getRoles().stream()
                .map(roleName -> roleDAO.findRoleByName(roleName))
                .collect(Collectors.toList());

        //make sure minimum ROLE_EMPLOYEE for every user
        boolean hasEmployeeRole = roles.stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getName()));
        if (!hasEmployeeRole) {
            roles.add(roleDAO.findRoleByName("ROLE_EMPLOYEE"));
        }
        existingEmployee.getUser().setRoles(roles);

        String newPassword = employeeDTO.getUserDTO().getPassword();
        if (newPassword != null && !newPassword.isBlank()) {
            throw new NoPasswordUpdateException("Password update is not allowed");
        }

        employeeDAO.update(existingEmployee);

        return existingEmployee;
    }

    @Override
    @Transactional
    public void deleteEmployee(int employeeId) {
        Employee targetEmployee;
        try {
            targetEmployee = employeeDAO.findByIdWithUser(employeeId);
        } catch (Exception e) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }
        User targetUser = targetEmployee.getUser();
        boolean isAdmin = targetUser.getRoles().stream()
                .anyMatch((role -> "ROLE_ADMIN".equals(role.getName())));
        if (isAdmin) {
            throw new AccessDeniedException("Cannot delete an Admin user.");
        }

        Long userId = targetUser.getId();
        employeeDAO.deleteById(employeeId);
        userDAO.deleteById(userId);
    }

    @Override
    @Transactional
    public Employee patchEmployee(int employeeId, EmployeeDTO patchDTO) {

        Employee existingEmployee;
        try {
            existingEmployee = employeeDAO.findByIdWithUser(employeeId);
        } catch (Exception e) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
        }

        if (patchDTO.getId() != 0 && patchDTO.getId() != employeeId) {
            throw new RuntimeException("Employee id cannot be updated");
        }

        if (patchDTO.getFirstName() != null) {
            existingEmployee.setFirstName(patchDTO.getFirstName());
        }
        if (patchDTO.getLastName() != null) {
            existingEmployee.setLastName(patchDTO.getLastName());
        }
        if (patchDTO.getEmail() != null) {
            existingEmployee.setEmail(patchDTO.getEmail());
        }
        if (patchDTO.getUserDTO() != null) {
            User existingUser = existingEmployee.getUser();
            UserDTO userDTO = patchDTO.getUserDTO();

            if (userDTO.getUserName() != null) {
                existingUser.setUserName(userDTO.getUserName());
            }
            if (userDTO.getEnable() != null) {
                userDTO.setEnable(userDTO.getEnable());
            }
            if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
                List<Role> roles = userDTO.getRoles().stream()
                        .map(roleName -> roleDAO.findRoleByName(roleName))
                        .filter(role -> role != null)
                        .collect(Collectors.toList());
                //make sure minimum ROLE_EMPLOYEE for every user
                boolean hasEmployeeRole = roles.stream()
                        .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getName()));
                if(!hasEmployeeRole) {
                    roles.add(roleDAO.findRoleByName("ROLE_EMPLOYEE"));
                }
                existingUser.setRoles(roles);
            }
            String newPassword = userDTO.getPassword();
            if (newPassword != null && !newPassword.isBlank()) {
                throw new NoPasswordUpdateException("Password update is not allowed");
            }
        }

        employeeDAO.update(existingEmployee);

        return existingEmployee;
    }

    @Override
    public void checkDuplicateUserName(String userName) {
        User existingUser = userDAO.findByUserName(userName);
        if (existingUser != null) {
            throw new DuplicateUsernameException("Username is duplicated: " + userName);
        }
    }

    @Override
    public void checkDuplicateEmail(String email) {
        List<String> emails = employeeDAO.findAllEmail();
        if (emails.contains(email)) {
            throw new DuplicateEmailException("Email is duplicated: " + email);
        }
    }

    @Override
    public EmployeeDTO findEmployeeDTObyID(int employeeId) {
        Employee targetEmployee = employeeDAO.findByIdWithUser(employeeId);
        EmployeeDTO targetEmployeeDTO = covertToEmployeeDTO(targetEmployee);
        return targetEmployeeDTO;
    }

}
