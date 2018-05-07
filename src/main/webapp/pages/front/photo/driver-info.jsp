<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx}/static/pc/Issuedgoods/css/photos.css"/>
	</head>
	<body>
		<!---头部的内容--->
		<div class="header">
			<div class="header-list">
				<p>Hi,13480256996,欢迎来到网上商城 !</p>
				<a href="#" class="header-a">退出</a>
				<ul>
					<li><a href="#">首页</a></li>
					<li><a href="#">我的订单</a></li>
					<li><a href="#">个人中心</a></li>
					<li><a href="#">我要开单</a></li>
					<li><a href="#">消息</a></li>
					<li><a href="#">客服服务</a></li>
				</ul>
			</div>
		</div>
		<!---中间的内容--->
		<div class="banner">
			<div class="banner-left">
				<p>服务热线 : 400-020-2345</p>
			</div>
			<div class="banner-right"></div>
		</div>
		<!---中间内容的部分--->
		<!---中间内容的部分--->
		<div class="content">
			<div class="content-top">
				<p>承运方基本信息</p>
			</div>
			<div class="content-left">
				<ul>
					<li>星级：*****</li>
					<li>司机姓名：刘伟</li>
					<li>车牌号：粤A25869</li>
					<li><a href="#">查看行驶证</a></li>
				</ul>
			</div>
			<div class="content-content">
				<ul>
					<li></li>
					<li>车辆属性：个体司机</li>
					<li>车长：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;157米</li>
					<li><a href="#">查看驾驶证</a></li>
				</ul>
			</div>
			<div class="content-right">
				<div class="content-img">
					<img src="${photo}_200x150.png" />
				</div>
				<a href="#">查看人车合影</a>
			</div>
			<div class="content-imgs">
				<img src="${photo}" />
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
