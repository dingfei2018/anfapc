<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>首页</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/translate.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/kd/js/echarts.common.min.js"/></script>
		<script src="${ctx}/static/common/js/ichart.1.2.1.min.js"></script>
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/layui/layui.js"></script>
	</head>
	<body>
	    <%@ include file="common/head2.jsp" %>
		<%@ include file="common/head.jsp" %>
		<div class="banner">
			<div class="banner-list">
				<div class="banner-right-list3">
					<ul>
					   <li><a href="${ctx}/pages/kd/index.jsp" class="active">操作流程</a></li>
					   <li><a href="${ctx}/pages/kd/indexr.jsp">财务中心</a></li>
					</ul>
					<img src="${ctx}/static/kd/img/shouye06.png" />
					<a class="banner-right-a1" <c:if test="${fn:contains(sessionScope.baseDataName, '客户列表')}" > href="kd/customer"</c:if>></a>
					<a class="banner-right-a2" <c:if test="${fn:contains(sessionScope.baseDataName, '车辆列表')}" > href="kd/truck"</c:if>></a>
					<a class="banner-right-a3" <c:if test="${fn:contains(sessionScope.reDeMage, '运单列表')}" > href="kd/waybill"</c:if>></a>
					<a class="banner-right-a4" <c:if test="${fn:contains(sessionScope.reDeMage, '库存查询')}" > href="kd/kucun"</c:if>></a>
					<a class="banner-right-a5" <c:if test="${fn:contains(sessionScope.reDeMage, '中转管理')}" > href="kd/transfer"</c:if>></a>
					<a class="banner-right-a6" <c:if test="${fn:contains(sessionScope.reDeMage, '装车配载')}" > href="kd/loading"</c:if>></a>
					<a class="banner-right-a7" <c:if test="${fn:contains(sessionScope.reDeMage, '客户签收')}" > href="kd/sign"</c:if>></a>
					<a class="banner-right-a8" <c:if test="${fn:contains(sessionScope.reDeMage, '到货确认')}" > href="kd/transport"</c:if>></a>
					<a class="banner-right-a9" <c:if test="${fn:contains(sessionScope.reDeMage, '库存查询')}" > href="kd/kucun"</c:if>></a>
					<a class="banner-right-a10" <c:if test="${fn:contains(sessionScope.reDeMage, '装车配载')}" > href="kd/loading"</c:if>></a>
					<a class="banner-right-a11" <c:if test="${fn:contains(sessionScope.reDeMage, '中转管理')}" > href="kd/transfer"</c:if>></a>
					<a class="banner-right-a12" <c:if test="${fn:contains(sessionScope.reDeMage, '客户签收')}" > href="kd/sign"</c:if>></a>
					<a class="banner-right-a13" <c:if test="${fn:contains(sessionScope.receiptName, '我的回单')}" > href="kd/receipt"</c:if>></a>
					<a class="banner-right-a14" <c:if test="${fn:contains(sessionScope.receiptName, '我的回单')}" > href="kd/receipt"</c:if>></a>
					<a class="banner-right-a15" <c:if test="${fn:contains(sessionScope.receiptName, '我的回单')}" > href="kd/receipt"</c:if>></a>
					<a class="banner-right-a16" <c:if test="${fn:contains(sessionScope.receiptName, '我的回单')}" > href="kd/receipt"</c:if>></a>
				</div>
				
			</div>
		</div>
		<%@ include file="common/loginfoot.jsp" %>
	
	</body>
</html>
