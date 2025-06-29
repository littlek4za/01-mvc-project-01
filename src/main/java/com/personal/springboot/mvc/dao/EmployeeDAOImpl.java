package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Employee;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private EntityManager entityManager;

    public EmployeeDAOImpl (EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void save(Employee employee) {
        entityManager.persist(employee);
    }
}
