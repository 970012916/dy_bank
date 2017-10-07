package com.dayuanit.dymall.util;

import com.dayuanit.dymall.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmailUtil1 {


    private final Logger log = LoggerFactory.getLogger(SendEmailUtil1.class);
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    public void sendMail(User user) {
        log.info("----begin send email--------");
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(user.getEmail());
        StringBuffer sb = new StringBuffer();
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

        msg.setText(sb.toString());
        mailSender.send(msg);
        log.info("---------send email over------------");

    }

}
