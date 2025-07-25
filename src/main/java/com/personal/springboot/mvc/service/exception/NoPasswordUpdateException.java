package com.personal.springboot.mvc.service.exception;

public class NoPasswordUpdateException extends RuntimeException {
    public NoPasswordUpdateException(String message) {
        super(message);
    }
}
