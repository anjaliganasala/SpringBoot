package com.flm.service;

import com.flm.MailSenderApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailSenderApplication mailSenderApplication;

	@Autowired
	JavaMailSender javaMailSender;

    MailService(MailSenderApplication mailSenderApplication) {
        this.mailSenderApplication = mailSenderApplication;
    }
	
	public String sendMail(String toEMail) {
		
		SimpleMailMessage mail  = new SimpleMailMessage();
		
		mail.setFrom("anjaliganasala@gmail.com");
		mail.setTo("anjaliganasala3@gmail.com");
		
		if (toEMail != null && toEMail.contains("@")) {
	        mail.setCc(toEMail);
	        mail.setBcc(toEMail);
	    }
		
		mail.setSubject("looking for job");
		mail.setText("I am anjali Java full stack developer looking for opening");
		
		javaMailSender.send(mail);
		return "mail sent successfully";
		
	}
}
