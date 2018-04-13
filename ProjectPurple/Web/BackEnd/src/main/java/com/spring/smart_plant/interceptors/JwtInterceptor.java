package com.spring.smart_plant.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.smart_plant.services.jwt.JwtService;

public class JwtInterceptor implements HandlerInterceptor{

	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
    private JwtService jwtService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		final String token = request.getHeader(HEADER_AUTH);
		if(token != null && jwtService.isUsable(token)){ //토큰 검사 수행
            return true;
        }
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //401
		return false;
	}
}
