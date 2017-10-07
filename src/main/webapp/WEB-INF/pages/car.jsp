<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="utf-8" />
		<title>cart</title>
		<link rel="stylesheet" type="text/css" href="/css/order/public.css"/>
		<link rel="stylesheet" type="text/css" href="/css/order/proList.css" />
	</head>
	<body>
		<!--------------------------------------cart--------------------->
		<div class="head ding">
			<div class="wrapper clearfix">
				<div class="clearfix" id="top">
					<h1 class="fl"><a href="index.html"><img src="/images/order/logo.png"/></a></h1>
					<div class="fr clearfix" id="top1">
						<p class="fl">
							<a href="/user/toLogin.do" id="login">登录</a>
							<a href="/user/toRegist.do" id="reg">注册</a>
						</p>
						<form action="#" method="get" class="fl">
							<input type="text" placeholder="搜索" />
							<input type="button" />
						</form>
						<div class="btn fl clearfix">
							<a href="mygxin.html"><img src="/images/order/grzx.png"/></a>
							<a href="#" class="er1"><img src="/images/order/ewm.png"/></a>
							<a href="cart.html"><img src="/images/order/gwc.png"/></a>
							<p><a href="#"><img src="/images/order/smewm.png"/></a></p>
						</div>
					</div>
				</div>
				<ul class="clearfix" id="bott">
					<li><a href="index.html">首页</a></li>
					<li>
						<a href="#">所有商品</a>
						<div class="sList">
							<div class="wrapper  clearfix">
								<a href="paint.html">
									<dl>
										<dt><img src="/images/order/nav1.jpg"/></dt>
										<dd>浓情欧式</dd>
									</dl>
								</a>
								<a href="paint.html">
									<dl>
										<dt><img src="/images/order/nav2.jpg"/></dt>
										<dd>浪漫美式</dd>
									</dl>
								</a>
								<a href="paint.html">
									<dl>
										<dt><img src="/images/order/nav3.jpg"/></dt>
										<dd>雅致中式</dd>
									</dl>
								</a>
								<a href="paint.html">
									<dl>
										<dt><img src="/images/order/nav6.jpg"/></dt>
										<dd>简约现代</dd>
									</dl>
								</a>
								<a href="paint.html">
									<dl>
										<dt><img src="/images/order/nav7.jpg"/></dt>
										<dd>创意装饰</dd>
									</dl>
								</a>
							</div>
						</div>
					</li>
					<li>
						<a href="flowerDer.html">装饰摆件</a>
						<div class="sList2">
							<div class="clearfix">
								<a href="proList.html">干花花艺</a>
								<a href="vase_proList.html">花瓶花器</a>
							</div>
						</div>
					</li>
					<li>
						<a href="decoration.html">布艺软饰</a>
						<div class="sList2">
							<div class="clearfix">
								<a href="zbproList.html">桌布罩件</a>
								<a href="bzproList.html">抱枕靠垫</a>
							</div>
						</div>
					</li>
					<li><a href="paint.html">墙式壁挂</a></li>
					<li><a href="perfume.html">蜡艺香薰</a></li>
					<li><a href="idea.html">创意家居</a></li>
				</ul>
			</div>
		</div>
		<div class="cart mt">
			<!-----------------logo------------------->
			<!--<div class="logo">
				<h1 class="wrapper clearfix">
					<a href="index.html"><img class="fl" src="img/temp/logo.png"></a>
					<img class="top" src="img/temp/cartTop01.png">
				</h1>
			</div>-->
			<!-----------------site------------------->
			<div class="site">
				<p class=" wrapper clearfix">
					<span class="fl">购物车</span>
					<img class="top" src="/images/order/temp/cartTop01.png">
					<a href="index.html" class="fr">继续购物&gt;</a>
				</p>
			</div>
			<!-----------------table------------------->
			<div class="table wrapper" id="goodsShow">
				<div class="tr">
					<div>商品</div>
					<div>单价</div>
					<div>数量</div>
					<div>小计</div>
					<div>操作</div>
				</div>
				
				
				<div class="th"  v-for="(car,index) in shoppingCarList">
					<div class="pro clearfix">
						<label class="fl">
							<!--v-bind:id 提供一个id v-on:click提供一个click事件-->
							<input type="checkBox" v-bind:id="index" v-on:click="goodsChecked"/>
    						<span></span>
						</label>
						<a class="fl" href="#">
							<dl class="clearfix">
								<dt class="fl"><img src="/images/order/temp/cart01.jpg"></dt>
								<dd class="fl">
									<p>{{car.goods.goodsName}}</p>
					
								</dd>
							</dl>
						</a>
					</div>
					<div class="price">￥{{car.goods.price}}</div>
					<div class="number">
						<p class="num clearfix">
							<img class="fl sub" src="/images/order/temp/sub.jpg" v-on:click="subNum(index)">
							<span class="fl">{{car.goodsAmout}}</span>
							<img class="fl add" src="/images/order/temp/add.jpg" v-on:click="addNum(index)">
						</p>
					</div>
					<div class="price sAll">￥{{car.goods.price}}</div>
					<div class="price"><a class="del" href="#2">删除</a></div>
				</div>

				<div class="goOn">空空如也~<a href="index.html">去逛逛</a></div>
				<div class="tr clearfix">
					<label class="fl">
						<input class="checkAll" type="checkbox" id="selectAll" onclick="selectAll();"/>
						<span></span>
					</label>
					<p class="fr">
						<span>共<small id="sl">0</small>件商品</span>
						<span>合计:&nbsp;<small id="totalPrice">￥0.00</small></span>
						<a href="###" class="count" id="settlement">结算</a>
					</p>
				</div>
			</div>
		</div>
		<div class="mask"></div>
		<div class="tipDel">
			<p>确定要删除该商品吗？</p>
			<p class="clearfix">
				<a class="fl cer" href="#">确定</a>
				<a class="fr cancel" href="#">取消</a>
			</p>
		</div>
		<!--返回顶部-->
		<div class="gotop">
			<a href="cart.html">
			<dl>
				<dt><img src="/images/order/gt1.png"/></dt>
				<dd>去购<br />物车</dd>
			</dl>
			</a>
			<a href="#" class="dh">
			<dl>
				<dt><img src="/images/order/gt2.png"/></dt>
				<dd>联系<br />客服</dd>
			</dl>
			</a>
			<a href="mygxin.html">
			<dl>
				<dt><img src="/images/order/gt3.png"/></dt>
				<dd>个人<br />中心</dd>
			</dl>
			</a>
			<a href="#" class="toptop" style="display: none;">
			<dl>
				<dt><img src="/images/order/gt4.png"/></dt>
				<dd>返回<br />顶部</dd>
			</dl>
			</a>
			<p>400-800-8200</p>
		</div>
		<!--footer-->
		<div class="footer">
			<div class="top">
				<div class="wrapper">
					<div class="clearfix">
						<a href="#2" class="fl"><img src="/images/orderimg/foot1.png"/></a>
						<span class="fl">7天无理由退货</span>
					</div>
					<div class="clearfix">
						<a href="#2" class="fl"><img src="/images/order/foot2.png"/></a>
						<span class="fl">15天免费换货</span>
					</div>
					<div class="clearfix">
						<a href="#2" class="fl"><img src="/images/order/foot3.png"/></a>
						<span class="fl">满599包邮</span>
					</div>
					<div class="clearfix">
						<a href="#2" class="fl"><img src="/images/order/foot4.png"/></a>
						<span class="fl">手机特色服务</span>
					</div>
				</div>
			</div>
			<p class="dibu">最家家居&copy;2013-2017公司版权所有 京ICP备080100-44备0000111000号<br />
			违法和不良信息举报电话：188-0130-1238，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试</p>
		</div>
		<!----------------mask------------------->
		<div class="mask"></div>
		<!-------------------mask内容------------------->
		<div class="proDets">
			<img class="off" src="/images/order/temp/off.jpg" />
			<div class="proCon clearfix">
				<div class="proImg fr">
					<img class="list" src="/images/order/temp/proDet.jpg"  />
					<div class="smallImg clearfix">
						<img src="/images/order/temp/proDet01.jpg" data-src="/images/order/temp/proDet01_big.jpg">
						<img src="img/temp/proDet02.jpg" data-src="/images/order/temp/proDet02_big.jpg">
						<img src="/images/order/temp/proDet03.jpg" data-src="/images/order/temp/proDet03_big.jpg">
						<img src="/images/order/temp/proDet04.jpg" data-src="/images/order/temp/proDet04_big.jpg">
					</div>
				</div>
				<div class="fl">
					<div class="proIntro change">
						<p>颜色分类</p>
						<div class="smallImg clearfix">
							<p class="fl on"><img src="/images/order/temp/prosmall01.jpg" alt="白瓷花瓶+20支快乐花" data-src="/images/order/temp/proBig01.jpg"></p>
							<p class="fl"><img src="/images/order/temp/prosmall02.jpg" alt="白瓷花瓶+20支兔尾巴草" data-src="/images/order/temp/proBig02.jpg"></p>
							<p class="fl"><img src="/images/order/temp/prosmall03.jpg" alt="20支快乐花" data-src="/images/order/temp/proBig03.jpg"></p>
							<p class="fl"><img src="/images/order/temp/prosmall04.jpg" alt="20支兔尾巴草" data-src="/images/order/temp/proBig04.jpg"></p>
						</div>
					</div>
					<div class="changeBtn clearfix">
						<a href="#2" class="fl"><p class="buy">确认</p></a>
						<a href="#2" class="fr"><p class="cart">取消</p></a>
					</div>
				</div>
			</div>
		</div>
		<div class="pleaseC">
			<p>请选择宝贝</p>
			<img class="off" src="img/temp/off.jpg" />
		</div>
	<script src="/js/jquery-1.12.4.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/public.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/pro.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/cart.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/js/jquery-mini.js"></script>
	<script type="text/javascript" src="/js/vue.js"></script>
	<script type="text/javascript" src="/js/json2.js"></script>
	<script>
	
	var pageNum = 1;
	var totalPageNum;
	var goodsShow = {}; //定义对象
	$(document).ready(function() {
		goodsShow = new Vue({  //定义VUE对象
			el : '#goodsShow', //goodsShow节点
			data : {
				shoppingCarList : [
				]
			},
			methods : {
				subNum : function(index){
					var car = this.shoppingCarList[index];
					if(car.goodsAmout<=1) {
						return;
					}
					car.goodsAmout-=1;
					countPrice();
				},
				addNum : function(index){
					var car = this.shoppingCarList[index];
					car.goodsAmout+=1;
					countPrice();
				},
				goodsChecked : function() {
					countPrice();
				}
			}
		});
		
		listShoppcar();
        $('#settlement').click(function () {
            settlementMoney();
        });
	});

	function settlementMoney() {
       var goodsBuyArray = [];
		$('.th :checkbox').each(function () {
			if($(this).is(':checked')){
			    var index = $(this).attr('id');
			    var goods = goodsShow.shoppingCarList[index].goods;
			    var car = goodsShow.shoppingCarList[index];
                goodsBuyArray.push({goodsId : goods.id, goodsAmount : car.goodsAmout,carId : car.id});
			}
        });
		var array = JSON.stringify(goodsBuyArray);
		alert(array);
        $.ajax({
            url:"/order/createOrder.do",
            type:"POST",
            async:true,
            data: array,
			contentType:"application/json",
            dataType:'json',
            timeout:5000,
            dataType:"json",
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
            },
        });


    }

	function listShoppcar() {
		$.ajax({
			url:"/goods/listShoppingCar.do",
			type:"POST",
			async:true,
			data:{
			},
        	timeout:5000,    
            dataType:"json",   
            beforeSend:function(xhr){
                console.log(xhr)
                console.log("发送前")
            },
            success:function(data,textStatus,jqXHR){	
            	
               if(!data.flag) {
              	alert(data.message);
           		return;
           	   }
               
               var map = data.data;
               totalPageNum = map["totalPageNum"];
               pageNum = map["pageNum"] ;
               var goodsList = map["goodsList"];
               var carList = map["carList"];
               
               goodsShow.shoppingCarList = carList;
              
               for(var i=0;i<carList.length;i++) {
            	   var car= goodsShow.shoppingCarList[i];
            	 	car.goods = goodsList[i];
            	 	goodsShow.shoppingCarList.splice(i,1,car);
               }
 				
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
	
	function selectAll() {
	   $('.th :checkbox').each(function () {
		   if ($('#selectAll').is(':checked')) {
			   $(this).prop('checked', true);
		   } else {
			   $(this).prop('checked', false);
		   }
	   });
	   countPrice();
	}
	
	
	function countPrice() {
		var goodsArr = [];
		$('.th :checkbox').each(function () {//遍历所有的复选框
			if($(this).is(':checked')) {
				var attrValue = $(this).attr('id');//checkBox绑定的id值
				goodsArr.push(attrValue);
			}
		});
		
		var total = 0;
		for(var i = 0;i<goodsArr.length;i++) {
			var index = goodsArr[i];
			var price = goodsShow.shoppingCarList[index].goods.price;
			var amount = goodsShow.shoppingCarList[index].goodsAmout;
			total+=price*amount;
		}
		$('#totalPrice').html(total);

	}
	
	</script>
		
	</body>
</html>
