package com.spring.smart_plant.common.utills;

import com.spring.smart_plant.common.service.mail.MailSenderService;

public class ConstantMailService {
	private static MailSenderService mailService;

	public static MailSenderService getMailService() {
		return mailService;
	}

	public static void setMailService(MailSenderService mailService) {
		ConstantMailService.mailService = mailService;
	}
}
