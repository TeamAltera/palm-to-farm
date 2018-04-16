package com.spring.smart_plant.common.service.jwt;

import java.util.Map;

public interface JwtService {
	public <T> String create(String key, T data, String subject);
	
	public boolean isUsable(String jwt);
	
	public Map<String, Object> get(String key);
}
