<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>中转物流信息</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/checkorder.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
		<div class="banner-right-list4">
          		<h1></h1>
	<div class="banner-list4-imfo">
		<ul>
			<li>
				<p>中转单号：<span>${shipTransfer.ship_transfer_sn}</span></p>
				
			</li>
			<li>
				<p>中转方：<span>${shipTransfer.transferName}</span></p>
				
			</li>
			<li>
				<p>中转联系人：<span>${shipTransfer.personName}</span></p>
				
			</li>
			<li>
				<p>联系电话：<span>${shipTransfer.personMobile}</span></p>
				
			</li>
			<li>
				<p>中转日期：<span><fmt:formatDate value="${shipTransfer.ship_transfer_time}" pattern="yyyy-MM-dd"/></span></p>
				
			</li>
			<li>
				<p>中转网点：<span>${shipTransfer.tranNetName}</span></p>
				
			</li>
			
		</ul>					
	</div>
	<div class="banner-list4-log">
		<p>物流信息：</p>
			<table cellspacing="0" border="1">
				<tr>
					<th>跟踪时间</th>
					<th>跟踪内容</th>
				</tr>
				<c:forEach items="${tracks}" var="track">
				<tr>
					<td><fmt:formatDate value="${track.create_time}" pattern="yyyy-MM-dd HH:mm:dd"/></td>
					<td>${track.track_desc}</td>
				</tr>
				</c:forEach>
			</table>
	</div>
<button class="banner-list4-button" onclick="parent.layer.closeAll();">关闭</button>
</div>
	</body>
</html>