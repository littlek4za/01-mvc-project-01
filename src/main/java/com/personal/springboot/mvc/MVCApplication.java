package com.personal.springboot.mvc;

import com.personal.springboot.mvc.dao.RoleDAO;
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
	public CommandLineRunner commandLineRunner(RoleDAO roleDAO){
		return runner ->{
			findRoleByUserName(roleDAO);
		};
	}

	private void findRoleByUserName(RoleDAO roleDAO) {


		System.out.println(roleDAO.findRoleByUserName("Ian"));
	}

}
