package com.personal.springboot.mvc.service;

import com.personal.springboot.mvc.entity.User;
import com.personal.springboot.mvc.user.WebUser;
import org.springframework.security.provisioning.UserDetailsManager;

public interface UserService extends UserDetailsManager {

    public User findByUserName(String userName);

    void save(WebUser webUser);

}
