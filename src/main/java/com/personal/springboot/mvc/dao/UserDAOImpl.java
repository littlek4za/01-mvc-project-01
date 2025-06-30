package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.Role;
import com.personal.springboot.mvc.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    public UserDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public User findByUserName(String theUserName) {

        TypedQuery<User> theQuery = entityManager.createQuery(
                "FROM User u WHERE u.userName =:userName", User.class);
        theQuery.setParameter("userName", theUserName);

        User theUser = null;

        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {

        entityManager.persist(theUser);
    }

    public void update(User theUser) {

        entityManager.merge(theUser);
    }

    @Override
    public void deleteById(long userId){
        User user = entityManager.find(User.class, userId);
        if (user != null){
            entityManager.remove(user);
        }
    }

    public List<User> findAllUser(){

        TypedQuery<User> query = entityManager.createQuery(
                "FROM User u", User.class);
        List<User> users = query.getResultList();

        return users;
    }
}
