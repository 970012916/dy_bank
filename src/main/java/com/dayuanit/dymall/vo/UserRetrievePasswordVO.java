package com.dayuanit.dymall.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserRetrievePasswordVO {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message="密码不能为空")
    @Length(max = 10,min = 1,message = "密码长度在1-10之间")
    private String password;

    @NotNull(message="确认密码不能为空")
    @Length(max = 10,min = 1,message = "密码长度在1-10之间")
    private String passwordOther;

    @NotNull(message="邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

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
}
