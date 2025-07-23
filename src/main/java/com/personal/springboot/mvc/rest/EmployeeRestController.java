package com.personal.springboot.mvc.rest;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.dto.UserDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.service.DTOService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    public EmployeeRestController (EmployeeDAO employeeDAO, DTOService dtoService){
        this.employeeDAO = employeeDAO;
        this.dtoService = dtoService;
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll(){
        List<EmployeeDTO> employeesDTO = employeeDAO.findAllEmployee().stream()
                .map(employee -> dtoService.covertToEmployeeDTO(employee))
                .toList();

        return employeesDTO;
    }
}
