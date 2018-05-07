<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>司机信息</title>
	<link rel="stylesheet" href="${ctx}/static/pc/css/sharp.css"/>
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css"/>
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css"/>
</head>
<body>
	<%@ include file="../personal/common/head.jsp" %>
	<!---中间的内容--->
	<div class="banner">
		<div class="banner-left">
			<img src="${ctx}/static/pc/img/image_人车合影.png" />
		</div>
		<div class="banner-right">
			<p>星级：*****</p>
			<p>司机姓名：刘伟</p>
			<ul>
				<li>车牌号：粤A2586R</li>
				<li>17.5米</li>
				<li>高栏</li>
				<li><a href="${ctx}/driver/info">查看详情</a></li>
			</ul>
		</div>
		<button>发货</button>
	</div>
	<!--底部的内容--->
	<%@ include file="../common/loginfoot.jsp" %>
</body>
</html>
