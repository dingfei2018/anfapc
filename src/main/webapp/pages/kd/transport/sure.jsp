<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/sureview.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
</head>
<body>
	<div class="banner-sure">
		<h3>确认到达</h3>
		<p>确认<span>粤A35562</span>已到达？ 	该车配载的运单将进入库存中</p>
		<div class="sure-button">
			<button class="button1">确定</button>
			<button class="button2">取消</button> 
		</div>
	</div>
</body>
</html>