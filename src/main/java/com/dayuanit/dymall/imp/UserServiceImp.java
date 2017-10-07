package com.dayuanit.dymall.imp;

import com.dayuanit.dymall.dao.UserServiceDao;
import com.dayuanit.dymall.domain.User;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.mapper.UserMapper;
import com.dayuanit.dymall.util.SendEmailUtil1;
import com.dayuanit.dymall.vo.UserRetrievePasswordVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
@Service("userServiceDao")
public class UserServiceImp implements UserServiceDao{

	private final int NORMAL_STATUS = 1;
	private final int UNNORMAL_STATUS = 2;
	
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SendEmailUtil1 sendEmailUtil1;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int regist(User user) {
		
		User checkUser = userMapper.getUser(user.getUsername());
		if(null != checkUser) {
			throw new DyMallException("用户名已存在");
		}

		checkUser = userMapper.getUserByEmail(user.getEmail());
		if(null != checkUser) {
			throw new DyMallException("邮箱已经被注册，请使用其他邮箱");
		}
		int row = userMapper.addUser(user);
		
		if(1 != row) {
			throw new DyMallException("注册失败");
		}
		sendEmailUtil1.sendMail(user);
		
		return row;
	}

	@Override
	public User login(String username, String password) {
		
		User user = userMapper.getUser(username.trim());
		if(null == user ) {
			throw new DyMallException("用户名或者密码错误");
		}
		
		if(NORMAL_STATUS != user.getStatus()) {
			throw new DyMallException("该账户尚未更新或者激活");
		}
		
		password = DigestUtils.md5Hex(password);

		if(! user.getPassword().equals(password)) {
			
			throw new DyMallException("用户名或者密码错误");
		}
		return user;
	}

	@Override
	public int activateUser(String verifyCode, String email) {
		User user = userMapper.getUserByEmail(email);
		if(null == user ) {
			throw new DyMallException("用户尚未注册");
		}
		
		if (verifyCode == user.getVerifyCode()) {
			throw new DyMallException("非法激活码");
		}
		
		user.setStatus(NORMAL_STATUS);
		
		int row = userMapper.updateUser(user);
		if(1 != row ) {
			throw new DyMallException("激活失败");
		}
		return row;
	}


	@Override
	public void verifyUser(User user, BindingResult br) {
		if(br.hasErrors()) {
			List<FieldError> list = br.getFieldErrors();
			String message = "";
			for(FieldError fieldError : list) {
				message += fieldError.getDefaultMessage();
				message +=","; 
			}
			message +="请按照要求填写上述注册资料";
			throw new DyMallException(message);
		}
		
	}

	@Override
	public void changePassword(UserRetrievePasswordVO userRetrievePasswordVO) {

		String username = userRetrievePasswordVO.getUsername().trim();
		String email = userRetrievePasswordVO.getEmail().trim();

		User user = userMapper.getRetrievePassword(username,email);
		if(null == user) {
			throw new DyMallException("用户不存在");
		}
		String password = userRetrievePasswordVO.getPassword().trim();
		password = DigestUtils.md5Hex(password);
		int row = userMapper.updatePassword(username,password);
		if(1 != row) {
			throw new DyMallException("更改密码失败");
		}

	}

	@Override
	public int checkUsername(String username) {
		int row = 0;
		User user = userMapper.getUser(username);
		if(null != user) {
			row = 1;
			return row;
		}
		
		return row;
	}

	@Override
	public User getUser(String username) {
		User user = userMapper.getUser(username);
		return user;
	}


}
