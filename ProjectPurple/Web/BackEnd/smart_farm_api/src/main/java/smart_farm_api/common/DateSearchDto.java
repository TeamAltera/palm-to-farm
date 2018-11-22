package smart_farm_api.common;

public class DateSearchDto {
	private String date; //조회하고자 하는 날짜(YYYY-MM-DD)
	private int sfCode;//조회 하고자 하는 수경재배기 코드
	private int apCode;//조회 하고자 하는 공유기 코드
	private int userCode;//조회 하고자 하는 사용자 코드
	/**
	 * @param date
	 * @param sfCode
	 */
	public DateSearchDto(String date, int sfCode, int apCode) {
		super();
		this.date = date;
		this.sfCode = sfCode;
		this.apCode = apCode;
	}
	public DateSearchDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSfCode() {
		return sfCode;
	}
	public void setSfCode(int sfCode) {
		this.sfCode = sfCode;
	}
	public int getApCode() {
		return apCode;
	}
	public void setApCode(int apCode) {
		this.apCode = apCode;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	
}
