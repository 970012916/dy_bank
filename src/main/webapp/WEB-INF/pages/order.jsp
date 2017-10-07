<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="utf-8" />
		<title>order</title>
		<link rel="stylesheet" type="text/css" href="/css/order/public.css"/>
		<link rel="stylesheet" type="text/css" href="/css/order/proList.css" />
		<link rel="stylesheet" type="text/css" href="/css/order/mygxin.css" />
	</head>
	<body>
		<!----------------------------------------order------------------>
		<!-- header begin -->
		<jsp:include page="head.jsp"></jsp:include>
		<!-- header end -->


		<div class="order cart mt">
			<!-----------------site------------------->
			<div class="site">
				<p class="wrapper clearfix">
					<span class="fl">订单确认</span>
					<img class="top" src="/images/order/temp/cartTop02.png">
				</p>
			</div>
			<!-----------------orderCon------------------->
			<div class="orderCon wrapper clearfix">
				<div class="orderL fl">
					<!--------h3---------------->
					<h3>收件信息<a href="#" class="fr">新增地址</a></h3>
					<!--------addres---------------->
					<div class="addres clearfix" id="addressShow">
						<div  class="addre fl on" v-for="(address,index) in addresses" v-on:click="defaultAddress(index)" v-bind:id="index" v-if="address.isDefault ==1">
							<div class="tit clearfix">
								<p class="fl">
									{{address.username}}

								</p>
								<p class="fr">
									<a href="#">删除</a>
									<span>|</span>
									<a href="#" class="edit">编辑</a>
								</p>
							</div>
							<div class="addCon">
								<p>{{address.provinceName}}&nbsp;{{address.cityName}}&nbsp;{{address.areaName}}&nbsp;{{address.detailAddress}}</p>
								<p>{{address.mobileNum}}</p>
							</div>
						</div>
						<div  class="addre fl" v-for="(address,index) in addresses" v-on:click="defaultAddress(index)" v-bind:id="index" v-if="address.isDefault !=1">
							<div class="tit clearfix ">

								<p class="fl">
									{{address.username}}

								</p>
								<p class="fr">
									<a href="#">删除</a>
									<span>|</span>
									<a href="#" class="edit">编辑</a>
								</p>
							</div>
							<div class="addCon">
								<p>{{address.provinceName}}&nbsp;{{address.cityName}}&nbsp;{{address.areaName}}&nbsp;{{address.detailAddress}}</p>
								<p>{{address.mobileNum}}</p>
							</div>
						</div>

					</div>
					<h3>支付方式</h3>
					<!--------way---------------->
					<div id="payShow" class="way clearfix">
				
						<span>
							<img v-bind:src="'/images/order/temp/'+ payChannel.payImgId"  v-bind:id="'pay'+ index" v-for="(payChannel,index) in payChannelArray" 
							v-on:click="selectPay(index)">
						</span>
						
					</div>
					
					<h3>选择快递</h3>
					<!--------dis---------------->
					<div id="logisticsShow" class="dis clearfix">
						<span v-bind:id="'logistics'+index" v-for="(logistics,index) in logisticsArray" v-on:click="selectLogistics(index)">
							{{logistics.logisticsName}}   
						</span>
						
					</div>
				</div>
				<div class="orderR fr" id="goodsShow">
					<div class="msg" >
						<h3>订单内容<a href="cart.html" class="fr">返回购物车</a></h3>
						<!--------ul---------------->
						<ul class="clearfix" v-for="goods in goodsArray">
							<li class="fl">
								<img src="/images/order/temp/order01.jpg">
							</li>
							<li class="fl">
								<p>{{goods.goodsName}}</p>
								<p>单价：{{goods.goodsPrice}}</p>
								<p>数量：{{goods.goodsAmount}}</p>
							</li>
							<li class="fr">￥{{goods.goodsTotalPrice}}</li>
						</ul>
					</div> 
					<!--------tips---------------->
					<div class="tips">
						<p><span class="fl">商品金额：</span><span class="fr">￥{{totalPrice}}</span></p>
						<p><span class="fl">运费：</span><span class="fr">免运费</span></p>
					</div>
					<!--------tips count---------------->
					<div class="count tips">
						<p><span class="fl">合计：</span><span class="fr">￥{{totalPrice}}</span></p>
					</div>
					<a href="#" class="pay" onclick="pay();">去支付</a>
				</div>
			</div>
		</div>
		<!--编辑弹框-->
		<!--遮罩-->
		<div class="mask"></div>
		<div class="adddz editAddre">
			<form action="#" method="get">
				<input type="text" placeholder="姓名" class="on" />
				<input type="text" placeholder="手机号" />
				<div class="city">
					<select name="">
						<option value="省份/自治区">省份/自治区</option>
					</select>
					<select>
						<option value="城市/地区">城市/地区</option>
					</select>
					<select>
						<option value="区/县">区/县</option>
					</select>
					<select>
						<option value="配送区域">配送区域</option>
					</select>
				</div>
				<textarea name="" rows="" cols="" placeholder="详细地址"></textarea>
				<input type="text" placeholder="邮政编码" />
				<div class="bc">
					<input type="button" value="保存" />
					<input type="button" value="取消" />
				</div>
			</form>
		</div>
		<!--返回顶部-->
		<div class="gotop">
			<a href="cart.html">
			<dl>
				<dt><img src="/images/orders/gt1.png"/></dt>
				<dd>去购<br />物车</dd>
			</dl>
			</a>
			<a href="#" class="dh">
			<dl>
				<dt><img src="/images/orders/gt2.png"/></dt>
				<dd>联系<br />客服</dd>
			</dl>
			</a>
			<a href="mygxin.html">
			<dl>
				<dt><img src="/images/orders/gt3.png"/></dt>
				<dd>个人<br />中心</dd>
			</dl>
			</a>
			<a href="#" class="toptop" style="display: none;">
			<dl>
				<dt><img src="/images/orders/gt4.png"/></dt>
				<dd>返回<br />顶部</dd>
			</dl>
			</a>
			<p>400-800-8200</p>
		</div>
		<!--footer-->
		<jsp:include page="foot.jsp"></jsp:include>

		<script type="text/javascript" src="/js/jquery-mini.js"></script>
		<script src="/js/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/vue.js" type="text/javascript" charset="utf-8"></script>


		<script>
			var payCode = -1;
			var logisticsCode = -1;
			var orderId = "${orderId}";
			var addressId = -1;
			var goodsList = {};
			var addressList = {};
			var logisticsList = {};
			var payChannelList = {};
			$(document).ready(function () {
				goodsList = new Vue ({
					el : "#goodsShow",
					data : {
						goodsArray : [],
						totalPrice : {}
					}

				});
				listGoods();

				addressList = new Vue({
					el : '#addressShow',
					data : {
						addresses : []
					},
					methods : {
						defaultAddress: function (index) {
							$("#" + index).addClass("on").siblings().removeClass("on");
							addressId = addressList.addresses[index].id;
							alert("addressId " + addressId);
						}

					}
				});
                logisticsList = new Vue({
					el : "#logisticsShow",
					data : {
                        logisticsArray : []
					},
					methods: {
                        selectLogistics : function (index) {
                            $("#logistics" + index).addClass("on").siblings().removeClass("on");
                            logisticsCode = logisticsList.logisticsArray[index].id;
                            alert("logisticsCode " + logisticsCode);

                        }
					}

                });
                payChannelList = new Vue({
                    el : "#payShow",
                    data : {
                        payChannelArray : []
                    },
                    methods: {
                        selectPay : function (index) {
                            $("#pay" + index).addClass("on").siblings().removeClass("on");
                            payCode = payChannelList.payChannelArray[index].id;
                            alert("payCode " + payCode);
                        }
                    }

                });
                loadLogistics();
				loadPayChannel();
				loadAddress();
            });

            function pay() {
            	alert("准备");
                $.ajax({
                    url:'/order/submitOrder.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
                        orderId : orderId,
                        payCode : payCode,
                        logisticsCode :logisticsCode,
                        addressId : addressId
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

			function selectPayAndLogistics(id) {
                $('.way img').click(function() {

                    $(this).addClass("on").siblings().removeClass("on");
                    alert("payChannelId " + id);
                    payChannelId = id;
				});
                $('.dis span').click(function() {
                    $(this).addClass("on").siblings().removeClass("on");
                    alert("logisticsId " + id);
                    logisticsId = id;
                });
            }

            function loadPayChannel() {
                $.ajax({
                    url:'/pay/listPayChannel.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
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

                        payChannelList.payChannelArray = data.data;

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

            function loadLogistics() {
                $.ajax({
                    url:'/logistics/listLogistics.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
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

                        logisticsList.logisticsArray = data.data;

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
			
			function loadAddress() {
                $.ajax({
                    url:'/address/listAddress.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
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

                        addressList.addresses = data.data;
                        for(var i=0;i<data.data.length;i++) {
                        	if(addressList.addresses[i].isDefault == 1) {
                        		addressId = addressList.addresses[i].id;
                        	}
                        }

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
			
			function listGoods() {
				$.ajax({
					url:"/order/listOrder.do",
					type:"POST",
					async:true,
					data:{
						orderId : orderId
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
		               
		              // var orderList = data.data.;
		               goodsList.goodsArray = data.data.list;
		               goodsList.totalPrice = data.data.totalGoodsPrice;
		               
		               
		              
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


		</script>
	</body>


</html>
