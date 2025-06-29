package com.personal.springboot.mvc.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class WebUser {


    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9][a-zA-Z0-9!@#$%^&*]{0,28}[a-zA-Z0-9]$",
            message = "Username must be 1-30 characters, only letters, digits, and !@#$%^&*, and cannot start or end with a symbol"
    )
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Password must be at least 8 characters, include uppercase, lowercase, digit, and special character (!@#$%^&*)"
    )
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, max = 30, message = "First Name must be between 1 and 30 characters")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, max = 30, message = "Last Name must be between 1 and 30 characters")
    private String lastName;

    @Pattern(
            regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email address"
    )
    private String email;

    public WebUser(){

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
}
