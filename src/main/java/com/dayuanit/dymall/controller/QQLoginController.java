package com.dayuanit.dymall.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/qq")
public class QQLoginController {

    private static final Logger log = LoggerFactory.getLogger(QQLoginController.class);

    @RequestMapping("/toQQLogin")
    public void toQQLogin(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=utf-8");
        try {
            System.out.println("new Oauth().getAuthorizeURL(req)" + new Oauth().getAuthorizeURL(req));
            resp.sendRedirect(new Oauth().getAuthorizeURL(req));
        } catch (QQConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/getQQInfo")
    public void getToken(HttpServletRequest request, HttpServletResponse response) {
    	 response.setContentType("text/html; charset=utf-8");
         PrintWriter out = null;
         try {
        	 out = response.getWriter();
             AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
             String accessToken = null;
             String openID = null;
             long tokenExpireIn = 0L;//token 协议文件信息
             if (accessTokenObj.getAccessToken().equals("")) {
//                 我们的网站被CSRF攻击了或者用户取消了授权
//                 做一些数据统计工作
                 System.out.print("没有获取到响应参数");
             } else {
                 accessToken = accessTokenObj.getAccessToken();
                 System.out.println("accessToken is " +accessToken);
                 tokenExpireIn = accessTokenObj.getExpireIn();
                 System.out.println("tokenExpireIn is " +tokenExpireIn);

                 request.getSession().setAttribute("demo_access_token", accessToken);
                 request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

                 // 利用获取到的accessToken 去获取当前用的openid -------- start
                 OpenID openIDObj =  new OpenID(accessToken);
                 openID = openIDObj.getUserOpenID();

                 out.println("欢迎你，代号为 " + openID + " 的用户!");
                 request.getSession().setAttribute("demo_openid", openID);
                 // 利用获取到的accessToken 去获取当前用户的openid --------- end
                // out.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start </p>");
                 UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                 UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                 response.sendRedirect("/index.jsp");
             }
         } catch (QQConnectException e) {
         } catch (Exception e) {
			// TODO: handle exception
		}
     
    }
    

}
