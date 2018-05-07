<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/tanceng2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
</head>
<body>
	<div id="fade2" class="black_overlay2"></div>
		<div id="MyDiv2" class="white_content2">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->

			<div class="banner-list5-div1">
				<p class="banner-right-p">配载信息</p>
				
				<table>
					<tr>
						<td>配载网点：${load.snetworkName}</td>
						<td>发车时间： <fmt:formatDate value="${load.load_depart_time}" pattern="yyyy-MM-dd"/> </td>
						<td>运输类型： 
						<c:if test="${load.load_transport_type==1}">
							提货
						</c:if>
						<c:if test="${load.load_transport_type==2}">
							短驳
						</c:if>
						<c:if test="${load.load_transport_type==3}">
							干线
						</c:if>
						<c:if test="${load.load_transport_type==4}">
							送货
						</c:if>
						
						</td>
					</tr>
					<tr>
						<td>出发地： ${load.fromCity}</td>
						<td>到达地： ${load.toCity}</td>
						<td>收货网点： ${load.enetworkName}</td>
						
					</tr>
					<tr>
						<td>司机： ${load.truck_driver_name}</td>
						<td>司机电话： ${load.truck_driver_mobile}</td>
						<td>车牌号： ${load.truck_id_number}</td>
						
					</tr>
					<tr>
						<td>运费： ${load.load_fee}</td>
						<td>费用分摊方式： 
						<c:if test="${load.load_fee_allocation_way==2}">
							按体积分摊
						</c:if>
						<c:if test="${load.load_fee_allocation_way==3}">
							按重量分摊
						</c:if>
						<c:if test="${load.load_fee_allocation_way==1}">
							按单数分摊
						</c:if>
						</td>
						<td>总件数： ${load.load_amount}</td>
					</tr>
					<tr>
						<td>总体积/立方:${load.load_volume}</td>
						<td>总重量/公斤:${load.load_weight}</td>
					</tr>
					<tr>
						<td>备注：${load.load_remark}</td>
					</tr>
				</table>
				<div class="banner-list4-log">
					<table cellspacing="0" border="1" width="95%" >
						<tr>
							<th>提货费</th>
							<th>送货费</th>
							<th>短驳费</th>
							<th>现付运输费</th>
							<th>现付油卡费</th>
							<th>回付运输费</th>
							<th>到付运输费</th>
							<th>整车保险费</th>
							<th>发车装车费</th>
							<th>发站其他费</th>
							<th>到站卸车费</th>
							<th>到站其他费</th>
						</tr>
						<tr align="center" >
							<c:if test="${load.load_transport_type==1}">
								<td>${load.load_fee}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${load.load_transport_type==2}">
								<td></td>
								<td></td>
								<td>${load.load_fee}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${load.load_transport_type==3}">
								<td></td>
								<td></td>
								<td></td>
								<td>${empty load.load_nowtrans_fee?"":load.load_nowtrans_fee}</td>
								<td>${empty load.load_nowoil_fee?"":load.load_nowoil_fee}</td>
								<td>${empty load.load_backtrans_fee?"":load.load_backtrans_fee}</td>
								<td>${empty load.load_attrans_fee?"":load.load_attrans_fee}</td>
								<td>${empty load.load_allsafe_fee?"":load.load_allsafe_fee}</td>
								<td>${empty load.load_start_fee?"":load.load_start_fee}</td>
								<td>${empty load.load_other_fee?"":load.load_other_fee}</td>
							</c:if>
							<c:if test="${load.load_transport_type==4}">
								<td></td>
								<td>${load.load_fee}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:if>
								<td>${empty load.load_atunload_fee?"":load.load_atunload_fee}</td>
								<td>${empty load.load_atother_fee?"":load.load_atother_fee}</td>

						</tr>
					</table>
				</div>
			</div>

			<div class="banner-right-list3">
				<p class="banner-right-p">运单列表</p>
				<div class="banner-right-list3-margin">
					<table>
						<tr>
							<th>序号</th>
							<th>运单号</th>
							<th>开单网点</th>
							<th>开单日期</th>
							<th>托运方</th>
							<th>收货方</th>
							<th>出发地</th>
							<th>到达地</th>
							<th>货物名称</th>
							<th>件数</th>
							<th>体积/立方</th>
							<th>重量/公斤</th>
						</tr>
						<c:forEach items="${ships}" var="ship" varStatus="i">
				   	    	<tr>
				   	    		<td>${i.index+1}</td>
				   	    		<td style="color: #3974f8;">${ship.ship_sn}</td>
				   	    		<td>${ship.snetworkName}</td>
				   	    		<td><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></td>
				   	    		<td>${ship.sendName}</td>
				   	    		<td>${ship.receiverName}</td>
				   	    		<td>${ship.fromCity}</td>
				   	    		<td>${ship.toCity}</td>
				   	    		<td>${ship.proname}</td>
				   	    		<td>${ship.ship_amount}</td>
				   	    		<td>${ship.ship_volume}</td>
				   	    		<td>${ship.ship_weight}</td>
				   	    	</tr>
	   	    			</c:forEach>
					</table>
				</div>
			</div>

		</div>
</body>
</html>