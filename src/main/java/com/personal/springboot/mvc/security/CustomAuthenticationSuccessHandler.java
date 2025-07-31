package com.personal.springboot.mvc.security;


import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.WebUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // inject to use to look for user info from database after log in
    private WebUserService webUserService;

    public CustomAuthenticationSuccessHandler(WebUserService theWebUserService) {
        webUserService = theWebUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("In customAuthenticationSuccessHandler");

        // get the name of successful log in user
        String userName = authentication.getName();
        System.out.println("userName = " + userName);
        User theUser = webUserService.findByUserName(userName);
        Employee theEmployee = webUserService.findByUser(theUser);

        // add user object to the http session
        // by default, not full info can be display, usually only username, encoded password and roles
        // using custom can pull in full info(firstName, lastName etc) and use it when retrieve from session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        session.setAttribute("employee", theEmployee);

        //send the user to rootpage
        response.sendRedirect(request.getContextPath() + "/");
    }

    /* Examples of what you can do with a custom handler:
    -Store full User object in session (not just username)
    -Redirect to /admin if user is admin, or /user if normal user
    -Log login time in DB
    -Send welcome email
    -Block login if user is disabled */
}
