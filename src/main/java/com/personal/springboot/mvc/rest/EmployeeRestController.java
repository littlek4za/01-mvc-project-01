package com.personal.springboot.mvc.rest;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.rest.exception.DuplicateEmailException;
import com.personal.springboot.mvc.rest.exception.DuplicateUsernameException;
import com.personal.springboot.mvc.service.DTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    // Client can
    // Get a list of employees
    // Get a single employee by id
    // Add a new employee
    // Update an employee
    // Delete an employee by id
    private EmployeeDAO employeeDAO;
    private DTOService dtoService;
    private UserDAO userDAO;

    public EmployeeRestController(EmployeeDAO employeeDAO, DTOService dtoService, UserDAO userDAO) {
        this.employeeDAO = employeeDAO;
        this.dtoService = dtoService;
        this.userDAO = userDAO;
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeesDTO = dtoService.findAllEmployeeDTO();

        return employeesDTO;
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable("employeeId") int employeeId) {
        Employee targetEmployee = employeeDAO.findByIdWithUser(employeeId);
        EmployeeDTO targetEmployeeDTO = dtoService.covertToEmployeeDTO(targetEmployee);

        return targetEmployeeDTO;
    }

    @PostMapping("/employees")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        String username = employeeDTO.getUserDTO().getUserName();
        String email = employeeDTO.getEmail();

        User existingUser = userDAO.findByUserName(username);
        if (existingUser != null) {
            throw new DuplicateUsernameException("Username is duplicated: " + username);
        }

        List<String> emails = employeeDAO.findAllEmail();
        if (emails.contains(email)) {
            throw new DuplicateEmailException("Email is duplicated: " + email);
        }

        Employee newEmployee = dtoService.saveEmployeeDTO(employeeDTO);
        return dtoService.covertToEmployeeDTO(newEmployee);
    }

    @PutMapping ("/employees")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO){

        Employee updatedEmployee = dtoService.updateEmployee(employeeDTO);
        return dtoService.covertToEmployeeDTO(updatedEmployee);
    }
}
