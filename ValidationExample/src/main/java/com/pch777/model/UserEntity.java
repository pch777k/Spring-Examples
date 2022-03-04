package com.pch777.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pch777.validation.EqualPasswords;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
@Entity
@EqualPasswords(passwordField = "password", confirmPasswordField = "confirmPassword")
public class UserEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "username must not be null")
	@Size(min=3, max=12, message = "password size must be between 3 and 12")
	private String username;
	
	@NotNull(message = "password must not be null")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
			 message = "password should has minimum eight characters, at least one letter, one number and one special character")
	private String password;
	
	private String confirmPassword;
	
	@Min(value = 18)
    private int age;

}
