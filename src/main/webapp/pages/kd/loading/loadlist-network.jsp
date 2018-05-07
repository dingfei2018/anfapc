<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>更换到货网点</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/study/css/city-picker.css">
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.data.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/common/js/common.js"></script>
		<style>
			.tab-list2 span
			{
				margin-left: 0;
			}
			.city-picker-span .title{
		  		float: right;
		  		white-space: nowrap; 
		  }
		  .city-select-content{
		  	padding: 0;		  }
		</style>
	</head>
<body>
<div id="MyDivs" class="white_content">
   <div class="tab-list">
   	 <p>配载单号 : ${load.load_sn}</p>
     <table>
     	<tr>
     		<td>配载网点 : ${load.snetworkName}</td>
     		<td>发车日期 : <fmt:formatDate value="${load.create_time}" pattern="yyyy-MM-dd"/>   </td>
     		<td>出发地 : ${load.fromCity}</td>
     	</tr>
     	<tr>
     		<td>运输类型 : 
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
     		<td>司机 : ${load.truck_driver_name}</td>
     		<td>司机电话 : ${load.truck_driver_mobile}</td>
     	</tr>
     	<tr>
     		<td>车牌号 : ${load.truck_id_number}</td>
     		<td>运费: ${load.load_fee}</td>
     		<td>费用分摊方式 : 
     		<c:if test="${load.load_fee_allocation_way==1}">
     			按单
     		</c:if>
     		<c:if test="${load.load_fee_allocation_way==2}">
     			按体积
     		</c:if>
     		<c:if test="${load.load_fee_allocation_way==3}">
     			按重量
     		</c:if>
     		</td>
     	</tr>
     	<tr>
     		<td>总件数 : ${load.load_amount}</td>
     		<td>总体积 : ${load.load_volume}</td>
     		<td>总重量 : ${load.load_weight}</td>
     	</tr>
     </table>
   </div>
   
   <div class="tab-list2">
   	<form onsubmit="return false;" id="searchFrom">
   		<input type="hidden" name="loadId" value="${loadId}">
   	   <p>更换信息 :</p>
   	    <strong>原到货网点 : ${load.enetworkName}</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>原到达地 : ${load.toCity}</strong><br />
   	    <span class="span">改到货网点 : </span>
   	    <select name="load_next_network_id" class="select" id="load_next_network_id">
   	   		<option value="0">请选择网点</option>
			<c:forEach items="${networks}" var="net">
				<option value="${net.id}">${net.sub_network_name}</option>
			</c:forEach>
   	    </select>
   	    <div class="banner-right-list-liopc" style="margin-left: 420px; width: 160px; height: 27px;position: relative;left: -380px;">
				<span class="span4">到达地：</span>
				<div class="form-group" id="city1">
					<div style="position: relative;">
						<input type="hidden" name="load_delivery_to" id="load_delivery_to">
						<input id="city-picker2" class="form-control"
							placeholder="请选择省/市/区" readonly type="text"
							data-toggle="city-picker">
					</div>
					<script>
						$(function(){
							$(".city-picker-span").css("width","auto").css("height","27px").css("line-height","27px");
							$(".city-select-content").css("paddixng","0 0");
						})
					</script>
				</div>
			</div>
		</form>
   </div>
   <button onclick="save()">提交</button>
</div>
<script type="text/javascript">
function save(){
	var network_id = $("#load_next_network_id").val();
	var type = "${load.load_transport_type}";
	if(network_id==0&&(type==2||type==3)){//短驳或者干线必选
		Anfa.show("请选择到货网点","#load_next_network_id");
		return;
	}
	
	if(network_id=="${load.network_id}"){
		Anfa.show("到货网点与配载网点不能相同","#load_next_network_id");
		return;
	}
	
	if($("#load_delivery_to").val()==""){
		Anfa.show("请填选择到达地","#city1");
		return;
	}
	
	var param = $("#searchFrom").serialize();
	$.ajax({
		type:'POST',
		url:'${ctx}/kd/loading/changenetwork',
		data:param,
		success:function(data){
			if (data.success) {
				layer.msg("修改成功");
				setTimeout(function(){parent.layer.closeAll();}, 1000);
			} else {
				layer.msg(data.msg);
			}
		}
	});
}
</script>
</body>
</html>