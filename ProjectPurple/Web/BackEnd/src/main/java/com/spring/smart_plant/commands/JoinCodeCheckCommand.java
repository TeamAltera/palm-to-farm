package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class JoinCodeCheckCommand implements AjaxCommand{

	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		System.out.println("code check");
		int sessionCode=-1;
		HttpSession session=request.getSession(false);
		if(session!=null) {
			Object obj=session.getAttribute("code");
			if(obj!=null) {
				sessionCode=(int)obj;
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("result", checkJoinCode((int)request.getAttribute("code"),sessionCode));
		return map;
	}
	
	private boolean checkJoinCode(int code, int sessionCode) {
		return code==sessionCode;
	}
}
