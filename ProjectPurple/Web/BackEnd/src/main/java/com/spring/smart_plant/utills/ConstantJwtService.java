package com.spring.smart_plant.utills;

import com.spring.smart_plant.services.jwt.JwtService;

public class ConstantJwtService {
	private static JwtService jwtService;

	public static JwtService getJwtService() {
		return jwtService;
	}

	public static void setJwtService(JwtService jwtService) {
		ConstantJwtService.jwtService = jwtService;
	}
	
}
