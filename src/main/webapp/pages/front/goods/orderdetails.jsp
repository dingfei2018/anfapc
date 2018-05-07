<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
		<html>
		<head>
		<meta charset="UTF-8">
		<title>订单详情</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/online2.css?v=${version}"/> 
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}" />
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}" />
		<script src="${ctx }/static/pc/study/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
</head>
<body>
	<%@ include file="../common/loginhead.jsp"%>
	<div class="banner">
	
	<div class="banner-top">
	
	   <span>订单详情：<b>${order.from_city}</b> — <b>${order.to_city}</b></span>
	   <span class="banner-top-span">订单编号： <b>${order.order_number}</b></span>
	   <span style="margin-left:40px;">时间日期：<b>${order.create_time}</b></span>
	   <span class="banner-top-spans">状态： <b class="banner-top-b">
   			<c:if test="${order.ship_status==1}">
       			交易进行中
       		</c:if>
       		<c:if test="${order.ship_status==2}">
       			交易已确认
       		</c:if>
	   </b></span>
	</div>
	
	<table cellspacing="0">
	   <tr>
	      <th>货物名称</th>
	      <th>货物体积(方)</th>
	      <th>货物重量(公斤)</th>
	   </tr>
		<c:forEach items="${ordergoods}" var="i">
			<tr>
				<td>
					<c:if test="${i.good_name==1}">设备</c:if>
					<c:if test="${i.good_name==2}">机械</c:if>
					<c:if test="${i.good_name==3}">煤炭</c:if>
					<c:if test="${i.good_name==4}">矿产</c:if>
					<c:if test="${i.good_name==5}">钢材</c:if>
					<c:if test="${i.good_name==6}">饲料</c:if>
					<c:if test="${i.good_name==7}">建材</c:if>
					<c:if test="${i.good_name==8}">木材</c:if>
					<c:if test="${i.good_name==9}">粮食</c:if>
					<c:if test="${i.good_name==10}">饮料</c:if>
					<c:if test="${i.good_name==11}">蔬菜</c:if>
					<c:if test="${i.good_name==12}">水果</c:if>
					<c:if test="${i.good_name==13}">畜产品</c:if>
					<c:if test="${i.good_name==14}">农资</c:if>
					<c:if test="${i.good_name==15}">服装鞋包</c:if>
					<c:if test="${i.good_name==16}">日用百货</c:if>
					<c:if test="${i.good_name==17}">药品</c:if>
					<c:if test="${i.good_name==18}">化工产品</c:if>
					<c:if test="${i.good_name==19}">电子电器</c:if>
					<c:if test="${i.good_name==20}">仪表仪器</c:if>
					<c:if test="${i.good_name==21}">汽车摩托</c:if>
					<c:if test="${i.good_name==22}">汽配摩配</c:if>
					<c:if test="${i.good_name==23}">图书音像</c:if>
					<c:if test="${i.good_name==24}">纸类包装</c:if>
					<c:if test="${i.good_name==25}">工艺礼品</c:if>
					<c:if test="${i.good_name==26}">文体用品</c:if>
					<c:if test="${i.good_name==27}">危险品</c:if>
				</td>
				<td>${i.volume}</td>
				<td>${i.weight_unit=='t'?i.weight*1000:i.weight}</td>
			</tr>
			</c:forEach>
			<tr style="background-color: #e3e7f7;color:#f00;">
			   <td>总计</td>
			   <td>${order.all_volume}</td>
			   <td>${order.all_weight}</td>
			</tr>
	</table>
	
	<div class="banner-topos">
	   <p class="banner-topos-p">发货方信息</p>
	   <p>发货方：${order.from_corpname}<span>联系方式：<b>${order.sender_mobile}</b></span></p>
	   <p>发货人： ${order.sender} </p>
	   <p>发货地：${order.from_addr}</p>
	</div>
	<div class="banner-topos">
	   <p class="banner-topos-p">收货方信息</p>
	   <p>收货方：${order.corpname}<span>联系方式：<b>${order.receiver_mobile}</b></span></p>
	   <p>收货人： ${order.receiver} </p>
	   <p>收货地：${order.to_addr}</p>
	</div>
	  <p class="banner-op"><a href="${ctx}/map/getAddressMap?address=${order.to_addr}&corname=${order.corpname}">查看收货方地理位置</a></p>
	</div>
	
	<%@ include file="../common/loginfoot.jsp"%>

</body>
</html>

