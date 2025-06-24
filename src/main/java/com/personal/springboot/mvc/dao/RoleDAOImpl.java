package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{

    @Autowired
    private EntityManager entityManager;

    public RoleDAOImpl (EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Role> findRoleByUserName(String theUserName) {
        // retrieve from database using username
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "SELECT r FROM User u JOIN u.roles r WHERE u.userName =:userName", Role.class);
        theQuery.setParameter("userName", theUserName);

        List<Role> theRoles = null;

        try {
            theRoles = theQuery.getResultList();
        } catch (Exception e) {
            theRoles = null;
        }

        return theRoles;
    }
}
