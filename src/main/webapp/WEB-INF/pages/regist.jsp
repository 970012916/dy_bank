<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
	<title>大猿商城系统</title>
	<link rel="stylesheet" href="/css/register/reset.css" type="text/css">
	<link rel="stylesheet" href="/css/register/style.css" type="text/css">
</head>
<body>
<div id="head">
	<div class="wrap">
		<div class="logo">
			<a href="#"><img src="/img/default.jpg" width="50px" height="50px" ></a>
		</div>
		<h3>大猿商城系统</h3>
	</div>
</div>
<div class="register">
	<form id="registForm">
	<ul class="register_box">
		<li class="user_info"><span><b>*</b>用户名</span><input type="text" id="username" name="username" placeholder="邮箱/用户名/手机号" class="login_name" onkeyup="check()"><span id="checkResult"></span></li>
		
		<li class="user_info"><span><b>*</b>请设置密码</span><input type="password" id="password" name="password" class="login_password"></li>
		<li class="user_info"><span><b>*</b>确认密码码</span><input type="password" id="passwordOther" name="passwordOther" class="login_password"></li>
		<li class="user_info"><span><b>*</b>邮箱</span><input type="text" id="email" name="email" class="login_password"></li>
		<li class="user_info"><span><b>*</b>手机号码</span><input type="text" id="telNum" name="telNum" class="login_password"></li>
		<li class="user_info"><span><b>*</b>性别</span><input type="text" id="sex" name="sex" class="login_password" placeholder="男/女"></li>
		<li class="user_info"><span><b>*</b>生日</span><input type="text" id="birth" name="birth" class="login_password" placeholder="1990-12-30"></li>
		<li class="user_info"><span><b>*</b>验证码</span><input type="text" id="verifyCode" name="verifyCode" class="login_password"></li>
		<li><img id="img" src="/verify/getVerifyCode.do?"/>&nbsp;&nbsp;&nbsp;
       	<a href='#' onclick="javascript:changeImg()">看不清?</a><br></li>
		<li class="agree"><input type="checkbox" checked="checked" id="agreement"><label for="agreement">我已阅读并同意</label><a href="#">《用户注册协议》</a></li>
		<li class="submit"><input type="button" value="注册" onclick="regist();" class="submit_btn"></li>
	</ul>
	</form>
</div>
<div id="footer">
	<p>慕课简介|慕课公告| 招纳贤士| 联系我们|客服热线：400-675-1234</p>
	<p>Copyright © 2006 - 2014 慕课版权所有   京ICP备09037834号   京ICP证B1034-8373号   某市公安局XX分局备案编号：123456789123</p>
	<div class="credit_rating">
		<a href="#"><img src="/images/register/pj.jpg" alt="ä¿¡ç¨è¯ä»·"></a>
		<a href="#"><img src="/images/register/pj.jpg" alt="ä¿¡ç¨è¯ä»·"></a>
		<a href="#"><img src="/images/register/pj.jpg" alt="ä¿¡ç¨è¯ä»·"></a>
		<a href="#"><img src="/images/register/pj.jpg" alt="ä¿¡ç¨è¯ä»·"></a>
	</div>
</div>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
	<script type="text/javascript" src="/js/jquery-validate.min.js"></script>
	<script type="text/javascript" src="/js/messages_zh.js"></script>
	<script>
	$(document).ready(function() {
		changeImg();
		$("#registForm").validate({
		    rules: {
		    	username: {
		    		required:true
		    	},
		    	password: {
			        required: true,
			        minlength: 1,
			        maxlength: 6
			     },
				passwordOther:{
					 required: true,
				     minlength: 1,
		     	     maxlength: 6
				},
				email:{
					required: true,
					email: true
				},
				telNum:{
					required:true,
					minlength: 11,
		     	    maxlength: 11
				},
				sex:{
					required:true,
					minlength: 1,
		     	    maxlength: 1
				},
				
				birth:{
					dateISO:true
				},
				verifyCode:{
					required:true
				},
		
		    },
		    messages: {
		    	username:{
		    		required :"请输入登录名"
		    	} ,
		    	password: {
		    		required : "请输入密码",
		    		minlength : "密码最小长度为1",
		    		maxlength : "密码最大长度为6"
		    	},
		   		 passwordOther: {
		    		required : "请输入密码",
		    		minlength : "密码最小长度为1",
		    		maxlength : "密码最大长度为6"
		    	},
		    	email:{
		    		required:"请输入邮箱地址",
		    		email: "请输入有效的电子邮件地址"
		    	},
		    	telNum:{
					required:"请输入手机号码",
					minlength: "请输入正确的手机号码",
		     	    maxlength: "请输入正确的手机号码"
					
		    	},
		    	sex:{
					required:"请输入性别男/女",
					minlength:"请输入性别男/女",
		     	    maxlength: "请输入性别男/女"
				},
				birth:{
					dateISO:"日期格式不正确"
				},
				verifyCode:{
					required:"验证码不能为空"
				}
		    }
		});
	});
	
	function regist() {
		$.ajax({
			url:"/user/regist.do",
			type:"POST",
			async:true,
			data:{
				username:$("#username").val(),
				password:$("#password").val(),
				passwordOther:$("#passwordOther").val(),
				mobileNum:$("#telNum").val(),
				sexy:$("#sex").val(),
				birthday:$("#birth").val(),
				password:$("#password").val(),
				verifyCode:$("#verifyCode").val(),
				email:$("#email").val()
			},
        	timeout:5000,    
            dataType:"json",   
            beforeSend:function(xhr){
                console.log(xhr)
                console.log("发送前")
            },
            success:function(data,textStatus,jqXHR){	
            	
               if(data.flag != true) {
              	alert(data.message);
              	changeImg();
           		return;
           	   }
               
               window.location.href="/user/toLogin.do";  
            },
            error:function(xhr,textStatus){
                console.log('错误')
                console.log(xhr)
                console.log(textStatus)
            },
            complete:function(){
                console.log("结束")
            },
		});
	}
	
	function changeImg(){
		var img = document.getElementById("img"); 
		img.src = "/verify/getVerifyCode.do?date=" + new Date();
		
	}
	
	var xmlhttp;
	function check() {
		var name = document.getElementById("username").value;
		var url = "http://127.0.0.1:8080/user/checkUser.do?username="+name;
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange=checkResult; //响应函数
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
	}
	
	function checkResult() {
		if(xmlhttp.readyState == 4&& xmlhttp.status == 200) {
			$('#checkResult').html(xmlhttp.responseText);
		}
	}
	
	</script>

</body>
</html>