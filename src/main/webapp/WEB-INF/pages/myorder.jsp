<%@page contentType="text/html; charset=utf-8" %>
<html>
	<jsp:include page="commons/head.jsp"></jsp:include>
	<head lang="en">
		<meta charset="utf-8" />
		<title>大猿商城-我的订单</title>
		<link rel="stylesheet" type="text/css" href="/css/order/public.css"/>
		<link rel="stylesheet" type="text/css" href="/css/order/myorder.css" />
	</head>
	<body>
		<!------------------------------head------------------------------>
		<jsp:include page="commons/head.jsp"></jsp:include> 
		<!------------------------------idea------------------------------>
		<div class="address mt">
			<div class="wrapper clearfix">
				<a href="#" class="fl">首页</a>
				<span>/</span>
				<a href="mygxin.html">个人中心</a>
				<span>/</span>
				<a href="myorderq.html" class="on">我的订单</a>
			</div>
		</div>
		
		<!------------------------------Bott------------------------------>
		<div class="Bott">
			<div class="wrapper clearfix">
			<jsp:include page="commons/left.jsp"></jsp:include>
				<div class="you fl" id="orderShow">
					<div class="my clearfix">
						<h2 class="fl">我的订单</h2>
						<a href="#" class="fl">请谨防钓鱼链接或诈骗电话，了解更多&gt;</a>
					</div>
					<div class="dlist clearfix">
						<ul class="fl clearfix" id="wa">
							<li class="on" id="-1"><a href="#2" >全部有效订单</a></li>
							<li id="5"><a href="#5" >待支付</a></li>
							<li id="2"><a href="#2" >待收货</a></li>
							<li id="3"><a href="#3" >已关闭</a></li>
						</ul>
						<form action="#" method="get" class="fr clearfix">
							<input type="text" name="" id="x" value="" placeholder="请输入商品名称、订单号" />
							<input type="button" name="" id="xx" value="" />
						</form>
					</div>
					<div class="dkuang deng" v-for="(order, index) in orders">
						<p class="one">{{order.status}}</p>
						<div class="word clearfix">
							<ul class="fl clearfix">
								<li>{{order.createTime}}</li>
								<li>{{order.userRealName}}</li>
								<li>订单号:{{order.id}}</li>
								<li>{{order.payChannel}}</li>
							</ul>
							<p class="fr">订单金额：<span>{{order.totalPrice}}</span>元</p>
						</div>
						<div class="shohou clearfix">
							<div v-for="goods in order.goods">
								<a href="#" class="fl"><img src="/images/order/g1.jpg"/></a>
								<p class="fl"><a href="#">{{goods.goodsName}}</a><a href="#">¥{{goods.goodsPrice}}×{{goods.goodsAmount}}</a></p>
							</div>	
							<p class="fr">
								<a href="myprod.html" v-if="order.status == '已付款'">待评价</a>
								<a href="#" v-if="order.status == '准备付款'" v-on:click="orderPay(index)">立即支付</a>
								<a href="orderxq.html">订单详情</a>
							</p>
						</div>
					</div>
					
					<div class="fenye clearfix">
						<a href="#" onclick="prePage();"><img src="/images/order/zuo.jpg"/></a>
						<a href="#" id="currentPage">1</a>
						<a href="#" onclick="nextPage();"><img src="/images/order/you.jpg"/></a>
					</div>
				</div>
			</div>
		</div>
		<!--返回顶部-->
		<jsp:include page="commons/right.jsp"></jsp:include>
		<!--footer-->
		<jsp:include page="commons/footer.jsp"></jsp:include>
		<script src="/js/jquery-mini.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/user.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/vue.js" type="text/javascript" charset="utf-8"></script>
		<script>

		var currentPage = 1;
		var currentStatus = -1;
		var orderData = {};
		var totalPageNum = 1;
		$(document).ready(function() {
			orderData = new Vue({
				el : '#orderShow',
				data : {
					orders : []
				},
				methods : {
					orderPay : function(index) {
						var order = this.orders[index];
						pay(order);
					}
				}
			});
			
			initOrderStatus();//一直监听着onclick事件
			loadOrders();
		});
		
		function pay(order) {
			var orderId = order.id;
			$.ajax({
	            url:'/order/payFromOrder.do',
	            type:'POST', //GET
	            async:true,    //或false,是否异步
	            data:{
	            	orderId : orderId
	            },
	            timeout:5000,    //超时时间
	            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	            beforeSend:function(xhr){
	                console.log(xhr)
	                console.log('发送前')
	            },
	            success:function(data,textStatus,jqXHR){
	            	 if (!data.flag) {
	                 	alert(data.message);
	                 	return;
	                 }
	            	 alert("订单生成OK，即将去支付");
					 window.location.href=data.data.payOrderUrl;
	             },
	            error:function(xhr,textStatus){
	                console.log('错误')
	                console.log(xhr)
	                console.log(textStatus)
	            },
	            complete:function(){
	                console.log('结束')
	            }
	        });
		}
		
		
		
		function initOrderStatus() {
			$('#wa li').click(function() {
				$(this).addClass("on").siblings().removeClass("on");
				currentStatus = $(this).attr("id");
				alert("index");
				alert(currentStatus);
				loadOrders();
			});
		}
		
		function loadOrders() {
			//alert(currentStatus);
			$.ajax({
	            url:'/order/listMyOrder.do',
	            type:'POST', //GET
	            async:true,    //或false,是否异步
	            data:{
	            	orderStatus : currentStatus,
                    currentPage : currentPage
	            },
	            timeout:5000,    //超时时间
	            dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	            beforeSend:function(xhr){
	                console.log(xhr)
	                console.log('发送前')
	            },
	            success:function(data,textStatus,jqXHR){
	            	 if (!data.flag) {
	                 	alert(data.message);
	                 	return;
	                 }
					orderData.orders = data.data.list;

					if(null != data.data.totalPageNum) {
                        totalPageNum = data.data.totalPageNum;
					}
                    currentPage = data.data.currentPage;
					//alert(totalPageNum);
					$('#currentPage').html(currentPage);
	             },
	            error:function(xhr,textStatus){
	                console.log('错误')
	                console.log(xhr)
	                console.log(textStatus)
	            },
	            complete:function(){
	                console.log('结束')
	            }
	        });

		}

		function prePage() {
			if(currentPage <= 1) {
			    return;
			}
            currentPage = currentPage - 1;
			loadOrders();
        }

        function nextPage() {
            if(currentPage >= totalPageNum) {
                return;
            }
            currentPage = currentPage + 1;
            loadOrders();
        }

		</script>
	</body>
</html>
