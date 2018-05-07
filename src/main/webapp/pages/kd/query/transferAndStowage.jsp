<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>中转或配载</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/checkorder.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
		<div class="banner-right-list4">
          		<h1>运单中转或配载信息</h1>
	<div class="banner-list4-imfo">
		<ul>
			<li>
				<p>运单号：<span>${ship.ship_sn}</span></p>
				
			</li>
			<li>
				<p>开单网点：<span>1</span></p>
				
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
				<p>开单日期：<span><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></span></p>
				
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
		<p>配载列表：</p>
			<table cellspacing="0" border="1">
				<tr>
					<th>配载单号</th>
					<th>配载网点</th>
					<th>车牌号</th>
					<th>司机</th>
					<th>司机电话</th>
					<th>配载日期</th>
					<th>配载类型</th>
				</tr>
				<c:forEach items="${shipList}" var="ship">
				<tr>
					<td>${ship.load_sn}</td>
					<td>${ship.netName}</td>
					<td>${ship.truck_id_number}</td>
					<td>${ship.truck_driver_name}</td>
					<td>${ship.truck_driver_mobile}</td>
					<td>${ship.create_time }</td>
					
						<c:if test="${ship.load_transport_type==1}">
						<td>提货</td>
						</c:if>
						<c:if test="${ship.load_transport_type==2}">
						<td>短驳</td>
						</c:if>
						<c:if test="${ship.load_transport_type==3}">
						<td>干线</td>
						</c:if>
						<c:if test="${ship.load_transport_type==4}">
						<td>送货</td>
						</c:if>
					
				</tr>
				</c:forEach>
			</table>
	</div>
	
	<div class="banner-list4-log">
		<p>中转信息：</p>
			<table cellspacing="0" border="1">
				<tr>
					<th>配载单号</th>
					<th>中转网点</th>
					<th>中转方</th>
					<th>中转联系人</th>
					<th>联系电话</th>
					<th>中转日期</th>
				</tr>
				<c:forEach items="${shipList}" var="ship">
				<tr>
					<td>${ship.load_sn}</td>
					<td>${ship.netName}</td>
					<td>${ship.customer_corp_name}</td>
					<td>${ship.customer_name}</td>
					<td>${ship.customer_mobile}</td>
					<td>${ship.ship_transfer_time }</td>
				</tr>
				</c:forEach>
			</table>
	</div>
	
<button class="banner-list4-button" onclick="parent.layer.closeAll();">关闭</button>
</div>
	</body>
</html>