<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" ; charset="utf-8">
<title>京西(JX.COM)-综合网购首选-正品低价、品质保障、配送及时、轻松购物！</title>
<link rel="stylesheet" href="/css/goods_introduce/reset.css"
	type="text/css">
<link rel="stylesheet" href="/css/goods_introduce/style.css"
	type="text/css">
</head>
<body class="grey">
	<div id="head">
		<div class="head_top">
			<div class="wrap clearfix">
				<div class="leftArea">
					<a href="#" id="collection">收藏京西</a>
				</div>
				<div class="rightArea">
					欢迎来到京西网！<a href="/user/toLogin.do">[登录]</a><a href="/user/toRegist.do">[免费注册]</a>
				</div>
			</div>
		</div>

		<div class="shop_car">
			<span class="car"><a href="/goods/toGoodsCar.do">购物车</a></span> <span class="num_text">0</span>
		</div>

	</div>
	</div>
	<div class="bread wrap">
		<a class="index" href="#">首页</a><em>&gt;</em><a href="#">平板电脑</a>&gt;<a
			href="#">Apple 苹果</a>&gt;<a href="#">MD531CH/A</a>
	</div>
	<div class="commodity_info wrap clearfix">
		<div class="info_left">
			<div class="commodity_img">
				<img src="/images/goods_introduce/sp.jpg" alt="商品图片">
			</div>
			<ul class="clearfix">
				<li class="imgOn"><img src="/images/goods_introduce/img_list.jpg" alt="缩略图"></li>
				<li><img src="/images/goods_introduce/img_list.jpg"
					alt="缩略图"></li>
				<li><img src="/images/goods_introduce/img_list.jpg"
					alt="缩略图"></li>
				<li><img src="/images/goods_introduce/img_list.jpg"
					alt="缩略图"></li>
				<li><img src="/images/goods_introduce/img_list.jpg"
					alt="缩略图"></li>
			</ul>
		</div>
		
		<div class="info_right" >
		
			<h3 class="shop_name" id="name" name="name"></h3>
			<dl class="price">
				<dt>京西价</dt>
				<dd >
					<b id="price" name="price">￥</b >
				</dd>
			</dl>
			<dl class="favourable">
				<dt>优惠</dt>
				<dd>
					<span>满换购</span>购ipad加价优惠够配件或USB充电插座
				</dd>
			</dl>
			<div class="selection">
				<dl class="address">
					<dt>送到</dt>
					<dd>
						<h3>
							北京市 海淀区 五环内<i></i>
						</h3>
						有货，可当日出库
					</dd>
				</dl>
				<dl class="color_select">
					<dt>选择颜色</dt>
					<dd class="clearfix">
						<a class="active" href="#">白色</a><a href="#">黑色</a><a href="#">灰色</a>
					</dd>
				</dl>
				<dl class="specification">
					<dt>选择规格</dt>
					<dd class="clearfix">
						<a class="active" href="#">WIFI 16G</a><a href="#">WIFI 64G</a><a
							href="#">WIFI 32G</a><a href="#">WIFI Cellular 64G</a><a href="#">WIFI
							Cellular 32G</a><a href="#">WIFI Cellular 16G</a>
					</dd>
				</dl>
				<dl>
					<dt>数量</dt>
					<dd class="clearfix">
						<div class="num_select">
							<span onclick="minus();">-</span> <input type="text" id="amount" name="amount" value="1"> <span onclick="add();">+</span>
						</div>
						<span class="limit_num">限购<b>9</b>件
						</span>
					</dd>
				</dl>
			</div>
			<div class="buy">
			<input type="hidden" name="" id="cartToken" value="${cart_token }">
				<h4 class="selected">
					已选择<span>“白色|WIFI 16G”</span>
				</h4>
				<div class="buy_btn">
					<a href="#" onclick="insertIntoCar()">加入购物车</a> <span class="ver_line"></span > <a href="#" onclick="pay();">立即购买</a>
				</div>
				<p class="notice">注意：此商品可提供普通发票，不提供增值税发票。</p>
			</div>
		</div>
	</div>

	<div id="footer">
		<p>慕课简介|慕课公告| 招纳贤士| 联系我们|客服热线：400-675-1234</p>
		<p>Copyright © 2006 - 2014 慕课版权所有 京ICP备09037834号 京ICP证B1034-8373号
			某市公安局XX分局备案编号：123456789123</p>
		<div class="credit_rating">
			<a href="#"><img src="/images/goods_introduce/pj.jpg"
				alt="信用评价"></a> <a href="#">
				<img src="/images/goods_introduce/pj.jpg" alt="信用评价"></a> <a
				href="#"><img src="/images/goods_introduce/pj.jpg"
				alt="信用评价"></a> <a href="#"><img
				src="/images/goods_introduce/pj.jpg" alt="信用评价"></a>
		</div>
	</div>


