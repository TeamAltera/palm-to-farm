package com.spring.smart_plant.device.domain;

public class CommandDTO {
	private String middle; //공유기의 주소
	private String dest; //명령어가 도착해야 되는 목적지
	private int cmd; //명령어의 종류
	
	/**
	 * @param dest
	 * @param cmd
	 */
	public CommandDTO(String middle,String dest, int cmd) {
		super();
		this.middle=middle;
		this.dest = dest;
		this.cmd = cmd;
	}
	/**
	 * 
	 */
	public CommandDTO() {
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
}
