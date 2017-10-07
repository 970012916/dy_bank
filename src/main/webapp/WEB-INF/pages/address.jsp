<%@page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="utf-8" />
		<title>最家</title>
		<link rel="stylesheet" type="text/css" href="/css/order/public.css"/>
		<link rel="stylesheet" type="text/css" href="/css/order/mygxin.css" />
		<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css" />
	</head>
	<body>
		<!------------------------------head------------------------------>
		<div class="head ding">
			<div class="wrapper clearfix">
				<div class="clearfix" id="top">
					<h1 class="fl"><a href="index.html"><img src="/images/order/logo.png"/></a></h1>
					<div class="fr clearfix" id="top1">
						<p class="fl">
							<a href="login.html" id="login">登录</a>
							<a href="reg.html" id="reg">注册</a>
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
		<!------------------------------idea------------------------------>
		<div class="address mt">
			<div class="wrapper clearfix">
				<a href="index.html" class="fl">首页</a>
				<span>/</span>
				<a href="mygxin.html">个人中心</a>
				<span>/</span>
				<a href="address.html" class="on">地址管理</a>
			</div>
		</div>
		
		<!------------------------------Bott------------------------------>
		<div class="Bott">
			<div class="wrapper clearfix">
				
				<jsp:include page="commons/left.jsp"></jsp:include>
				<div class="you fl" id="addressShow">
					<h2>收货地址</h2>
					<div class="add">
						<div>
							<a href="#2" id="addxad"><img src="/images/order/jia.png"/></a>
							<span>添加新地址</span>
						</div>
					</div>
					<div class="add" v-for="(address, index) in addresses">
						<div>
							<p>{{address.username}}</p>
							<p>{{address.mobileNum}}</p>
							<p>{{address.provinceName}} {{address.cityName}} {{address.areaName}}</p>
							<p>{{address.detailAddress}}</p>
							<p>
							<span v-on:click="modifyAdd(index)">修改</span>
							<span v-on:click="delAdd(index)">删除</span>
						</div>
					</div>
						
				</div>
				
			</div>
		</div>
		<!--编辑弹框-->
		<!--遮罩-->
		<div class="mask"></div>
		<div class="adddz">
			<form action="#" method="get">
				<input type="text" placeholder="姓名" class="on" id="name" />
				<input type="text" placeholder="手机号" id="phone" />
				<div class="city">
					<select name="" id="addProvince" onchange="listCity();">
						<option value="省份/自治区">省份/自治区</option>
					</select>
					<select id="addCity" onchange="listArea()">
						<option value="城市/地区">城市/地区</option>
					</select>
					<select id="addArea">
						<option value="区/县" >区/县</option>
					</select>
				</div>
				<textarea name="" rows="" cols="" placeholder="详细地址" id="detailAddress"></textarea>
				默认地址：
				<input type="radio" id="xx" name="defAdd" value="2" style="width: 20px;height: 20px">否
				<input type="radio" id="xxx" name="defAdd" value="1" style="width: 20px;height: 20px">是
				<div class="bc">
					<input type="button" value="保存" id="save"/>
					<input type="button" value="取消" />
				</div>
			</form>
		</div>
		<!--返回顶部-->
		<jsp:include page="commons/right.jsp"></jsp:include>

		<div id="del-confirm" title="删除地址吗？">
		</div>

		<!--footer-->
		<jsp:include page="commons/footer.jsp"></jsp:include>		

		<script type="text/javascript" src="/js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-js.js"></script>
		<script type="text/javascript" src="/js/vue.js"></script>

		<script src="/js/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/user.js" type="text/javascript" charset="utf-8"></script>

		<script>
			addressId = -1;
            var addressList = {};
            var flag = true;
            var delAddressIndex = -1;
			$(document).ready(function () {
				addressList = new Vue({
					el : '#addressShow',
					data : {
					    addresses : []
					},
					methods : {
						modifyAdd : function (index) {
							$('.mask').show();
						    $('.adddz').show();
						    var address = this.addresses[index];
							initModify(address);
							flag = false;
                        },
                        delAdd : function (index) {
							var address = this.addresses[index];
							delAddressIndex = index;
							addressId = address.id;
                            $('#del-confirm').html(address.username + '-' + address.mobileNum);
                            $('#del-confirm').dialog( "open" );

                        }
                    }
				});
				
				//点击增加地址
				$('#addxad').click(function () {
                    $('.mask').show();
				    $('.adddz').show();
                    listProvince();
                });

				//隐藏幕布
				$('.bc>input').click(function () {
                    $('.mask').hide();
                    $('.adddz').hide();
                });

				//保存地址
				$('#save').click(function () {
				    optionChecked();
                });

                loadAddress();
              	initDelDialog();
            });

			function initDelDialog() {
                $( "#del-confirm" ).dialog({
                    resizable: false,
                    height:140,
                    modal: true,
                    autoOpen: false,
                    buttons: {
                        '删除': function() {
                            deleteAddress();
                            $( this ).dialog( "close" );
                        },
                        '取消': function() {
                            $( this ).dialog( "close" );
                        }
                    }
                });
            }


			function deleteAddress() {
                $.ajax({
                    url:'/address/deleteAddress.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
                        id : addressId
                    },

                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    beforeSend:function(xhr){
                        console.log(xhr)
                        console.log('发送前')
                    },
                    success:function(data,textStatus,jqXHR){
                        if (!data.flag) {
                            return;
                        }

                        addressList.addresses.splice(delAddressIndex,1);
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

			function initModify(address) {
				listProvince();//展现省
                $('#addProvince').val(address.provinceCode);
                listCity();
                $('#addCity').val(address.cityCode);
                listArea();
                $('#addArea').val(address.areaCode);
                $('#name').val(address.username);
                $('#phone').val(address.mobileNum);
                $('#detailAddress').val(address.detailAddress);
                if(1 === address.isDefault) {
                    $('input:radio[name="defAdd"][value="1"]').prop('checked',true);
				}else {
                    $('input:radio[name="defAdd"][value="2"]').prop('checked',true);
				}

                addressId = address.id;

            }

            function optionChecked() {
                if(flag == true) {
                    saveAddress();
                }
                if(flag == false) {
                    modifyAddress();
                }
            }

            function modifyAddress() {
				alert('modifyAddress');
                // alert( $('#addProvince').find('option:checked').text());
                // alert(index);
                $.ajax({
                    url:'/address/modifyAddress.do',
                    type:'POST', //GET
                    async:true,    //或false,是否异步
                    data:{
                        provinceCode : $('#addProvince').val(),
                        provinceName :  $('#addProvince').find('option:checked').text(),
                        cityCode :$('#addCity').val(),
                        cityName :  $('#addCity').find('option:checked').text(),
                        areaCode : $('#addArea').val(),
                        areaName :  $('#addArea').find('option:checked').text(),
                        username : $('#name').val(),
                        mobileNum :$('#phone').val(),
                        detailAddress　:$('#detailAddress').val(),
                        isDefault : $('input:radio[name="defAdd"]:checked').val(),
                        id : addressId
                    },

                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    beforeSend:function(xhr){
                        console.log(xhr)
                        console.log('发送前')
                    },
                    success:function(data,textStatus,jqXHR){
                        if (!data.flag) {
                            return;
                        }
                        var address = data.data;

                        addressList.addresses.splice(delAddressIndex,1,address);
                        //loadAddress();

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
			
			function listProvince() {
                initAddress();
                $.ajax({
                    url:'/address/listProvince.do',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
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

                        var results = data.data;
                        var msg = '';
						for( var i=0;i<results.length;i++) {
						    var province = results[i];
						    msg+='<option value= "'+province.code+'">'+ province.name+'</option>';
						}

                        $('#addProvince').append(msg);
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
            function listCity() {
                $.ajax({
                    url:'/address/listCity.do',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
                    data:{
                        provinceCode : $('#addProvince').val()
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

                        var results = data.data;
                        var msg = '<option value= "城市/地区">城市/地区</option>';
                        for( var i=0;i<results.length;i++) {
                            var city = results[i];
                            msg+='<option value= "'+city.code+'">'+ city.name+'</option>';
                        }

                        $('#addCity').html(msg);
                        $('#addArea').html('<option value="xxx">区/县</option>')

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

            function listArea() {
                $.ajax({
                    url:'/address/listArea.do',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
                    data:{
                        cityCode:$('#addCity').val()
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

                        var results = data.data;
                        var msg = '<option value= "区/县">区/县</option>';
                        for( var i=0;i<results.length;i++) {
                            var area = results[i];
                            msg+='<option value= "'+area.code+'">'+ area.name+'</option>';
                        }

                        $('#addArea').html(msg);
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
            
            function initAddress() {
				$('#addProvince').val('省份/自治区');
                $('#addCity').val('城市/地区');
                $('#addArea').val('区/县');
                $('#name').val('');
                $('#phone').val('');
                $('#detailAddress').val('');
            }

            function saveAddress() {
			   // alert( $('#addProvince').find('option:checked').text());
				alert($('#name').val());
                $.ajax({
                    url:'/address/addAddress.do',
                    type:'POST', //GET
                    async:false,    //或false,是否异步
                    data:{
                        provinceCode : $('#addProvince').val(),
                        provinceName :  $('#addProvince').find('option:checked').text(),
                        cityCode :$('#addCity').val(),
                        cityName :  $('#addCity').find('option:checked').text(),
                        areaCode : $('#addArea').val(),
                        areaName :  $('#addArea').find('option:checked').text(),
                        username : $('#name').val(),
                        mobileNum :$('#phone').val(),
                        detailAddress　:$('#detailAddress').val(),
                        isDefault : $('input:radio[name="defAdd"]:checked').val(),

                    },

                    timeout:5000,    //超时时间
                    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
                    beforeSend:function(xhr){
                        console.log(xhr)
                        console.log('发送前')
                    },
                    success:function(data,textStatus,jqXHR){
                        if (!data.flag) {
                            return;
                        }
                        var address = data.data;
                        addressList.addresses.splice(0,0,address);

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

		</script>
	</body>
</html>