<script type="text/javascript" src="/js/jquery-mini.js"></script>

<script>

	var goodsId = "${goodsId}";
	getGoodsBrief();
	

	
	function add() {
		var amount = parseInt($('#amount').val());
		
		if(amount+1>9){
			return;
		}
		amount = amount + 1;
		$('#amount').val(amount);
	}
	
	function minus() {
		var amount = parseInt($('#amount').val());
		
		if(amount-1<1){
			return;
		}
		amount = amount - 1;
		$('#amount').val(amount);
	}

	function pay() {
		 $.ajax({
	            url:"/order/createOrderFromBuyNow.do",
	            type:"POST",
	            async:true,
	            data:{
	            	goodsId : goodsId,
	            	amount :$('#amount').val()
	            },
	            timeout:5000, //超时时间
				dataType:"json", //返回的数据格式：json/xml/html/script/jsonp/text
	            beforeSend:function(xhr){
	                console.log(xhr)
	                console.log("发送前")
	            },
	            success:function(data,textStatus,jqXHR){

	                if(!data.flag) {
	                    alert(data.message);
	                    return;
	                }
	                alert("success");
	                var orderId = data.data.id;
	                alert(orderId);
	               	window.location.href = "/order/toOrderPage.do?orderId=" + orderId;
	            },
	            error:function(xhr,textStatus){
	                console.log('错误')
	                console.log(xhr)
	                console.log(textStatus)
	            },
	            complete:function(){
	                console.log("结束")
	            }
	        });
	}

	function getGoodsBrief() {
		$.ajax({
			url : "/goods/getGoodsBrief.do",
			type : "POST",
			async : true,
			data : {
				id:goodsId
			},
			timeout : 5000, //超时时间
			dataType : "json", //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log("发送前")
			},
			success : function(data, textStatus, jqXHR) {
				if (!data.flag) {
					alert(data.message);
					changeImg();
					return;
				}
				var goods = data.data;
				$('#name').html(goods.goodsName);
				$('#price').html('￥' + goods.price);
		  
			},
			error : function(xhr, textStatus) {
				console.log("错误")
				console.log(xhr)
				console.log(textStatus)
			},
			complete : function() {
				console.log("结束")
			}
		});
	}
	
	function insertIntoCar() {
       
		$.ajax({
			url : "/goods/insertIntoCar.do",
			type : "POST",
			async : true,                  
			data : {
				goodsId:goodsId,
				goodsAmout:$("#amount").val(),
				cartToken : $('#cartToken').val()
			},
			timeout : 5000, //超时时间
			dataType : "json", //返回的数据格式：json/xml/html/script/jsonp/text
			beforeSend : function(xhr) {
				console.log(xhr)
				console.log("发送前")
			},
			success : function(data, textStatus, jqXHR) {
				if (!data.flag) {
					alert(data.message);
					return;
				}		  
			},
			error : function(xhr, textStatus) {
				console.log("错误")
				console.log(xhr)
				console.log(textStatus)
			},
			complete : function() {
				console.log("结束")
			}
		});
	}
</script>
</body>
</html>