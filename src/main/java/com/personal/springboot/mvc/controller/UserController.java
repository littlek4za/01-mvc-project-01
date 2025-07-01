package com.personal.springboot.mvc.controller;

import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.UserService;
import com.personal.springboot.mvc.user.OnCreate;
import com.personal.springboot.mvc.user.OnUpdate;
import com.personal.springboot.mvc.user.WebUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class UserController {

    private UserService userService;
    private RoleDAO roleDAO;

    @Autowired
    public UserController(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/list")
    public String listUsers(Model theModel) {

        //get all the user + employee info from db
        List<Employee> theEmployees = userService.findAllEmployee();

        theModel.addAttribute("employees", theEmployees);

        return "user/list-employees-page";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theEmployeeId, Model theModel) {

        //create model attribute to bind form data
        Employee theEmployee = userService.findEmployeeByIdWithUserInfo(theEmployeeId);

        if (theEmployee == null) {
            return "error";
        }

        WebUser existingWebUser = userService.toWebUser(theEmployee);

        theModel.addAttribute("webUser", existingWebUser);
        theModel.addAttribute("roleList", roleDAO.findAllRole());

        return "user/add-or-update-employee-page";
    }

    @PostMapping("/update")
    public String updateEmployeeAndUser(@Validated(OnUpdate.class) @ModelAttribute("webUser") WebUser theWebUser, BindingResult theBindingResult) {

        // form Validation
        if (theBindingResult.hasErrors()) {
            return "user/add-or-update-employee-page";
        }

        userService.update(theWebUser);

        return "redirect:/employee/list";
    }

    @PostMapping("/delete")
    public String deleteEmployeeAndUser(@RequestParam("employeeId") int employeeId, Model theModel) {

        userService.delete(employeeId);

        return "redirect:/employee/list";
    }
}
