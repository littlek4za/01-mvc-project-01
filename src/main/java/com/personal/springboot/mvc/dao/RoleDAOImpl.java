package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager entityManager;

    public RoleDAOImpl(EntityManager theEntityManager) {
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

    @Override
    public Role findRoleByName(String theRoleName) {
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "FROM Role WHERE name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }

    public List<Role> findAllRole() {
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "FROM Role", Role.class);
        return theQuery.getResultList();
    }

    public Role findRoleById(Long theRoleId) {
        TypedQuery<Role> theQuery = entityManager.createQuery(
                "FROM Role WHERE id=:roleId", Role.class);
        theQuery.setParameter("roleId", theRoleId);

        return theQuery.getSingleResult();
    }

    @Override
    public Long findIdByName(String roleName) {
        TypedQuery<Long> theQuery = entityManager.createQuery(
                "SELECT r.id FROM Role r WHERE r.name=:roleName", Long.class);
        theQuery.setParameter("roleName", roleName);

        return theQuery.getSingleResult();
    }
}
