package com.vadim.parser;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(String to, String subject, String body) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		
		try {
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body);
			javaMailSender.send(mail);
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
