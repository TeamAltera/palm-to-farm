package com.spring.smart_plant.commands;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spring.smart_plant.DAO.DAO;

public class MemberSearchCommand implements AjaxCommand{

	@Override
	public Map<String, Object> execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String,Object>();
		String id=(String)request.getAttribute("memberId");
		if(!id.isEmpty())
			map.put("result", new DAO().searchMember(id));
		else map.put("result", 2); //공백인 경우
		return map;
	}

}
