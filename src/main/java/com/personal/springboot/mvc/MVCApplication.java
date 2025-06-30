package com.personal.springboot.mvc;

import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.service.UserService;
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
	public CommandLineRunner commandLineRunner(RoleDAO roleDAO, UserDAO userDAO, UserService userService){
		return runner ->{
			//findRoleByUserName(roleDAO);
			//findUserByUserName(userDAO);
			findWebUserAndUpdate(userService,roleDAO,userDAO);
		};
	}

	private void findWebUserAndUpdate(UserService userService, RoleDAO roleDAO, UserDAO userDAO) {
		Employee employee = userService.findEmployeeByIdWithUserInfo(3);

		WebUser webUser = userService.toWebUser(employee);
		System.out.println(webUser);

		webUser.setLastName("Ken");
		System.out.println(webUser);
		userService.update(webUser);
	}


	private void findUserByUserName(UserDAO userDAO) {

		System.out.println(userDAO.findByUserName("Ian"));

	}

	private void findRoleByUserName(RoleDAO roleDAO) {

		System.out.println(roleDAO.findRoleByUserName("Ian"));
	}
}
