package com.personal.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage(Model model, Principal principal) {

        model.addAttribute("userName", principal.getName());

        return "home-page";
    }
}
