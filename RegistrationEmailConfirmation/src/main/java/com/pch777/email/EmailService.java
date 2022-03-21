package com.pch777.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

	private static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	private JavaMailSender mailSender;
	
	@Override
	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
			mimeMessageHelper.setText(email, true);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject("confirm your email");
			mimeMessageHelper.setFrom("hello@registration.com");
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			LOGGER.error("failed to send email",e);
			throw new IllegalStateException("failed to send email");
		}	
	}

}
