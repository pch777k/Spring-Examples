package com.pch777.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, Object> {

	private String passwordField;
	private String confirmPasswordField;

    @Override
    public void initialize(EqualPasswords constraint) {
    	passwordField = constraint.passwordField();
    	confirmPasswordField = constraint.confirmPasswordField();
    }
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			final String password = BeanUtils.getProperty(value, passwordField);
			final String confirmPassword = BeanUtils.getProperty(value, confirmPasswordField);
			
			boolean isValid = password.equals(confirmPassword);

			if (!isValid) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
						.addPropertyNode(confirmPasswordField).addConstraintViolation();
			}

			return isValid;
		} catch (Exception e) {
			System.out.println("Something went wrong.");
		}


		return true;
		
	}

}
