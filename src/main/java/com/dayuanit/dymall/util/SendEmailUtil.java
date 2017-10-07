package com.dayuanit.dymall.util;

import com.dayuanit.dymall.domain.User;
import com.dayuanit.dymall.exception.DyMallException;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @ClassName: Sendmail
 * @Description: Sendmail类继承Thread，因此Sendmail就是一个线程类，这个线程类用于给指定的用户发送Email
 * @author: 孤傲苍狼
 * @date: 2015-1-12 下午10:43:48
 */

@Component
public class SendEmailUtil extends Thread{

	//private static final ConcurrentLinkedDeque<User> deque = new ConcurrentLinkedDeque<User>();
	//用于给用户发送邮件的邮箱
	private String from = "13912289837@163.com";
    //邮箱的用户名
	private String username = "13912289837";
    //邮箱的密码
	private String password = "gyy123456";
    //发送邮件的服务器地址
	private String host = "smtp.163.com";

	@Autowired
	private SimpleMailMessage templateMessage;

	public void submit(User user) {
		 //deque.offerLast(user);
		 sendEmail(user);
	}
	
	private static final Logger log = LoggerFactory.getLogger(SendEmailUtil.class);

	private Message createEmail(Session session,User user) throws AddressException, MessagingException {
		log.info("开始发验证邮件");
		//User user = deque.pollFirst();
		MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
	    message.setSubject("用户注册邮件");
		StringBuffer sb = new StringBuffer();

		SimpleMailMessage smm = new SimpleMailMessage(this.templateMessage);
		smm.setTo(user.getEmail().trim());

		//邮件内容
		sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
		sb.append("<a href=\"http://127.0.0.1:8080/user/activate.do?email=");
		sb.append(user.getEmail());
		sb.append("&validateCode=");
		sb.append(user.getVerifyCode());
		sb.append("\">http://127.0.0.1:8080/user/activate.do?email=");
		sb.append(user.getEmail());
		sb.append("&validateCode=");
		sb.append(user.getVerifyCode());
		sb.append("</a>");

		message.setContent(sb.toString(),"text/html;charset=UTF-8");
		message.saveChanges();
		return message;
		
	}
	

	public void sendEmail(User user){
		try{

	    	MailSSLSocketFactory sf = new MailSSLSocketFactory();
	    	sf.setTrustAllHosts(true);
	        Properties prop = new Properties();
	        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        prop.setProperty("mail.smtp.port", "465");
	        prop.setProperty("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.ssl.enable", "true");
	        prop.put("mail.smtp.ssl.socketFactory", sf);
	        prop.setProperty("mail.host", host);
	        prop.setProperty("mail.transport.protocol", "smtp");
	        prop.setProperty("mail.smtp.auth", "true");
	        Authenticator auth = new Authenticator() {
	        	@Override
	            public PasswordAuthentication getPasswordAuthentication() {  
	                // TODO Auto-generated method stub  
	                return new PasswordAuthentication(username, password);
	            }  
	        };  
	        Session session = Session.getInstance(prop,auth);
	        session.setDebug(true);
	        Transport ts = session.getTransport();
	        ts.connect(host, username, password);
	       
	       // ts.sendMessage(message, message.getAllRecipients());
	        Message message = createEmail(session,user);
	        Transport.send(message);
	        ts.close();
	    }catch (Exception e) {
	        log.info("发送注册邮件异常｛｝",e.getMessage(),e);
	        if(null != e ) {
	        	throw new DyMallException("注册失败");
			}
	    }
	}
	
}
