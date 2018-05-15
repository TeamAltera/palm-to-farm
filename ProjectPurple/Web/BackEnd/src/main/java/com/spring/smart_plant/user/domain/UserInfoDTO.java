package com.spring.smart_plant.user.domain;

/*{
 * 	userCode:
 * 	pwd:
 * 	email:
 * 	firstName:
 * 	secondName:
 * 	sfCnt:
 * }
 * */
public class UserInfoDTO {
	private int userCode;
	private String pwd;
	private String email;
	private String firstName;
	private String secondName;
	private int sfCnt;
	private int block;
	
	/**
	 * 
	 */
	public UserInfoDTO() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param userCode
	 * @param email
	 * @param pwd
	 * @param email
	 * @param phoneNum
	 * @param userName
	 * @param sfCnt
	 */
	public UserInfoDTO(int userCode,String pwd, String email, int sfCnt, String firstName, String secondName, int block) {
		super();
		this.userCode = userCode;
		this.pwd = pwd;
		this.email = email;
		this.sfCnt = sfCnt;
		this.firstName=firstName;
		this.secondName=secondName;
		this.block=block;
	}

	/**
	 * @param email
	 * @param pwd
	 */
	public UserInfoDTO(LoginDTO dto) {
		super();
		this.email = dto.getEmail();
		this.pwd = dto.getPwd();
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getSfCnt() {
		return sfCnt;
	}

	public void setSfCnt(int sfCnt) {
		this.sfCnt = sfCnt;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}
}