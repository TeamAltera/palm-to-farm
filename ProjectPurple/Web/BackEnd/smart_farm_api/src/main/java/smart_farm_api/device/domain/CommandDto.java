package smart_farm_api.device.domain;

public class CommandDto {
	private String middle; //공유기의 주소
	private String dest; //명령어가 도착해야 되는 목적지
	private int cmd; //명령어의 종류
	private int sfCode;
	private int apCode;
	private String usedIp;
	private CommandDataDto optData;
	
	/**
	 * @param dest
	 * @param cmd
	 */
	public CommandDto(String middle,String dest, int cmd, int sfCode, int apCode, String usedIp) {
		super();
		this.middle=middle;
		this.dest = dest;
		this.cmd = cmd;
		this.sfCode=sfCode;
		this.apCode=apCode;
		this.usedIp=usedIp;
	}
	/**
	 * 
	 */
	public CommandDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	public String getUsedIp() {
		return usedIp;
	}
	public void setUsedIp(String usedIp) {
		this.usedIp = usedIp;
	}
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public CommandDataDto getOptData() {
		return optData;
	}
	public void setOptData(CommandDataDto optData) {
		this.optData = optData;
	}
	
}
