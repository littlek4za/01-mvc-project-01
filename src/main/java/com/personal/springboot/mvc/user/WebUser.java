package com.personal.springboot.mvc.user;

import com.personal.springboot.mvc.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.util.Collection;
import java.util.List;

public class WebUser {


    @NotNull(message = "is required", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, message = "is required", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9][a-zA-Z0-9!@#$%^&*]{0,28}[a-zA-Z0-9]$",
            message = "Username must be 1-30 characters, only letters, digits, and !@#$%^&*, and cannot start or end with a symbol",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String userName;

    @NotNull(message = "is required", groups = {OnCreate.class})
    @Size(min = 1, message = "is required", groups = {OnCreate.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Password must be at least 8 characters, include uppercase, lowercase, digit, and special character (!@#$%^&*)",
            groups = {OnCreate.class}
    )
    private String password;

    @NotNull(message = "is required", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 30, message = "First Name must be between 1 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;

    @NotNull(message = "is required", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 30, message = "Last Name must be between 1 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String lastName;

    @Pattern(
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email address",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String email;

    @NotNull(message = "is required", groups = {OnUpdate.class})
    private List<Long> roleIds;

    private Boolean enabled;

    private int employeeId;

    public WebUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roleIds=" + roleIds +
                ", enabled=" + enabled +
                ", employeeId=" + employeeId +
                '}';
    }
}
