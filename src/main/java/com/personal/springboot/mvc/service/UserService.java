package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.entity.User;
import org.springframework.security.provisioning.UserDetailsManager;

public interface UserService extends UserDetailsManager {

    public User findByUserName(String userName);

}
