package com.spring.smart_plant.common.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService{
	
	@Autowired
	private JavaMailSender mailSender;


	@Override
	public boolean send(String subject, String text, String from, String to, String filePath) {
		// TODO Auto-generated method stub
		MimeMessage msg=mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(msg,true,"UTF-8");
			helper.setSubject(subject);
			helper.setText(text,true);
			helper.setFrom(from);
			helper.setTo(to);
			
			mailSender.send(msg);
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
