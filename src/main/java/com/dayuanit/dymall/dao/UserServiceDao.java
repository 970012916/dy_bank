package com.dayuanit.dymall.dao;

import com.dayuanit.dymall.vo.UserRetrievePasswordVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import com.dayuanit.dymall.domain.User;

public interface UserServiceDao {

	int regist(User user);
	User login(String username,String password);
	int activateUser(String verifyCode,String email);
	void verifyUser(@Validated User user,BindingResult br);
	void changePassword(UserRetrievePasswordVO userRetrievePasswordVO);
	int checkUsername(String username);
	User getUser(String username);
	
}
