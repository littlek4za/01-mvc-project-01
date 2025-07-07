package com.personal.springboot.mvc.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = FieldMatchValidator.class) // link to the validation logic class
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) // define where this annotation can be used
@Retention(RetentionPolicy.RUNTIME) // makes it available during runtime
public @interface FieldMatch {
    String message() default "The fields must match";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
    String first();
    String second();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        FieldMatch[] value(); //to hold multiple FieldMatch so this annotation can be used repeatedly
    }
}
