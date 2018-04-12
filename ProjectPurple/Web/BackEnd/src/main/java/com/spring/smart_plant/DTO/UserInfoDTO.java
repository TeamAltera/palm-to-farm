package com.spring.smart_plant.DTO;

public class UserInfoDTO {
	private int userCode;
	private String memberId;
	private String pwd;
	private String email;
	private String userName;
	private int sfCnt;
	
	/**
	 * 
	 */
	public UserInfoDTO() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param userCode
	 * @param memberId
	 * @param pwd
	 * @param email
	 * @param phoneNum
	 * @param userName
	 * @param sfCnt
	 */
	public UserInfoDTO(int userCode, String memberId, String pwd, String email, String userName,
			int sfCnt) {
		super();
		this.userCode = userCode;
		this.memberId = memberId;
		this.pwd = pwd;
		this.email = email;
		this.userName = userName;
		this.sfCnt = sfCnt;
	}

	/**
	 * @param memberId
	 * @param pwd
	 */
	public UserInfoDTO(LoginDTO dto) {
		super();
		this.memberId = dto.getMemberId();
		this.pwd = dto.getPwd();
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSfCnt() {
		return sfCnt;
	}

	public void setSfCnt(int sfCnt) {
		this.sfCnt = sfCnt;
	}
	
	
}