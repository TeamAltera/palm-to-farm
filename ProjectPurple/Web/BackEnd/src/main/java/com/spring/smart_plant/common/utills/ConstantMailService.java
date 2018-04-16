package com.spring.smart_plant.common.utills;

import com.spring.smart_plant.common.service.mail.MailService;

public class ConstantMailService {
	private static MailService mailService;

	public static MailService getMailService() {
		return mailService;
	}

	public static void setMailService(MailService mailService) {
		ConstantMailService.mailService = mailService;
	}
}
