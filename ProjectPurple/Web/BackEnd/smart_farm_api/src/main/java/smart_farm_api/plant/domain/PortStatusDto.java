package smart_farm_api.plant.domain;

public class PortStatusDto {
	private boolean status;
	
	/**
	 * 
	 */
	public PortStatusDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param status
	 */
	public PortStatusDto(boolean status) {
		super();
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
