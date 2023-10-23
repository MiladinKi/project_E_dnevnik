package com.iktpreobuka.e_dnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_dnevnik.entities.EmailEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;
	@Override
	public void sendSimpleMessage(EmailDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getText());
		emailSender.send(message);
		
	}

}
