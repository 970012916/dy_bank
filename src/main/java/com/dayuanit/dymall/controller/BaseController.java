package com.dayuanit.dymall.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.dayuanit.dymall.domain.User;
import com.dayuanit.dymall.exception.DyMallException;

public class BaseController {

//	public User getUser(HttpServletRequest req) {
//		User loginUser = (User)req.getSession(false).getAttribute("loginUser");
//		if(null == loginUser) {
//			throw new DyMallException("请登录");
//		}
//		return loginUser;
//	}
//	
	protected User getUser(HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user =(User)session.getAttribute("loginUser");
		if(null == user) {
			throw new DyMallException("请登录");
		}
		return user;
	}
}
