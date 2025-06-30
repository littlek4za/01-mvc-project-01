package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.User;

import java.util.List;

public interface UserDAO {

    User findByUserName(String userName);

    void save(User theUser);

    void update(User theUser);

    void deleteById(long userId);

    List<User> findAllUser();
}
