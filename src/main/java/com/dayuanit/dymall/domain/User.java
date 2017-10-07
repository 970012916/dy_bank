package com.dayuanit.dymall.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class User {

	private Integer id;
	
	@NotNull(message="用户名不能为空")
	private String username;
	
	@NotNull(message="密码不能为空")
	@Length(max = 10,min = 1,message = "密码长度在1-10之间")
	private String password;
	
	@Email(message="邮箱格式不正确")
	private String email;
	
	@NotNull
	@Length(max=11,min=11,message="手机号码格式不正确")
	private String mobileNum;
	
	@NotNull(message="性别填写不正确")
	private Integer sexy;
	
	private Integer status;
	
	@NotNull(message="生日填写不正确")
	@Past(message="生日填写不正确")
	private Date birthday;

	private Date createTime;
	private Date modifyTime;
	
	@NotNull(message="验证码不能为空")
	private String verifyCode;
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getSexy() {
		return sexy;
	}
	public void setSexy(Integer sexy) {
		this.sexy = sexy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	
}
