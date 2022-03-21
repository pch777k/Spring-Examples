package com.pch777.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {

	private ConfirmationTokenRepository confirmationTokenRepository;
	
	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}
	
	public Optional<ConfirmationToken> getToken(String token) {
	    return confirmationTokenRepository.findByToken(token);
	}

	public void setConfirmedAt(String token) {
	    confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}
}
