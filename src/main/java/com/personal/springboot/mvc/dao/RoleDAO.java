package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Role;

import java.util.List;

public interface RoleDAO {

    List<Role> findRoleByUserName(String theUserName);

    public Role findRoleByName(String theRoleName);
}
