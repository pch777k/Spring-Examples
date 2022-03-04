package com.pch777.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({TYPE,FIELD})
@Constraint(validatedBy = {EqualPasswordsValidator.class})
public @interface EqualPasswords {

	String message() default "The passwords confirmation does not match";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
 
	String passwordField();

	String confirmPasswordField();
}
