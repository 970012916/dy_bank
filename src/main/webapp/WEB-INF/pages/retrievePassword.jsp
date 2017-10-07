<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2017/9/3
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h3>找回密码</h3>
    </div>
</div>
<div class="login">
    <form id="retrievePasswordForm">
        <ul class="login_box">
            <li class="login_text">用户名</li>
            <li><input type="text" id="username" name="username" class="user_name"></li>
            <li class="login_text">密码</li>
            <li><input type="password" id="password" name="password" class="user_password"></li>
            <li class="login_text">确认密码</li>
            <li><input type="password" id="passwordOther" name="passwordOther" class="user_password"></li>
            <li class="login_text">邮箱</li>
            <li><input type="text" id="email" name="email" class="user_name"></li>
            <li><input class="login_btn" type="button" value="确认" onclick="retrievePassword();"></li>
        </ul>
    </form>
</div>

<script type="text/javascript" src="/js/jquery-mini.js"></script>
<script type="text/javascript" src="/js/jquery-validate.min.js"></script>
<script type="text/javascript" src="/js/messages_zh.js"></script>

<script>
    $(document).ready(function() {
        $("#retrievePasswordForm").validate({
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
                }

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
                }
            }
        });
    });

    function retrievePassword() {
        $.ajax({
            url:"/user/changePassword.do",
            type:"POST",
            async:true,
            data:{
                username:$("#username").val(),
                password:$("#password").val(),
                passwordOther:$("#passwordOther").val(),
                email:$("#email").val()
            },
            timeout:5000,
            dataType:"json",
            beforeSend:function(xhr){
                console.log(xhr);
                console.log("发送前");
            },
            success:function(data,textStatus,jqXHR){

                if(data.flag != true) {
                    alert(data.message);
                    return;
                }

                window.location.href="/user/toLogin.do";
            },
            error:function(xhr,textStatus){
                console.log('错误');
                console.log(xhr);
                console.log(textStatus);
            },
            complete:function(){
                console.log("结束");
            }
        });
    }


</script>


</body>
</html>
