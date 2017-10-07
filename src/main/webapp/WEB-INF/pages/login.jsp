<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
	<title>商城系统</title>
	<link rel="stylesheet" href="/css/login/reset.css" type="text/css">
	<link rel="stylesheet" href="/css/login/style.css" type="text/css">
</head>
<body>
<div id="head">
	<div class="wrap">
		<div class="logo">
			<a href="#"><img width="50px" height="50px" src="/img/default.png" alt=""></a>
		</div>
		<h3>欢迎登录</h3>
	</div>
</div>
<div class="login">
	<form id="loginForm">
	<ul class="login_box">
		<li class="login_text">邮箱/用户名/已验证手机/</li>
		<li><input type="text" id="username" name="username" class="user_name"></li>
		<li class="login_text">密码</li>
		<li><input type="password" id="password" name="password" class="user_password"></li>
		<li class="login_check">
			<input type="checkbox" id="auto_login" name="auto_login"  value=""><label for="auto_login">自动登录</label>
		</li>
		<li class="login_check">
			<a href="/user/retrievePassword.do">忘记密码？</a>
		</li>
		
		<li><input class="login_btn" type="button" value="登录" onclick="login();"></li>
		<!-- <li class="login_text">使用合作账号登录</li>
		<li class="co_account">
			<a href="#">QQ</a><span>|</span><a href="#">网易</a><span>|</span><a href="#">人人</a><span>|</span><a href="#">奇虎360</a><span>|</span><a href="#">豆瓣</a><span>|</span><a href="#">开心</a><span>|</span><a href="#">搜狐</a><span>|</span><a href="#" class="more">更多</a>
		</li> -->
		
	</ul>
	</form>
</div>
<div>
<!--<div class="wrap register">
	<a href="#" class="free_register">免费注册&gt;&gt;</a></div>
<div id="footer">  -->

	<p>慕课简介|慕课公告| 招纳贤士| 联系我们|客服热线：400-675-1234</p>
	<p>Copyright © 2006 - 2014 慕课版权所有   京ICP备09037834号   京ICP证B1034-8373号   某市公安局XX分局备案编号：123456789123</p>
	<div class="credit_rating">
		<a href="#"><img src="/images/login/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/login/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/login/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/login/pj.jpg" alt="信用评价"></a>
	</div>
</div>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
	<script type="text/javascript" src="/js/jquery-validate.min.js"></script>
	<script type="text/javascript" src="/js/messages_zh.js"></script>
	
	<script>
	
	$(document).ready(function() {
		$("#loginForm").validate({
		    rules: {
		    	username: "required",
		    	password: {
			        required: true,
			        minlength: 1,
			        maxlength: 6
			      },
				passwordOhter:{
					required: true,
			        minlength: 1,
			        maxlength: 6
			      
					}
		    },
		    messages: {
		    	username: "请输入登录名",
		    	password: {
		    		required : "请输入密码",
		    		minlength : "密码最小长度为1",
		    		maxlength : "密码最大长度为6"
		    	}
		    }
		});
	});
	


	function autoLogin() {
	    var value = "on";

	 	if(document.getElementById("auto_login").checked) {

            alert("checkBox is checked");
            document.getElementById("auto_login").value = value;
		}
	}
	
	function login() {
		if (!$("#loginForm").valid()) {
			return false;
		}

		autoLogin();

		$.ajax({
			url:"/user/login.do",
			type:"POST",
			async:true,
			data:{
                username:$("#username").val(),
                password:$("#password").val(),
                autoLogin:$("#auto_login").val()
			},
        	timeout:5000,    //超时时间
            dataType:"json",    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend:function(xhr){
                console.log(xhr)
                console.log("发送前")
            },
            success:function(data,textStatus,jqXHR){	                    
               if(!data.flag) {
            	   alert(data.message);
              		return;
           	   }
               window.location.href="/user/toIndex.do";  
            },
            error:function(xhr,textStatus){
                console.log("错误")
                console.log(xhr)
                console.log(textStatus)
            },
            complete:function(){
                console.log("结束")
            },
		});
	}

	
	
	</script>
</body>
</html>