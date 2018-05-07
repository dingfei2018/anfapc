<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>打印页面</title>
		<%-- <link rel="stylesheet" href="${ctx}/static/kd/css/print.css" /> --%>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>
		<script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
	</head>
	<body>
	<div id="doc" >
	 <div style="width: 1075px;height:677px;border: 2px solid red;margin: auto; background: url(${ctx}/static/kd/img/waybillprint2.jpg) no-repeat center; position: relative;">
	         <div style="border:1px solid blue;width:412px; height:62px;position: absolute; right:33px; top:41px;">
	             <table style="width:412px; height:62px;text-align: center;">
	               <tr>
	                  <th>账单日期</th>
	                  <th>运单号</th>
	               </tr>
	               <tr>
	                  <td>2018-1-18</td>
	                  <td>888888888</td>
	               </tr>
	             </table>
	         </div>
	         <div style="border:1px solid yellow;width:1027px; height:447px;position: absolute; right:33px; top:148px;">
	              <div style="border:1px solid red; width:145px; height:82px;margin-left: 147px;"></div>
	              <div style="border:1px solid green; width:365px; height:82px;margin-left: 40px;position: absolute;left: 324px;top: -1px;"></div>
	              <div style="border:1px solid orange; width:219px; height:82px;position: absolute;right: 0px;top: 0px;"></div>
	              <div style="border:1px solid blue; width:1027px; height:131px;margin-top: 45px;"></div>
	         </div>
	    </div>
	</div>
	   
	        <button style="margin-left: 500px;margin-top: 50px;font-size: 18px;color: #fff;border: none;background-color: #3974f8;cursor: pointer;" id="print">打印</button>
	        <button onclick="window.location.href='${ctx}/kd/waybill'" style="margin-left:25px;">返回</button>
	    
	  
	</body>
</html>
