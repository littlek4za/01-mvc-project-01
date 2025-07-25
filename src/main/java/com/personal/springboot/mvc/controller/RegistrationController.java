package com.personal.springboot.mvc.controller;

import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.WebUserService;
import com.personal.springboot.mvc.user.OnCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.personal.springboot.mvc.user.WebUser;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    //log error in run log
    private Logger logger = Logger.getLogger(getClass().getName());
    private WebUserService webUserService;

    @Autowired
    public RegistrationController(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    // trim whitespace or convert white space to null
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationPage(Model theModel) {

        theModel.addAttribute("webUser", new WebUser());

        return "register/registration-form-page";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Validated(OnCreate.class) @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form Validation
        if (theBindingResult.hasErrors()) {
            return "register/registration-form-page";
        }

        // check for database if user already exists
        User existingUser = webUserService.findByUserName(userName);

        if (existingUser != null) {
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "Message: User name already exists.");

            logger.warning("User already exists");
            return "register/registration-form-page";
        }

        //create user account and store in the database for user and employee
        webUserService.save(theWebUser);

        logger.info("Successfully created user: " + userName);

        //place user in the web http sesssion
        session.setAttribute("user", theWebUser);

        return "register/registration-confirmation-page";
    }
}
