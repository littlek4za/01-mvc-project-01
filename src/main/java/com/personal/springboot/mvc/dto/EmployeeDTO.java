package com.personal.springboot.mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.springboot.mvc.entity.Employee;
import com.personal.springboot.mvc.entity.User;
import jakarta.persistence.*;

public class EmployeeDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonProperty("user")
    private UserDTO userDTO;

    public EmployeeDTO(){

    }

    public EmployeeDTO(int id, String firstName, String lastName, String email, UserDTO userDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userDTO = userDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
