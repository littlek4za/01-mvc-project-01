package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Role;

import java.util.List;

public interface RoleDAO {

    List<Role> findRoleByUserName(String theUserName);

    Role findRoleByName(String theRoleName);

    List<Role> findAllRole();

    Role findRoleById(Long theRoleId);

    Long findIdByName(String roleName);
}
