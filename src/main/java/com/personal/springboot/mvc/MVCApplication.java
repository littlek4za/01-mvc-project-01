package com.personal.springboot.mvc;

import com.personal.springboot.mvc.dao.RoleDAO;
import com.personal.springboot.mvc.dao.UserDAO;
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
	public CommandLineRunner commandLineRunner(RoleDAO roleDAO, UserDAO userDAO){
		return runner ->{
			findRoleByUserName(roleDAO);
			findUserByUserName(userDAO);
		};
	}

	private void findUserByUserName(UserDAO userDAO) {

		System.out.println(userDAO.findByUserName("Ian"));

	}

	private void findRoleByUserName(RoleDAO roleDAO) {

		System.out.println(roleDAO.findRoleByUserName("Ian"));
	}
}
