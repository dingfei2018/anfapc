<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/lineunload.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
	<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
</head>
<body>
	<div class="banner-sure">
		<h3>完成卸车</h3>
		
			<table>
				<tr>
					<td>车牌号：${load.truck_id_number}</td>
					<td>司机：${load.truck_driver_name}</td>
				</tr>
				<tr>
					<td>配载网点：${load.snetworkName}</td>
					<td>到货网点：${load.enetworkName}</td>
				</tr>
			</table>
		<ul>
			<li>到站卸车费： <input type="text" id="load_atunload_fee" placeholder="请填写"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></li>
			<li style="margin-left: 80px;">到站其他费： <input type="text" id="load_atother_fee" placeholder="请填写" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></li>
		</ul>
		<div class="sure-button">
			<button class="button1" onclick="changeCost()">确定</button>
			<button class="button2" style="cursor:pointer" onclick="parent.layer.closeAll()">取消</button>
		</div>
	</div>
	<script>
		 function changeCost(){
			if($("#load_atunload_fee").val()==""){
                layer.msg("请输入到站卸车费");
                return;
			}
			if($("#load_atother_fee").val()==""){
                layer.msg("请输入到站其他费");
                return;
			}
             $.ajax({
                 url : "/kd/transport/confirmUnloading",
                 type : 'POST',
                 data : {loadId:"${load.load_id}",loadType:${load.load_transport_type},atunloadFee:$("#load_atunload_fee").val(),atotherFee:$("#load_atother_fee").val()},
                 success:function(data){
                     if (data.success) {
                         layer.msg("卸车成功");
                         setTimeout(function(){parent.layer.closeAll();}, 1000);
                     } else {
                         layer.msg(data.msg);
                     }
                 }
             })
		 }


	</script>

</body>
</html>