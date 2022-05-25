package com.douzone.mysite.vo;

public class GuestBookVo {
	private long no;
	private long rowNum;
	private String name;
	private String password;
	private String message;
	private String regdate;
	
	
	@Override
	public String toString() {
		return "GuestBookVo [no=" + no + ", rowNum=" + rowNum + ", name=" + name + ", password=" + password
				+ ", message=" + message + ", regdate=" + regdate + "]";
	}
	
	public long getRowNum() {
		return rowNum;
	}
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}
	
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
	
}
