package smart_farm_api.common;

import org.springframework.http.HttpStatus;

/*{
 * 	status:
 * 	msg:
 * 	data:
 * }
 * */
public class ResultDto {
	public static final String SUCCESS_MSG="success";
	public static final String FAIL_MSG="fail";
	
	private HttpStatus status;
	private String msg;
	private Object data;
	
	public ResultDto() {}
	
	public static ResultDto createInstance(boolean state) {
		return state ? new ResultDto().setSuccessStatus() : new ResultDto().setFailStatus(); 
	}
	
	public ResultDto setSuccessStatus() {
		status=HttpStatus.OK;
		msg=SUCCESS_MSG;
		return this;
	}
	
	public ResultDto setFailStatus() {
		status=HttpStatus.INTERNAL_SERVER_ERROR;
		msg=SUCCESS_MSG;
		return this;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public ResultDto setStatus(HttpStatus status) {
		this.status = status;
		return this;
	}

	public ResultDto setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public ResultDto setData(Object data) {
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
