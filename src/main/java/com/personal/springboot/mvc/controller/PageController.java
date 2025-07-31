package com.personal.springboot.mvc.controller;

import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage(Model model, Principal principal, HttpSession session) {

        model.addAttribute("userName", principal.getName());

        User loggedInUser = (User) session.getAttribute("user");
        model.addAttribute("roles",loggedInUser.getRoles());

        Employee loggedInEmployee = (Employee) session.getAttribute("employee");
        model.addAttribute("firstName",loggedInEmployee.getFirstName());
        model.addAttribute("lastName",loggedInEmployee.getLastName());

        return "home-page";
    }
}
