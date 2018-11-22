package smart_farm_api.device.domain;

public class DeviceInfoDto {
	private int apCode;
	private int sfCode;
	private String ipInfo;
	/**
	 * 
	 */
	public DeviceInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param apInfo
	 * @param ipInfo
	 */
	public DeviceInfoDto(int apCode, int sfCode, String ipInfo) {
		super();
		this.apCode = apCode;
		this.sfCode = sfCode;
		this.ipInfo = ipInfo;
	}
	
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public String getIpInfo() {
		return ipInfo;
	}
	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
}
