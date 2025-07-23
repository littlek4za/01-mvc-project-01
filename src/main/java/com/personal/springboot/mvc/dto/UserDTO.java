package com.personal.springboot.mvc.dto;

import com.personal.springboot.mvc.entity.Role;
import jakarta.persistence.*;

import java.util.List;

public class UserDTO {

    private Long id;
    private String userName;
    private Boolean enable;
    private List<String> roles;

    public UserDTO(){
    }

    public UserDTO(Long id, String userName, Boolean enable, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.enable = enable;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
