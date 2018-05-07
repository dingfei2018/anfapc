<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>配载单打印</title>
		<script src="${ctx}/static/common/js/jquery.js"></script>
		<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>
		<script src="${ctx}/static/kd/js/print/loadprint.js"></script>
	</head>
	<body>
		<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
		       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
		</object>
		<div id="printHtml">
		<div style="width: auto;height: auto;border: 1px solid #ccc;margin: auto;padding-bottom: 10px;">
	        <h1 style="text-align: center;">${company.corpname}配载单 ${load.load_sn}</h1>
	        <div style="width: auto;height: auto;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;">
	          <table style="width: 100%;height: 150px;text-indent: 20px;">
	             <tr>
	                <td>车牌号 : ${load.truck_id_number}</td>
	                <td>司机 : ${load.truck_driver_name}</td>
	                <td>司机电话 :  ${load.truck_driver_mobile}</td>
	                <td>发车日期 : <fmt:formatDate value="${load.load_depart_time}" pattern="yyyy/MM/dd"/> </td>
	             </tr>
	             <tr>
	                <td>总件数 : ${load.load_amount}</td>
	                <td>总体积 : ${load.load_volume}</td>
	                <td>总重量 : ${load.load_weight}</td>
	                <td>运费 :  ${load.load_fee}</td>
	             </tr>
	             <tr>
	                <td colspan="1">出发地 : ${load.fromCity}</td>
	                <td colspan="3">到达地 : ${load.toCity}</td>
	             </tr>
	          </table>
	        </div>
	        <div style="width:auto;height: auto;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;">
	            <table style="width: 100%;height: auto;text-indent: 20px;text-align: center;">
	               <tr>
	                  <th>序号</th>
	                  <th>运单号</th>
	                  <th>签收</th>
	                  <th>货物名称</th>
	                  <th>件数</th>
	                  <th>体积</th>
	                  <th>重量</th>
	               </tr>
	                <c:forEach items="${ships}" var="ship" varStatus="i">
			   	    	<tr style="${i.index==33||(i.index-33)%44==0?'page-break-after:always':''}">
			   	    		<td>${i.index+1}</td>
			   	    		<td>${ship.ship_sn}</td>
			   	    		<td><input type="checkbox"></td>
			   	    		<td>${ship.proname}</td>
			   	    		<td>${ship.ship_amount}</td>
			   	    		<td>${ship.ship_volume}</td>
			   	    		<td>${ship.ship_weight}</td>
			   	    	</tr>
   	    			</c:forEach>
	            </table>
	        </div>
	        <div style="width: auto;height: auto;border: 1px solid #ccc;margin-top: 20px;border-left: none;border-right: none;">
	           <table style="width: 100%;height: auto;text-indent: 63px;">
	              <tr style="height:30px;">
	                 <td>配载人 : ${loaduser.realname}</td>
	                 <td>司机签收 : </td>
	                 <td>网点签收 : </td>
	              </tr>
	              <tr style="height:30px;">
	                 <td colspan="2">收货地址 : ${enet.addr}</td>
	                 <td>联系电话 : ${enet.sub_logistic_telphone}</td>
	              </tr>
	              <tr style="height:30px;">
	                 <td colspan="2">发货地址 : ${snet.addr}</td>
	                 <td>联系电话 : ${snet.sub_logistic_telphone}</td>
	              </tr>
	           </table>
	        </div>
	    </div>
		</div>
	    <div style="text-align:center">
	    	<button id="print" style="width: 120px;height: 40px;margin-top: 60px;font-size: 18px;color: #fff;border: none;background-color: #3974f8;cursor: pointer;">打印</button>
	    </div>
	</body>
	
</html>
