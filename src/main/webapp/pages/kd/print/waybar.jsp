<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>运单条形码打印</title>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>
	</head>
	<body>
		<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
		       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
		</object>
		
		<div >
			<input type="hidden" id="size" value="${size}">
			<input type="hidden" id="fromnetworkname" value="${fromnetWorkName}">
			<input type="hidden" id="tonetworkname" value="${tonetWorkName}">
			<input type="hidden" id="goodsSn" value="${goodsSn}">
			<input type="hidden" id="receiverName" value="${receiverName}">
			<input type="hidden" id="time" value="<fmt:formatDate value="${time}" pattern="yyyy-MM-dd"/>">
			<input type="hidden" id="companyName" value="${companyName}">
			<input type="hidden" id="detailAddress" value="${detailAddress}">
			<input type="hidden" id="telphone" value="${telphone}">
		</div>
		<div>
			启动打印中。。。。。。。
		</div>
	    <div style="text-align:center;display: none;">
	    	<button id="print" style="width: 120px;height: 40px;margin-top: 60px;font-size: 18px;color: #fff;border: none;background-color: #3974f8;cursor: pointer;">打印</button>
	    </div>
<script src="${ctx}/static/kd/js/print/waybarprint.js?v=${version}"></script>
</body>
</html>
