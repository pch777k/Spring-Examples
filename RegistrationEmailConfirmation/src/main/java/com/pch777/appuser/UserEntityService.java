package com.pch777.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pch777.registration.token.ConfirmationToken;
import com.pch777.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserEntityService implements UserDetailsService {

	private UserEntityRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private ConfirmationTokenService confirmationTokenService; 
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found." , email)));
	}

	public String signUpUser(UserEntity user) {
		boolean userExists = userRepository
				.findByEmail(user.getEmail())
				.isPresent();
		if(userExists) {
			throw new IllegalStateException("Email already taken");
		}
		String encodedPassword = bCryptPasswordEncoder
				.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		userRepository.save(user);
				
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return token;
	}
	
	public void enableUser(String email) {
        userRepository.enableUser(email);
    }

}
