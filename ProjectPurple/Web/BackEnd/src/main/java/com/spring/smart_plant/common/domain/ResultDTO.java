package com.spring.smart_plant.common.domain;

import org.springframework.http.HttpStatus;

/*{
 * 	status:
 * 	msg:
 * 	data:
 * }
 * */
public class ResultDTO {
	public static final String SUCCESS_MSG="success";
	public static final String FAIL_MSG="fail";
	
	private HttpStatus status;
	private String msg;
	private Object data;
	
	public ResultDTO() {}
	
	public static ResultDTO createInstance(boolean state) {
		return state ? new ResultDTO().setSuccessStatus() : new ResultDTO().setFailStatus(); 
	}
	
	public ResultDTO setSuccessStatus() {
		status=HttpStatus.OK;
		msg=SUCCESS_MSG;
		return this;
	}
	
	public ResultDTO setFailStatus() {
		status=HttpStatus.INTERNAL_SERVER_ERROR;
		msg=SUCCESS_MSG;
		return this;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public ResultDTO setStatus(HttpStatus status) {
		this.status = status;
		return this;
	}

	public ResultDTO setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public ResultDTO setData(Object data) {
		this.data=data;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}
	
}
