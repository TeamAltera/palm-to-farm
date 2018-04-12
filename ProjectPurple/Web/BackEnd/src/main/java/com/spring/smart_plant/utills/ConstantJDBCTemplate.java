package com.spring.smart_plant.utills;

import org.springframework.jdbc.core.JdbcTemplate;

public class ConstantJDBCTemplate {
	private static JdbcTemplate template;

	public static JdbcTemplate getTemplate() {
		return template;
	}

	public static void setTemplate(JdbcTemplate template) {
		ConstantJDBCTemplate.template = template;
	}
	
}
