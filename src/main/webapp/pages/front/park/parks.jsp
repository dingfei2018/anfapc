<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>物流园分布</title>
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/shipped/parks.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
		<script src="${ctx}/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/js/unslider.min.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx }/static/pc/js/search.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>

	<body>
		<%@ include file="../common/loginhead.jsp" %>
		<div class="div_title"><a href="${ctx}/index">首页</a> > 查看物流园分布</div>
		<div class="content">
		
		  <div class="content-img">
		     <iframe  width='1200px' height='600px' frameborder='0' scrolling='no' marginheight='0' marginwidth='0' src='https://yuntu.amap.com/share/aI7jy2'></iframe>
		  </div>
        </div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
