package com.personal.springboot.mvc.rest;

import com.personal.springboot.mvc.dto.EmployeeDTO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.rest.exception.EmployeeRestSuccessResponse;
import com.personal.springboot.mvc.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private RestService restService;

    public EmployeeRestController(RestService restService) {
        this.restService = restService;

    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeesDTO = restService.findAllEmployeeDTO();

        return employeesDTO;
    }

    @GetMapping("/employees/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable("employeeId") int employeeId) {
        EmployeeDTO targetEmployeeDTO = restService.findEmployeeDTObyID(employeeId);
        return targetEmployeeDTO;
    }

    @PostMapping("/employees")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        String username = employeeDTO.getUserDTO().getUserName();
        String email = employeeDTO.getEmail();

        restService.checkDuplicateUserName(username);
        restService.checkDuplicateEmail(email);

        Employee newEmployee = restService.saveEmployeeDTO(employeeDTO);
        return restService.covertToEmployeeDTO(newEmployee);
    }

    @PutMapping("/employees")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee updatedEmployee = restService.updateEmployee(employeeDTO);
        return restService.covertToEmployeeDTO(updatedEmployee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeRestSuccessResponse> deleteEmployee(@PathVariable("employeeId") int employeeId){
        restService.deleteEmployee(employeeId);

        EmployeeRestSuccessResponse response = new EmployeeRestSuccessResponse(
                200,
                "Employee ID Deleted: " + employeeId,
                System.currentTimeMillis()
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("employees/{employeeId}")
    public EmployeeDTO patchEmployee(@PathVariable("employeeId") int employeeId,
                                     @RequestBody EmployeeDTO patchDTO) {

        Employee patchedEmployee = restService.patchEmployee(employeeId, patchDTO);

        return restService.covertToEmployeeDTO(patchedEmployee);
    }

}
