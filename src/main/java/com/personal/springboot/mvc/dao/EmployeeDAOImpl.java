package com.personal.springboot.mvc.dao;

import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private EntityManager entityManager;

    public EmployeeDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public void update(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public void deleteById(int employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public List<Employee> findAllEmployee() {

        TypedQuery<Employee> query = entityManager.createQuery(
                "FROM Employee e", Employee.class);
        List<Employee> employees = query.getResultList();
        return employees;
    }

    public Employee findByIdWithUser(int theId) {

        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT e FROM Employee e " +
                        "JOIN FETCH e.user u " +
                        "LEFT JOIN FETCH u.roles " +
                        "WHERE e.id = :id", Employee.class
        );
        query.setParameter("id", theId);
        return query.getSingleResult();
    }
}
