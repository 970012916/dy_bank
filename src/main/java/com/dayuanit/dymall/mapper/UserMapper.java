package com.dayuanit.dymall.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.dayuanit.dymall.domain.User;

@Repository
public interface UserMapper {

	int addUser(@Validated User user);
	User getUser(String username);
	User getRetrievePassword(@Param("username") String username,@Param("email") String email);
	int updateUser(User user);
	User getUserByEmail(String email);
	int updatePassword(@Param("username") String username,@Param("password") String password);
}
