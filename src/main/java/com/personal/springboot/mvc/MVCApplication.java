package com.personal.springboot.mvc;

import com.personal.springboot.mvc.dao.EmployeeDAO;
import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.service.WebUserService;
import com.personal.springboot.mvc.user.WebUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MVCApplication {

    public static void main(String[] args) {
        SpringApplication.run(MVCApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleDAO roleDAO, UserDAO userDAO, WebUserService webUserService, EmployeeDAO employeeDAO) {
        return runner -> {
            //testing and troubleshoot purpose
            //findRoleByUserName(roleDAO);
            //findUserByUserName(userDAO);
            //findWebUserAndUpdate(userService,roleDAO,userDAO);
            //findEmployeeAllInfo(employeeDAO, userService);
            //findRoleIdByRoleName(roleDAO);
            //findEmployeeByUser(employeeDAO, userDAO);
        };
    }

    private void findEmployeeByUser(EmployeeDAO theEmployeeDAO, UserDAO theUserDAO) {
        User admin = theUserDAO.findByUserName("admin");
        Employee employee = theEmployeeDAO.findEmployeeByUser(admin);
        System.out.println(employee);
    }

    private void findRoleIdByRoleName(RoleDAO roleDAO) {
        Long id = roleDAO.findIdByName("ROLE_EMPLOYEE");
        System.out.println("Id for role employee = " + id);
    }

    private void findEmployeeAllInfo(EmployeeDAO employeeDAO, WebUserService webUserService) {
        int id = 3;
        Employee theEmployee1 = employeeDAO.findByIdWithUser(id);
        System.out.println("The Employee 1: " + "\n");
        System.out.println(theEmployee1);
        System.out.println(theEmployee1.getUser());
        System.out.println(theEmployee1.getUser().getRoles());

        Employee theEmployee2 = webUserService.findEmployeeByIdWithUserInfo(3);
        System.out.println("The Employee 2: " + "\n");
        System.out.println(theEmployee1);
        System.out.println(theEmployee1.getUser());
        System.out.println(theEmployee1.getUser().getRoles());

        WebUser webUser = webUserService.toWebUser(theEmployee2);
        System.out.println(webUser);
    }


    private void findWebUserAndUpdate(WebUserService webUserService, RoleDAO roleDAO, UserDAO userDAO) {
        Employee employee = webUserService.findEmployeeByIdWithUserInfo(3);

        WebUser webUser = webUserService.toWebUser(employee);
        System.out.println(webUser);

        webUser.setLastName("Ken");
        System.out.println(webUser);
        webUserService.update(webUser);
    }


    private void findUserByUserName(UserDAO userDAO) {

        System.out.println(userDAO.findByUserName("Ian"));

    }

    private void findRoleByUserName(RoleDAO roleDAO) {

        System.out.println(roleDAO.findRoleByUserName("mjcheh"));
    }
}
