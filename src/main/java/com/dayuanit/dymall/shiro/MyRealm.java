package com.dayuanit.dymall.shiro;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;

import com.dayuanit.dymall.dao.UserServiceDao;

public class MyRealm extends AuthenticatingRealm{

	private final String REALM_NAME = "my_realm";
	
	//在spring-shiro中配置，提供set方法
	private UserServiceDao userServiceDao;

	@Override
	public String getName() {
		return REALM_NAME;
	}
	

	public void setUserServiceDao(UserServiceDao userServiceDao) {
		this.userServiceDao = userServiceDao;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
		String username = uptoken.getUsername();
		String password = String.valueOf(uptoken.getPassword());
		userServiceDao.login(username, password);
		
		return new SimpleAuthenticationInfo(username, password, getName());
	}
	
	
	
}
