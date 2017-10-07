package com.dayuanit.dymall.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayuanit.dymall.dao.UserServiceDao;
import com.dayuanit.dymall.domain.User;
import com.dayuanit.dymall.dto.AjaxResultDTO;
import com.dayuanit.dymall.enums.SexyEnum;
import com.dayuanit.dymall.exception.DyMallException;
import com.dayuanit.dymall.util.VerifyCodeUtil;
import com.dayuanit.dymall.vo.UserLoginVO;
import com.dayuanit.dymall.vo.UserRetrievePasswordVO;
import com.dayuanit.dymall.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	private final int UNVALIDATE_STATUS = 3;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceDao userServiceDao;
	
	@Autowired
	private VerifyCodeUtil verifyCodeUtil;
	
	@RequestMapping("/toRegist")
	public String toRegist() {
		return "regist";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "welcome";
	}
	
	
	@RequestMapping("/checkUser")
	public void checkUser(String username,HttpServletResponse resp) {
		int row = userServiceDao.checkUsername(username);
		String str ="";
		if(1 == row ) {
			str = "用户名已经注册";
		}
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/xml;charset=utf-8");
		OutputStream oStream = null;
		try {
			oStream = resp.getOutputStream();
			oStream.write(str.getBytes());
			oStream.flush();
		} catch (IOException e) {
			
		} finally {
			if(null != oStream) {
				try {
					oStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	@RequestMapping("/regist")
	@ResponseBody
	public AjaxResultDTO regist(@Validated UserVo userVo,BindingResult br) {

		Date birth = userVo.getBirthday();
		System.out.println("birth is  " + userVo.getBirthday());
		log.info("开始注册");
		try {
			if(null == userVo.getPasswordOther()) {
				throw new DyMallException("确认密码不能为空");
			}
			String password = userVo.getPassword().trim();
			String passwordOther = userVo.getPasswordOther().trim();		
			if(!password.equals(passwordOther)) {
				throw new DyMallException("两次输入的密码不一致");
			}
			User user = new User();
			//String birthday = userVo.getBirthday();
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date birth = null;
			try {
				//birth = sdf.parse(birthday);
				if(birth.getTime()>new Date().getTime()) {
					throw new DyMallException("亲，你的生日晚于当前日期");
				}
			} catch (Exception e) {
				log.info("时间格式问题｛｝",e.getMessage(),e);
				throw new DyMallException("生日格式有问题");
			}
			user.setBirthday(birth);
			log.info("****************" + user.getBirthday());
			user.setEmail(userVo.getEmail().trim());
			user.setMobileNum(userVo.getMobileNum().trim());
			password = DigestUtils.md5Hex(password);
			user.setPassword(password);
			user.setSexy(SexyEnum.getSexyEnum(userVo.getSexy()).getValue());
			user.setStatus(UNVALIDATE_STATUS);
			user.setUsername(userVo.getUsername().trim());
			String verifyCode = verifyCodeUtil.generateVerifyCode(6);
			verifyCode +=user.getUsername();
			verifyCode = DigestUtils.md5Hex(verifyCode);
			user.setVerifyCode(verifyCode);
			userServiceDao.verifyUser(user,br);
			userServiceDao.regist(user);
			return AjaxResultDTO.success();
		}catch (DyMallException e) {
			log.info("注册异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed(e.getMessage());
		} catch (Exception e) {
			log.info("注册异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed("注册失败");
		}
	}

	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public AjaxResultDTO login(@Validated UserLoginVO userLoginVO,BindingResult br ,HttpServletRequest req, HttpServletResponse resp) {
		log.info("登录开始");

		if(br.hasErrors()) {
			List<FieldError> list =br.getFieldErrors();
			String message = "";
			for(FieldError fieldError : list) {
				message += fieldError.getDefaultMessage();
				message +=",";
				message +="请按照要求填写上述注册资料";
				throw new DyMallException(message);
			}
		}

		String username = userLoginVO.getUsername().trim();
		String password = userLoginVO.getPassword();
		String autoLogin = userLoginVO.getAutoLogin();

		try {
			if(null == username) {
				throw new DyMallException("用户名不能为空");
			}
			
			if(null == password) {
				throw new DyMallException("密码不能为空");
			}
			//使用代理模式
			
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken uptoken = new UsernamePasswordToken();
			uptoken.setUsername(username);
			uptoken.setPassword(password.toCharArray());
			subject.login(uptoken);
			Session session = subject.getSession(true);
			User user = userServiceDao.getUser(username);
			
			if("on".equals(autoLogin)) {
				Cookie usernameCookie = new Cookie("username",username);
				Cookie passwordCookie = new Cookie("password",password);
				resp.addCookie(usernameCookie);
				resp.addCookie(passwordCookie);
			}
			session.setAttribute("loginUser", user);
			//req.getSession(true).setAttribute("loginUser", user);
			return AjaxResultDTO.success();
		} catch (DyMallException e) {
			log.info("登录异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed(e.getMessage());
		} catch (Exception e) {
			log.info("登录异常{}",e.getMessage(),e);
			return AjaxResultDTO.failed("登录失败");
		}
		
	}

	@RequestMapping("/autoLogin")
	public String autoLoign(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		String username = null;
		String password = null;
		for(Cookie cookie :cookies) {
			if("username".equals(cookie.getName())){
				username = cookie.getValue();
			}
			if("password".equals(cookie.getName())){
				password = cookie.getValue();
			}
		}

		if(null != username && null != password ) {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken uptoken = new UsernamePasswordToken();
			uptoken.setUsername(username);
			uptoken.setPassword(password.toCharArray());
			subject.login(uptoken);
			Session session = subject.getSession(true);
			User user = userServiceDao.getUser(username);
			
			//User user = userServiceDao.login(username,password);
			if(null == user) {
				return "login";
			}
			session.setAttribute("loginUser", user);
			//req.getSession(true).setAttribute("loginUser",user);
			return "redirect:/";
		}else {
			return "login";
		}

	}

	@RequestMapping("/activate")
	public String activateUser(HttpServletRequest req) {
		String email = req.getParameter("email");
		String verifyCode = req.getParameter("validateCode");
		int row = userServiceDao.activateUser(verifyCode, email);
		if(1 != row ) {
			throw new DyMallException("激活失败");
		}
		return "login";
	}

	@RequestMapping("/toIndex")
	public String toIndex() {
		return  "redirect:/index.jsp";
	}

	@RequestMapping("/retrievePassword")
	public String toRetrievepassword() {
		return "retrievePassword";
	}

	@RequestMapping("/changePassword")
	@ResponseBody
	public AjaxResultDTO changePassword(@Validated UserRetrievePasswordVO userRetrievePasswordVO,BindingResult br) {

		log.info("开始找回密码");
		while (br.hasErrors()) {
			String msg = "";
			List<FieldError> list = br.getFieldErrors();

			for(FieldError fieldError : list) {
				msg +=fieldError.getDefaultMessage();
				msg +=",";
			}
			msg += "请按上述要求完成资料填写";
			return AjaxResultDTO.failed(msg);
		}
		String password = userRetrievePasswordVO.getPassword();
		String passwordOther = userRetrievePasswordVO.getPasswordOther();
		try {
			if(! password.equals(passwordOther)) {
				throw new DyMallException("两次密码不一致");
			}
			userServiceDao.changePassword(userRetrievePasswordVO);
			return  AjaxResultDTO.success();
		}catch (DyMallException e) {
			log.info("更改密码失败{}",e.getMessage(),e);
			return AjaxResultDTO.failed(e.getMessage());
		}
	}
		
	@RequestMapping("/logout")
	private String logout(HttpServletRequest req,HttpServletResponse resp) {
//		req.getSession(false).invalidate();
//		Cookie[] cookies = req.getCookies();
//		for (Cookie cookie : cookies) {
//			cookie.setMaxAge(0);
//		}
//		return "redirect:/index.jsp";
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies) {
			log.info("cookie name " +cookie.getName());
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
		}
		
		return "redirect:/";
	}
	
}
