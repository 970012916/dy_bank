package com.dayuanit.dymall.controller;

import com.dayuanit.dymall.util.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/verify")
public class VerifyCodeController {

	private static final int VERIFYSIZE = 4;
	@Autowired
	private VerifyCodeUtil verifyCodeUtil;
	
	@RequestMapping("/getVerifyCode")	
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// 下面3条记录是关闭客户端浏览器的缓冲区
	    	// 这3条语句都可以关闭浏览器的缓冲区，但是由于浏览器的版本不同，对这3条语句的支持也不同
	   		// 因此，为了保险起见，同时使用这3条语句来关闭浏览器的缓冲区  
	    	response.setHeader("Pragma", "No-cache"); 
			response.setHeader("Cache-Control", "no-cache"); 
	        response.setDateHeader("Expires", 0); 
	        response.setContentType("image/jpeg");
	        //生成随机字串 
	        String code = verifyCodeUtil.generateVerifyCode(VERIFYSIZE);
	      //删除以前的
	        HttpSession session = request.getSession(true); 
	        
	        session.setAttribute("verCode", code.toLowerCase()); 
	        ServletOutputStream sos = null;
	        try {
	        	sos=response.getOutputStream();
	 	        byte[] buff = verifyCodeUtil.outputImage(code);
	 	        response.setContentLength(buff.length);
	 	        sos.write(buff);
			} catch (IOException e) {
	        	e.printStackTrace();
			}finally {
				if(null != sos) {
					sos.close();
				}
			}
	 }
	
}
