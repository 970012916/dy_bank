<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8" >
	<title>大猿商城</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/css/index/reset.css" type="text/css">
	<link rel="stylesheet" href="/css/index/style.css" type="text/css">
</head>


<body>
<div id="head">
	<div class="head_top">
		<div class="wrap clearfix">
			<div class="leftArea">
				<c:if test="${not empty loginUser}">
					<img alt="" src="/file/readImage.do" height="50px" width="50px" onerror="this.src='/images/default.jpg'">
					<a href="/file/toUpload.do">上传头像</a> 
				</c:if> 
			</div>
			<shiro:guest>
			<div class="rightArea">欢迎来到大猿网！<a href="/qq/toQQLogin.do">[qq登录]</a><a href="/user/autoLogin.do">[登录]</a><a href="/user/toRegist.do">[免费注册]</a></div>
			</shiro:guest>
			
			<shiro:authenticated>
			<div class="rightArea">欢迎来到大猿网！<a href="/address/toAddress.do">[<shiro:principal/>]</a><a href="/user/logout.do">[注销]</a></div>
			</shiro:authenticated>>
			
		</div>
	</div>
	<div class="search">
	</div>
	<div class="nav">
		
	</div>
	<div class="banner">
	</div>
</div>

<div id="goodsShow">
	<div class="shopArea" v-for="xx in category">  <!-- //数组中的一个元素 -->
		<div class="wrap">
			<div class="shop_title">
				<h3>{{xx.categoryName}}</h3>                  <!-- //类目 --> 
			</div>
			<div class="main clearfix">
				<div class="shop_banner">
				
					<img src="/images/index/ad01.jpg" alt="shop_banner">
				</div>
				<div class="shop_list">
					<div class="shoplist_box clearfix">
					
						<div class="shopItem_top" v-for="(goods, index) in xx.goods" v-if="index < 4">
							<div class="shop_img">
							<a  v-bind:href="'/goods/toGoodsBref.do?action='+goods.id" target="">
								<img v-bind:src="'/goods/readImg.do?action='+goods.imgId" alt="商品">
							</a>
							</div>
							<h4>{{goods.goodsName}}</h4>
							<p>{{goods.price}}元</p>
						</div>
						<div class="shopItem_bottom" v-for="(goods, index) in xx.goods" v-if="index >= 4">
							<div class="shop_img">
							<a  v-bind:href="'/goods/toGoodsBref.do?action='+goods.id" target="">
								<img v-bind:src="'/goods/readImg.do?action='+goods.imgId" alt="商品">
							</a>
							</div>
							<div class="shop_text">
								<p>{{goods.goodsName}}</p>
								<span>￥{{goods.price}}</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="footer">
	<p>慕课简介|慕课公告| 招纳贤士| 联系我们|客服热线：400-675-1234</p>
	<p>Copyright © 2006 - 2014 慕课版权所有   京ICP备09037834号   京ICP证B1034-8373号   某市公安局XX分局备案编号：123456789123</p>
	<div class="credit_rating">
		<a href="#"><img src="/images/index/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/index/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/index/pj.jpg" alt="信用评价"></a>
		<a href="#"><img src="/images/index/pj.jpg" alt="信用评价"></a>
	</div>
</div>
<script type="text/javascript" src="/js/jquery-mini.js"></script>
<script type="text/javascript" src="/js/vue.js"></script>

<script type="text/javascript" charset="utf-8">

	var goodsShow = {}; //定义对象
	$(document).ready(function() {
		goodsShow = new Vue({  //定义VUE对象
			el : '#goodsShow', //goodsShow节点
			data : {
				category : [
				]
			},
		});
		
		getCategoryList();
	});
	
	function getCategoryList() {
		$.ajax({
			url:"/goods/getCategorys.do",
			type:"POST",
			async:true,
			data:{
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
	          goodsShow.category = data.data;
	          var len = data.data.length;
	          
	          for(var i=0;i<len;i++) {
	        	  var cg = goodsShow.category[i];
	        	  getGoodsList(cg,i);
	          }
	           
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
	
	
	function getGoodsList(cg,index) {
		$.ajax({
			url:"/goods/getGoods.do",
			type:"POST",
			async:true,
			data:{
				categoryId:cg.id
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
	           cg["goods"] = data.data;
	           goodsShow.category.splice(index,1,cg);
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

    function closeQQWin(){
        var result = $("#qq").val();
        if(result != ""){
            console.log(result);
            qqAuthWin.close();
        }else{
            console.log("值为空");
        }
    }

    function loginQQ(){
        window.open("https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=CLIENT_ID&state=register&redirect_uri=http://localhost:9090/logback/qq.jsp",
            'QQ授权登录','width=770,height=600,menubar=0,scrollbars=1,'+
            'resizable=1,status=1,titlebar=0,toolbar=0,location=1');
    }

</script>
</body>
</html>

