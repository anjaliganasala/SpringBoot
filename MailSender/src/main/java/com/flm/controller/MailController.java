package com.flm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.service.MailService;

@RestController
public class MailController {
	
	@Autowired
	MailService mailService;

	@GetMapping("/send")
	public String sendEMail(@RequestParam(name="toEmail") String toEmail) {
		return mailService.sendMail(toEmail);
		
	}
}
