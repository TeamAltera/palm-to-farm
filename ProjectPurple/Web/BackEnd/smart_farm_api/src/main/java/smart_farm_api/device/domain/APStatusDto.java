package smart_farm_api.device.domain;

import java.util.List;

public class APStatusDto {
	private int code;
	private List<Object> innerIp;
	/**
	 * 
	 */
	public APStatusDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param code
	 */
	public APStatusDto(int code) {
		super();
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<Object> getInnerIp() {
		return innerIp;
	}

	public void setInnerIp(List<Object> innerIp) {
		this.innerIp = innerIp;
	}


	
}
