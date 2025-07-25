package com.personal.springboot.mvc.rest.exception;

import com.personal.springboot.mvc.service.exception.EmployeeNotFoundException;
import com.personal.springboot.mvc.service.exception.NoPasswordUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<EmployeeRestErrorResponse> handleDuplicateUserName(DuplicateUsernameException e) {
        EmployeeRestErrorResponse error = new EmployeeRestErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<EmployeeRestErrorResponse> handleDuplicateEmail(DuplicateEmailException e) {
        EmployeeRestErrorResponse error = new EmployeeRestErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<EmployeeRestErrorResponse> handleException(Exception e) {
        EmployeeRestErrorResponse error = new EmployeeRestErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Unexpected Error: " + e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EmployeeRestErrorResponse> handleEmployeeNotFoundException(Exception e) {
        EmployeeRestErrorResponse error = new EmployeeRestErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Unexpected Error: " + e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoPasswordUpdateException.class)
    public ResponseEntity<EmployeeRestErrorResponse> handleNoPasswordUpdateException(Exception e) {
        EmployeeRestErrorResponse error = new EmployeeRestErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Unexpected Error: " + e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
