<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>操作日志</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/checkorder.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
		<div class="banner-right-list4">
          		<h1>操作日志</h1>
	<div class="banner-list4-imfo">
		<ul>
			<li>
				<p>运单号：<span>${ship.ship_sn}</span></p>
				
			</li>
			<li>
				<p>开单日期：<span><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></span></p>
				
			</li>
			<li>
				<p>出发地：<span>${ship.fromAdd}</span></p>
				
			</li>
			<li>
				<p>到达地：<span>${ship.toAdd}</span></p>
				
			</li>
			<li>
				<p>托运方：<span>${ship.senderName}</span></p>
				
			</li>
			<li>
				<p>收货方：<span>${ship.receiverName}</span></p>
				
			</li>
			<li>
				<p>货物名称：<span>${ship.ship_sn}</span></p>
				
			</li>
			<li>
				<p>体积：<span>${ship.ship_volume}立方</span></p>
				
			</li>
			<li>
				<p>重量：<span>${ship.ship_weight}公斤</span></p>
				
			</li>
			<li>
				<p>件数：<span>${ship.ship_amount}</span></p>
				
			</li>
		</ul>					
	</div>
	<div class="banner-list4-log">
		<p>日志信息：</p>
			<table cellspacing="0" border="1">
				<tr>
					<th>操作人</th>
					<th>操作时间</th>
					<th>操作类型</th>
				</tr>
				<c:forEach items="${tracks}" var="track">
				<tr>
					<td>${track.username}</td>
					<td><fmt:formatDate value="${track.create_time}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
					<td>${track.track_short_desc}</td>
				</tr>
				</c:forEach>
			</table>
	</div>
<button class="banner-list4-button" onclick="parent.layer.closeAll();">关闭</button>
</div>
	</body>
</html>