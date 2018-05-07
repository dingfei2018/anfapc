<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>签收</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/transfery.css" />
</head>
<body>
	<div class="maip">
		<p>运单中转或配载信息</p>
		<div class="maip-list">
			<table>
				<tr>
					<td colspan="3"><b>运单信息 :</b></td>
				</tr>
				<tr>
					<td>运单号：<span>${ship.ship_sn}</span></td>
					<td>开单网点 : ${ship.netWorkName}</td>
					<td>开单时间 : <span><fmt:formatDate
								value="${ship.create_time}" pattern="yyyy-MM-dd" /></span></td>
				</tr>
				<tr>
					<td>出发地 : <span>${ship.fromAdd}</span></td>
					<td>到达地：<span>${ship.toAdd}</span></td>
					<td>运单状态 : <c:if test="${ship.ship_status==1}">
									已入库
								</c:if> <c:if test="${ship.ship_status==2 }">
									短驳中
								</c:if> <c:if test="${ship.ship_status==3}">
									短驳到达
								</c:if><c:if test="${ship.ship_status==4}">
									已发车
								</c:if><c:if test="${ship.ship_status==5}">
									已到达
								</c:if><c:if test="${ship.ship_status==6}">
									收货中转中
								</c:if><c:if test="${ship.ship_status==7}">
									到货中转中
								</c:if><c:if test="${ship.ship_status==8}">
									送货中
								</c:if><c:if test="${ship.ship_status==9}">
									已签收
								</c:if>
					</td>
				</tr>
				<tr>
					<td>托运方 : <span>${ship.senderName}</span></td>
					<td>收货方 : <span>${ship.receiverName}</span></td>
					<td>客户单号 : <span>${ship.ship_customer_number}</span></td>
				</tr>
				<tr>
					<td>体积 : <span>${ship.ship_volume}立方</span></td>
					<td>重量 : <span>${ship.ship_weight}公斤</span></td>
					<td>件数 : <span>${ship.ship_amount}</span></td>
				</tr>
			</table>
		</div>
		<div class="maip-list2">
			<b>配载列表 :</b>
			<c:if test="${not empty shipList}">
				<table cellspacing="0">
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
							<td>${ship.loadnetName}</td>
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
			</c:if>
			<c:if test="${empty shipList}">
				<table>
					<div
						style="text-align: center; margin-top: 40px; font-size: 18px; height: 30px; line-height: 30px;">
						<p></p>
						暂无数据
					</div>

				</table>
			</c:if>
		</div>
		<div class="maip-list2" style="margin-top: 20px;">
			<b>中转信息 :</b>
			<c:if test="${not empty shipTransfer}">
				<table cellspacing="0">
					<tr>
						<th>中转单号</th>
						<th>中转网点</th>
						<th>中转方</th>
						<th>中转联系人</th>
						<th>联系电话</th>
						<th>中转日期</th>
					</tr>

					<tr>
						<td>${shipTransfer.ship_transfer_sn}</td>
						<td>${shipTransfer.tranNetName}</td>
						<td>${shipTransfer.transferName}</td>
						<td>${shipTransfer.personName}</td>
						<td>${shipTransfer.personMobile}</td>
						<td><span><fmt:formatDate
									value="${shipTransfer.ship_transfer_time}" pattern="yyyy-MM-dd" /></span></td>
					</tr>


				</table>
			</c:if>
			<c:if test="${empty shipTransfer}">
				<table>
					<div
						style="text-align: center; margin-top: 40px; font-size: 18px; height: 30px; line-height: 30px;">
						<p></p>
						暂无数据
					</div>

				</table>
			</c:if>
		</div>
		<button class="buttons" onclick="parent.layer.closeAll();">关闭</button>
	</div>
</body>

</html>