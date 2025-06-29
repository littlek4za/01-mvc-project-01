package com.personal.springboot.mvc.security;


import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.UserService;
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
    private UserService userService;

    public CustomAuthenticationSuccessHandler (UserService theUserService) {
        userService = theUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("In customAuthenticationSuccessHandler");

        // get the name of successful log in user
        String userName = authentication.getName();
        System.out.println("userName = " + userName);
        User theUser = userService.findByUserName(userName);

        // add user object to the http session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

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
