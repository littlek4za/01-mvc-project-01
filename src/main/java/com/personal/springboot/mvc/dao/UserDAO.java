package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.User;

public interface UserDAO {

    User findByUserName(String userName);

    void save(User theUser);
}
