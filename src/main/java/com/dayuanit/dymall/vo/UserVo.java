package com.dayuanit.dymall.vo;
//vo中的date和sexy验证

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserVo {
	
	private String username;
	private String password;
	private String passwordOther;
	private String email;
	private String mobileNum;
	private String sexy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	private String verifyCode;
	
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordOther() {
		return passwordOther;
	}
	public void setPasswordOther(String passwordOther) {
		this.passwordOther = passwordOther;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getSexy() {
		return sexy;
	}
	public void setSexy(String sexy) {
		this.sexy = sexy;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	
	
}
