package com.spring.smart_plant.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	//Controller 호출전 발생
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		boolean result=false;
		String uri=request.getRequestURI().toString().substring(request.getContextPath().length());
		HttpSession session= request.getSession(false);
		System.out.println("request: "+uri);
		if(uri.equals("/login")) {
			if(session!=null && session.getAttribute("userInfo")!=null) {
				System.out.println("[go main]");
				response.sendRedirect("main");
			}
			else {
				System.out.println("[go login]");
				result=true;
			}
		}
		else if(uri.equals("/main") || uri.equals("/sensing_data")){
			if(session!=null && session.getAttribute("userInfo")!=null) {
				System.out.println("[go main] or [go sensing_data]");
				result=true;
			}
			else {
				System.out.println("[go login]");
				response.sendRedirect("/smart_plant/login");
			}
		}
		return result;
		//return true;
	}
}
