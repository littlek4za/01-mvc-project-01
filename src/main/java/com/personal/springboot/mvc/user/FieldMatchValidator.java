package com.personal.springboot.mvc.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        // load field name
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context){
        boolean valid = true;

        try{
            //get field property
            Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            //implement logic to compare
            valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (Exception ignore){

        }

        if(!valid) {
            //add error message, link it to secondfield
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

}
