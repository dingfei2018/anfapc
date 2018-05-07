<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>联系我们</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/contact.css"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
	</head>
	<body>
    <!---头部的内容--->
	<%@ include file="../common/loginhead.jsp" %>
		
		<!---联系我们的内容--->
		<div class="content">
			<div class="content-left">
				<div class="content-left-banner">
					<p class="content-left-title">关于我们</p>
					 <img class="content-left-logo" src="${ctx }/static/pc/img/icon_11.png" >
					 <img class="content-left-bg" src="${ctx }/static/pc/img/icon_12.png" >
					<ul>
						<li><a href="${ctx }/front/logistics/aboutus">关于安发</a></li>
						<li><a href="${ctx }/front/logistics/joinus">加入我们</a></li>	
						<li><a href="${ctx }/front/logistics/commonproblems">常见问题</a></li>
						<li class="content-left-main"><a class="content-left-main" href="${ctx }/front/logistics/contactus">联系我们</a></li>				
					</ul>
					 <b class="content-left-bottom"></b>
				</div>
			</div>
			<div class="content-right">
				<div class="content-right-main">
					<p class="content-right-page">联系我们</p>
					<div class="content-right-page1">
						<span>全国客服热线：<%=application.getAttribute("CUSTOMER.SERVICE") %></span>
						<p>网上物流总部：广州</p>
						<p>联系地址：广州市天河区石牌桥丰兴广场B座1804</p>
					</div>
				</div>
			</div>
		</div>
		
		<!--底部的内容--->
<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
