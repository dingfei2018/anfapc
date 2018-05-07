<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<base href="${ctx }/">
<title>网上物流首页</title>
<link rel="stylesheet" href="${ctx }/static/pc/css/test.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/head.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/index.css?v=${version}" />
<link rel="stylesheet" href="${ctx }/static/pc/css/lrtk.css?v=${version}" />
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.css?v=${version}">
<link rel="stylesheet" href="${ctx }/static/pc/study/css/city-picker.css?v=${version}">
<link rel="stylesheet" href="${ctx }/static/pc/study/css/main.css?v=${version}">
<link rel="stylesheet" href="${ctx }/static/pc/stung/css/slide.css?v=${version}">

<script src="${ctx }/static/pc/study/js/jquery.js"></script>
<script src="${ctx }/static/pc/js/menu.js?v=${version}"></script>
<script src="${ctx }/static/pc/study/js/bootstrap.js"></script>
<script src="${ctx }/static/pc/study/js/city-picker.data.js"></script>
<script src="${ctx }/static/pc/study/js/city-picker.js"></script>
<script src="${ctx }/static/pc/study/js/main.js"></script>
<script src="${ctx }/static/pc/stung/js/slide.js"></script>
<script src="${ctx }/static/pc/js/search.js?v=${version}"></script>
<script src="${ctx }/static/pc/js/utils.js"></script>
<script src="${ctx }/static/pc/js/fontscroll.js"></script>
</head>
<body >
	<!--头部的内容-->
	<div class="header-list">
		<div class="head">
			<c:if test="${!empty session_user.mobile }">
				<div class="head-left">
					<p>&nbsp;${session_user.mobile}&nbsp;&nbsp;欢迎来到网上物流 !</p>
				</div>
				<div class="head-centers">
					<a href="${ctx}/front/user/logout">退出</a>
				</div>
			</c:if>
			<c:if test="${empty session_user.mobile}">
				<div class="head-center">
					<p>
						欢迎来到网上物流！&nbsp;&nbsp;&nbsp;<a href="${ctx}/front/user">请登录</a><a href="${ctx}/front/register/wl" class="head-center-a">快速注册</a>
					</p>
				</div>
			</c:if>

			<div class="head-right">
				<ul>
					<li><img src="${ctx}/static/pc/img/zs_app_icon1.jpg" /><a
						class="a1" href="#"> 找车源APP</a></li>
					<li><img src="${ctx}/static/pc/img/zs_app_icon2.jpg" /><a
						class="a2" href="#"> 找货源APP</a></li>
				</ul>
			</div>
			<script>
				$(function() {
					$(".head-right a").mouseover(
					function() {
						$(".a1").text("即将发布").css("color","red").css("margin-left", "5px");
					}).mouseout(
					function() {
						$(".a1").text("找车源APP").css("color","black").css("margin-left","5px");
					});
	
					$(".head-right a").mouseover(
						function() {
							$(".a2").text("即将发布").css("color","red").css("margin-left", "5px");
						}).mouseout(
						function() {
							$(".a2").text("找货源APP").css("color","black").css("margin-left","5px");
						});
				})
			</script>
		</div>
	</div>

	<!--轮播的内容-->
	<div class="banner">
		<div class="banner-left">
			<a href="${ctx }"><img src="${ctx }/static/pc/img/LOGO_03.png" /></a>
		</div>
		<a class="gun" href="${ctx}/listCity"></a>
		<ul id="test" class="banner-right">
			<li><a href="${ctx}" id='<c:if test='${curr eq 22}'>activety</c:if>'>首页</a></li>
			<li><a href="${ctx}/front/order">我的订单</a></li>
			<li class="banner-right-banner" id="content"><a id="flip"
				href="${ctx}/front/userconter">个人中心</a>
				<div class="banner-feel" id="panel box-1">
					<ul>
						<li><a href="${ctx}/front/userconter/getCompanyInfo">基本信息</a></li>
						<li><a href="${ctx}/cpy/admin/cert">实名认证</a></li>
						<li><a href="${ctx}/front/order/hasGoods">已发的货</a></li>
						<c:if test="${session_user.usertype==4 && session_user.isCert==2}">
							<li><a href="${ctx}/cpy/admin">网站管理</a></li>
						</c:if>
						<li class="banner-feel-ra"><a href="${ctx}/front/userconter/code">修改密码</a></li>
					</ul>
				</div> <!-- <li>我要开单</li> -->
			<li ><a href="${ctx}/kd">网上物流TMS</a></li>
			<li class="lop" id="contents"><a id="flips"
				href="${ctx}/front/message/showUnRead">消息</a>
				<div class="banner-feels" id="panels">
					<div class="banner-feels-top">
						<p onclick="gtn()">未读消息</p>
						<a href="${ctx}/front/message/showUnRead">查看所有 ></a>
						<script type="text/javascript">
							function gtn() {
								window.location.href = "${ctx}/front/message/showUnRead"
							}
						</script>
						<div class="banner-left-opspp">${unreadcount}</div>
					</div>
					<c:forEach begin="0" end="2" items="${anotherpage.list}" var="item">
						<div class="banner-feels-tops">
							<c:if test="${item.type==1}">
								<p class="activet">
							</c:if>
							<c:if test="${item.type==2}">
								<p class="activec">
							</c:if>
							<c:if test="${item.type==3}">
								<p class="actives">
							</c:if>
							<%-- <img src="${ctx }/static/pc/img/icon1.jpg"/> --%>
							${item.label} <span><fmt:formatDate
									value="${item.created}" type="both" /> </span>
							</p>
							<b><a href="${ctx}/front/message/showUnRead">${item.content}</a></b>
						</div>
					</c:forEach>
				</div></li>
			<li><a href="${ctx}/front/customer">客服服务</a></li>
		</ul>

		<script>
			$(function() {
				var cotrs = $(".banner-right a");
				cotrs.click(function() {
					$(this).addClass("thisclass").siblings().removeClass("thisclass");
				});
			});
		</script>

		<div class="banner-right-gjk"></div>
		<c:if test="${not empty session_user &&session_user.isCert!=2}">
			<p class="banner-right-gjk-p">
				<img id="kop" src="${ctx }/static/pc/img/point2.png" />
			</p>
			<script type="text/javascript">
				$(function() {
					$(".banner-right-banner").mouseover(function() {
						$("#kop").show();
						$("#kops").hide();
					}).mouseleave(function() {
						$("#kop").show();
						$("#kops").hide();
					})
				});
			</script>
		</c:if>

		<c:if test="${not empty anotherpage.list}">
			<script>
				$(function() {
					$(".banner-right-gjk").show();
				});
			</script>
		</c:if>

		<c:if test="${empty anotherpage.list}">
			<script>
				$(function() {
					/* $(".banner-feels-top").hide();  */
					$(".banner-left-opspp").hide();
				});
			</script>
		</c:if>

		<c:if test="${ not empty session_user.mobile}">
			<script>
				/*$(".lop").mouseover(function (){  
				    $(".banner-feels").show();  
				    $(".lop").css("border-bottom","0");
				});
				
				  $(".banner-feels").mouseleave(function (){
						$(".banner-feels").hide(); 	
				});*/
			</script>
		</c:if>
	</div>

	<div class="main-list">
		<div class="ck-slide" style="width: 100%; height: 385px;">
			<ul class="ck-slide-wrapper">
				<li><a><img src="${ctx }/static/pc/img/011.jpg" alt=""></a>
				</li>
				<li style="display: none"><a><img
						src="${ctx }/static/pc/img/02.jpg" alt=""></a></li>
				<li style="display: none"><a><img
						src="${ctx }/static/pc/img/1_03.jpg" alt=""></a></li>
				<%-- <li style="display:none">
						<a><img src="${ctx }/static/pc/img/1920-4.png" alt=""></a>
					</li> --%>
			</ul>
			<!--<a href="javascript:;" class="ctrl-slide ck-prev">上一张</a> <a href="javascript:;" class="ctrl-slide ck-next">下一张</a>-->
			<div class="ck-slidebox">
				<div class="slideWrap">
					<ul class="dot-wrap">
						<li class="current"><em>1</em></li>
						<li><em>2</em></li>
						<li><em>3</em></li>
						<!-- <li><em>4</em></li> -->
					</ul>
				</div>
			</div>
		</div>
		<script>
			$('.ck-slide').ckSlide({
				autoPlay : true
			});
		</script>
	</div>
	<div class="content">
		<div class="content-left">
			<h3>
				<!-- 找专线和三方 -->
				&nbsp;
				
			</h3>
			<div class="content-left-gun1">
				<div id="gun1" class="content-left-gun11">
					<a class="as">按线路发货</a>
				</div>
				<div id="gun2" class="content-left-gun12">
					<a>物流园发货</a>
				</div>
				<div id="gun3" class="content-left-gun13">
					<a>按公司发货</a>
				</div>
			</div>

			<div class="content-left-lay">
				<b class="content-font">到达地</b>
				<!-- <span class="content-fonts-span">*</span> -->

				<div id="distpicker" style="width: 300px" class="distpicker1">
					<script type="text/javascript">
						$("#distpicker").click(function() {
							//alert("555")
							$(".city-select-tab").find('a').eq(-1).remove();
							$("#city-selects").remove();
						})
					</script>
					<div class="form-group">
						<div style="position: relative;">
							<input id="city-picker1" class="form-control"
								placeholder="请选择省/市" readonly type="text"
								data-toggle="city-picker" data-type="city">
						</div>
					</div>
				</div>
				<br />
				<br /> <span class="content-fonts">出发地</span>
				<!-- <span class="content-fonts-span">*</span> -->
				<input type="text" class="content-font-input" />
				<%-- <img class="content-left-iml" src="${ctx }/static/pc/img/fang6.png"/> --%>
				<div id="distpicker-s" style="width: 300px">
					<div class="form-group">
						<div style="position: relative;">
							<input id="city-picker2" class="form-control"
								placeholder="请选择省/市/区" readonly type="text"
								data-toggle="city-picker">
						</div>
					</div>
				</div>
				<script>
					$(function() {
						$(".content-left-gun1").addClass('content-left-gun110');
						$(".content-font-input").click(function() {
							$(".content-font-input").hide();
							$("#distpicker-s").show();
							$(".city-picker-span").height(50);
							$(".city-picker-span").css('line-height', '50px');
							$(".city-picker-dropdown").css("width", "300");
						});
					});
				</script>
				<button class="content-left-ang" onclick="javascript:search();">立即搜索</button>
			</div>

			<form action="${ctx}/park/searchIndexPark" method="post"
				id="parkfrom">
				<input type="hidden" id="addcity" name="addcity"> <input
					type="hidden" id="addCode" name="addCode">
				<div class="content-left-lay2">
					<label>园区名<input type="text" id="parkName" name="parkName"
						placeholder="请输入物流园名称" /></label><br> <input type="text"
						class="content-font-inputs" /> <span class="content-fonts">所在地</span>

					<div id="distpicker" style="width: 300px" class="distpicker-s">
						<div class="form-group">
							<div style="position: relative;">
								<input id="city-picker2" class="form-control"
									placeholder="请选择省/市/区" readonly type="text"
									data-toggle="city-picker">
							</div>
						</div>
					</div>
					<script>
						$(function() {
							$(".distpicker-s").show();
							$(".city-picker-span").height(50);
							$(".city-picker-span").css('line-height', '50px');
							$(".city-picker-dropdown").css("width", "300");
							$(".content-font-inputs").click(function() {
								$(".content-font-inputs").hide();
							})
						 });
					</script>
					<button class="content-left-ang" onclick="searchParks();">立即搜索</button>
					<div class="content-left-ang3s">
						<ul>
						</ul>
					</div>
					<script type="text/javascript">
						$("#parkName").fillPark("${ctx}");
					</script>
				</div>
			</form>


			<div id="content-left-lay3">
				<label>公司名</label><input id="corpId" class="input" type="text"
					placeholder="请输入物流公司名"><br> <input
					class="content-left-ang2" type="button" value="立即搜索">
				<div class="content-left-ang3">
					<ul>
					</ul>
				</div>
				<script type="text/javascript">
					$("#corpId").fillCorps("${ctx}");
				</script>
			</div>

			<div class="content-left-right-tui" id="jjk">
				<div class="content-left-right">
					<img src="${ctx }/static/pc/img/te(2).gif"
						onclick="window.open('${ctx}/front/line/specialLine','_blank')" /><br />
					<a href="${ctx}/front/line/specialLine">特价专线</a>
					<%-- <p>${countOrders}</p> --%>
				</div>
				<div class="content-left-right2">
					<img src="${ctx }/static/pc/img/Hs_icon2.png" onclick="window.location.href='${ctx}/park/alltrade'" /><br />
					 <a href="${ctx}/park/alltrade">历史交易量</a>
				</div>
				<div class="content-left-right3">
					<img src="${ctx }/static/pc/img/Hs_icon4.png" onclick="window.location.href='${ctx}/express'" /><br />
					 <a href="${ctx}/express">运单查询</a>
				</div>
			</div>

			<div class="content-left-right-tui" id="jjks" style="display: none;">
				<div class="content-left-right-tui1">
					<img src="${ctx }/static/pc/img/Hs_icon3.png" onclick="window.location.href='${ctx}/park/around'" /><br /> 
					<a href="${ctx}/park/around">物流园分布</a>
				</div>
				<div class="content-left-right2-tui2">
					<img src="${ctx }/static/pc/img/Hs_icon4.png" onclick="window.location.href='${ctx}/express'" /><br /> 
					<a href="${ctx}/express">运单查询</a>
				</div>
			</div>

			<div class="content-left-right-tui" id="jjkc" style="display: none;">
				<div class="content-left-right-tui1">
					<img src="${ctx }/static/pc/img/Hs_icon2.png" onclick="window.location.href='${ctx}/park/alltrade'" /><br /> 
					<a href="${ctx}/park/alltrade">历史交易量</a>
				</div>
				<div class="content-left-right2-tui2">
					<img src="${ctx }/static/pc/img/Hs_icon4.png" onclick="window.location.href='${ctx}/express'" /><br /> 
					<a href="${ctx}/express">运单查询</a>
				</div>
			</div>
		</div>
		<form action="${ctx}/front/line/searchIndexLine" id="myfrom"
			method="post">
			<input type="hidden" name="toCityCode" id="toCityCode"> <input
				type="hidden" name="toRegionCode" id="toRegionCode"> <input
				type="hidden" name="fromCityCode" id="fromCityCode"> <input
				type="hidden" name="fromRegionCode" id="fromRegionCode">
		</form>

		<div class="content-right">
			<h3><%-- 黄金专线 <img class="content-right-imgo" src="${ctx }/static/pc/img/car.png"/> --%></h3>
			<div class="content-right-left">
				<div class="content-right-lefts">
					<ul>
						<li class="content-right-tag">
							<%-- <img src="${ctx }/static/pc/img/jiaoyi.png"/>  --%>信息交易大厅
						</li>
						<li class="content-right-left-size">动态播报</li>
						<!-- <li>今日发布</li> -->
						<%-- <li class="content-right-left-size">${fn:length(goldLines)}票</li> --%>
						<li>正在交易</li>
					</ul>
				</div>
			</div>
			<div class="content-div">
				<div class="Top_Record">
					<div class="topRec_List">
						<dl>
							<dd>出发地</dd>
							<dd>
								<img src="${ctx }/static/pc/img/icon_09.png"
									style="display: none;" />
							</dd>
							<dd>到达地</dd>
							<!-- <dd>诚信指数</dd>
					        	<dd>好评</dd> -->
							<dd>重货价/公斤</dd>
							<dd>轻货价/立方</dd>
							<dd>发车频次</dd>
						</dl>
						<div class="maquee">
							<ul>
								<c:forEach items="${goldLines}" var="glines">
									<li style="cursor: pointer;"
										onclick='javascript:window.location.href="${ctx}/company/special?id=${glines.company_id}"'>
										<div>
											<a style="color: #323232; text-decoration: none;" title="${glines.from_addr}">${glines.from_addr}</a>
										</div>
										<div>
											<img src="${ctx }/static/pc/img/icon_10.png" />
										</div>
										<div>
											<a style="display: inline-block; color: #323232; text-decoration: none; width: 80px; word-break: keep-all; overflow: hidden; text-overflow: ellipsis;" title="${glines.to_addr}">${glines.to_addr}</a>
										</div> <%-- <div style="color : red;">${glines.credit}</div>
					                    <div style="color : red;">${glines.feedback}%</div> --%>
										<c:if test="${glines.price_heavy!='0.00'}">
											<div>${glines.price_heavy}元</div>
										</c:if> <c:if test="${glines.price_heavy=='0.00'}">
											<div>面议</div>
										</c:if> <c:if test="${glines.price_small!='0.00'}">
											<div>${glines.price_small}元</div>
										</c:if> <c:if test="${glines.price_small=='0.00'}">
											<div>面议</div>
										</c:if>
										<div>1天${glines.frequency}次</div>
									</li>
								</c:forEach>

							</ul>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					function autoScroll2(obj) {
						$(obj).find("ul").animate({
							marginTop : "-33px"
						}, 500, function() {
							$(this).css({
								marginTop : "0px"
							}).find("li:first").appendTo(this);
						})
					}
					$(function() {
						setInterval('autoScroll2(".maquee")', 3000);
					});
				</script>
			</div>

		</div>
	</div>

	<div class="imgun">
		<img src="${ctx }/static/pc/img/05_02.png" />
	</div>

	<div class="menu">
		<h3>
			<%-- 热门专线推荐 <img src="${ctx }/static/pc/img/tuijian.png" id="menu-ou"/> --%>
			&nbsp;
		</h3>


		<div class="Top_Record" style="margin-top: -10px;">
			<div class="topRec">
				<dl>
					<dd>出发地</dd>
					<dd>
						<img src="${ctx }/static/pc/img/icon_09.png"
							style="display: none;" />
					</dd>
					<dd>到达地</dd>
					<dd>公司名称</dd>
					<!-- <dd>诚信指数</dd>
				        	<dd>好评率</dd> -->
					<dd>重货价/公斤</dd>
					<dd>轻货价/立方</dd>
					<dd>发车频次</dd>
					<dd>操作</dd>
				</dl>

				<div class="maques">
					<ul>
						<c:forEach items="${hotLines}" var="item">
							<li>
								<div>
									<a class="menu-a"
										style="display: inline-block; color: #323232; width: 94px; word-break: keep-all; overflow: hidden; text-overflow: ellipsis;"
										title="${item.to_addr}" title="${item.from_addr}">${item.from_addr}</a>
								</div>
								<div>
									<img src="${ctx }/static/pc/img/icon_10.png" />
								</div>
								<div>
									<a class="menu-a"
										style="display: inline-block; color: #323232; width: 94px; word-break: keep-all; overflow: hidden; text-overflow: ellipsis;"
										title="${item.to_addr}">${item.to_addr}</a>
								</div>
								 <c:if test="${ empty item.corpname}">
								<div>
									<a href="#" class="menu-a"
										target="_blank">---</a>
								</div>  
								</c:if>
								<c:if test="${not empty item.corpname}">
								<div>
									<a href="${ctx}/company?id=${item.company_id}" class="menu-a"
										target="_blank">${item.corpname}</a>
								</div>  
								</c:if>
								<c:if test="${item.price_heavy!='0.00'}">
									<div>${item.price_heavy}元/公斤</div>
								</c:if> <c:if test="${item.price_heavy=='0.00'}">
									<div>面议</div>
								</c:if> <c:if test="${item.price_small!='0.00'}">
									<div>${item.price_small}元/立方</div>
								</c:if> <c:if test="${item.price_small=='0.00'}">
									<div>面议</div>
								</c:if>
								<div>1天${item.frequency}次</div>
								<div>
									<%-- <a class="maques-a" href="${ctx }/front/goods?orderid=${item.id}&fromcitycode=${item.from_city_code}&fromregioncode=${item.from_region_code}&tocitycode=${item.to_city_code}&toregioncode=${item.to_region_code}">发货 --%>
									<a class="maques-a" target="_blank"
										href="${ctx}/company/special?id=${item.company_id}">查看 </a>
								</div>
							</li>
						</c:forEach>
					</ul>

				</div>
				<script type="text/javascript">
					function autoScroll(obj) {
						$(obj).find("ul").animate({
							marginTop : "-39px"
						}, 500, function() {
							$(this).css({
								marginTop : "0px"
							}).find("li:first").appendTo(this);
						})
					}
					$(function() {
						setInterval('autoScroll(".maques")', 3000);
					});
				</script>
			</div>
		</div>
	</div>


	<div class="footer-lop">
		<img src="${ctx }/static/pc/img/05_03.jpg"> <img src="${ctx }/static/pc/img/banner_4.png" class="footer-lop-img">
	</div>

    <!-- 隐藏的信息图片，不可以删除。 -->
	<%--<div class="footer-lop2">
		  <ul>
		   <li class="footer-lop2-li"><img src="${ctx }/static/pc/img/wechat.jpg"></li>
		   <li class="footer-lop2-lis"><img src="${ctx }/static/pc/img/wobo.jpg"></li>
		   <li class="footer-lop2-list"><img src="${ctx }/static/pc/img/qq.jpg" class="footer-lop-img3"></li>
		 </ul> 
		</div> --%>

	<%@ include file="common/loginfoot.jsp"%>
	<script type="text/javascript">
		$(function() {
			var cityValue = getCookie("anfaCity");
			cityValue = decodeURIComponent(cityValue);
			if (cityValue == "") {
				/* 				 //通过调用本地的ip地址进行定位查询
				 var url = 'http://chaxun.1616.net/s.php?type=ip&output=json&callback=?&_=' + Math.random();
				 $.getJSON(url, function (data) {
				 // console.log(data.Ip);
				 $.ajax({
				 url:"${ctx}/map/locationIP",
				 dataType : 'jsonp',  
				 timeout : 2000,
				 jsonp:"callback",
				 jsonpCallback:"success_jsonpCallback",
				 data : {
				 ip : data.Ip
				 },
				 success: function(tests){
				 $(".content-font-input").val(tests.content.city);
				 $(".content-font-inputs").val(tests.content.city);
				 $("#fromCityCode").val(tests.content.region_code);
				 $("#addcity").val(tests.content.region_code);
				 $(".gun").text(tests.content.city);
				 },
				 error : function(tests){
				 $(".content-font-input").val("广州市");
				 $("#fromCityCode").val("440100");
				 $("#addcity").val("440100");
				 $(".gun").text("广州市");
				 }
				 });
				 }); */
				$(".content-font-input").val("广州市");
				$(".content-font-inputs").val("广州市");
				$("#fromCityCode").val("440100");
				$("#addcity").val("440100");
				$(".gun").text("广州市");
			} else {
				var v = cityValue.split(",");
				$(".content-font-input").val(v[0]);
				$(".content-font-inputs").val(v[0]);
				$("#fromCityCode").val(v[1]);
				$("#addcity").val(v[1]);
				$(".gun").text(v[0]);
			}
			
			//回车事件
			var enterClick='route';
			$("#gun1").click(function(){
				 enterClick="route";
			 });
			 $("#gun2").click(function(){
				 enterClick="park";
			 });
			 $("#gun3").click(function(){
				 enterClick="company";
			 });
			 
			 $(document).keypress(function(e) {  
				    // 回车键事件  
				       if(e.which == 13) { 
				    	   if(enterClick=="route"){
				    		   search();
				    	   }
				    	   if(enterClick=="park"){
				    		   searchParks();
				    	   }
				    	   if( enterClick=="company"){
				    		   window.location.href="${ctx}"+"/company/queryList?corpName="+$("#corpId").val();
				    	   }
				       }  
				   }); 
		})
	</script>
</body>
</html>
