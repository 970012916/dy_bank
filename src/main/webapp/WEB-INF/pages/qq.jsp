<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
        String basePath = request.getScheme()+"://"
        +request.getServerName()+":"+request.getServerPort()+path+"/";
        String code = request.getParameter("code");//获取QQ返回的code
        String state = request.getParameter("state");
        %>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'weibo.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="http://statics.2cto.com/js/jquery.min.js"></script>
    <script>
        $(function(){
        var code = "<%=code%>";
        var state = "<%=state%>";
        $.ajax({
        url:"http://localhost:8080/cms_manage/api/qqLogin",
        type:"post",
        data:{code:code,state:state},
        dataType:"json",
        success:function(result){
        result = JSON.stringify(result);
        console.log(result);
        //把返回的数据传给父窗口的隐藏域中
        window.opener.document.getElementById("qq").value = result;
        //授权完成后，关闭子窗口
        window.opener.closeQQWin();
        }
        });
        });
    </script>
</head>

<body>
    登录成功。
</body>
</html>