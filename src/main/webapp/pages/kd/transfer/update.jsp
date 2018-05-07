<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改中转运单</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css"/>
		
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>	
		
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		
		<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
		<style>
			.gloup{
				width: auto;
				height: auto;
				margin: auto;
				position: relative;
				overflow: hidden;
			}
			.gloup .p{
				font-weight: bold;
				text-align: center;
				font-size: 24px;
			}
			.gloup-list{
				width: 1150px;
				height: auto;
				border: 1px solid #fff;
				border-bottom: 1px solid #dcdcdc;
				margin: auto;
				padding-bottom: 20px;
			}
			.gloup-list .pi{
				color: #0000FF;
				font-weight: bold;
			}
			.gloup-list table{
				width: 1150px;
			}
			.gloup-list td{
				height: 40px;
			}
			.gloup-list2{
				width: 1150px;
				height: auto;
				border: 1px solid #fff;
				margin: auto;
				padding-bottom: 20px;
				margin-top: 40px;
			}
			.gloup-list2 .pi{
				color: #0000FF;
				font-weight: bold;
			}
			.gloup-list2 span{
				margin-left: 10px;
			}
			.gloup-list2 .span2{
				margin-left: 160px;
			}
			.gloup-list2 select{
				width: 150px;
				height: 30px;
			}
			.gloup-list2 input{
				width: 150px;
				height: 25px;
			}
			.gloup-list2 .input{
				margin-top: 20px;
				
			}
			.gloup-list2 .span{
				margin-left: 10px;
			}
			.button{
				/* position: absolute;
				left: 50%; */
				width: 150px;
				height: 50px;
				color: #fff;
				background-color: #3974f8;
				border-radius: 3px;
				cursor: pointer;
				border: none;
				font-size: 20px;
				margin-left: 400px;
				margin-top: 40px;
			}
			.button2{
				/* position: absolute;
				left: 50%; */
				width: 150px;
				height: 50px;
				color: #fff;
				background-color: #3974f8;
				border-radius: 3px;
				cursor: pointer;
				border: none;
				font-size: 20px;
				margin-left: 108px;
				margin-top: 40px;
			}
			.ac{
				/* position: absolute;
				left: 64%; */
				margin-top: 55px;
				color: #0000FF;
				margin-left: 24px;
			}
		</style>
	</head>
	<body>
		<div class="gloup">
			<p class="p">修改中转运单</p>
			<div class="gloup-list">
				<p class="pi">中转信息</p>
				<table>
					<tr>
					  <td>运单号 : ${shipTransfer.ship_sn }</td>
					  <td>开单日期  : ${shipTransfer.create_time }</td>
					  <td>开单网点 : ${shipTransfer.shipNetName }</td>
					</tr>
					<tr>
					  <td>中转网点 : ${shipTransfer.tranNetName }</td>
					  <td>出发地 : ${shipTransfer.fromAdd }</td>
					  <td>到达地  :${shipTransfer.toAdd }</td>
					</tr>
					<tr>
					  <td>托运方 ：${shipTransfer.senderName }</td>
					  <td>收货方 ：${shipTransfer.receiverName }</td>
					</tr>
					<tr>
					  <td>总件数 ：${shipTransfer.ship_amount }</td>
					  <td>总体积 ：${shipTransfer.ship_volume }</td>
					  <td>总重量 ：${shipTransfer.ship_weight }</td>
					</tr>
				</table>
			</div>
			
			<div class="gloup-list2">
				<form action="${ctx }/kd/transfer/updateTransfer" method="post" id="transferForm">
				<p class="pi">更换信息 :</p>
				<input type="hidden"  name="shipTransfer.ship_id" value="${shipTransfer.ship_id }"/>
				<input type="hidden" name="shipTransfer.network_id" value="${shipTransfer.network_id }"/>
				<input type="hidden" name="shipTransfer.ship_transfer_fee_status" value="${shipTransfer.ship_transfer_fee_status }"/>
				<span>中转方 : </span><input id="customerCombo" name="shipTransfer.transfer_id" class="easyui-combobox" />
				<span class="span2">中转单号 : </span><input type="text" id="y_transferSn" name="shipTransfer.ship_transfer_sn" value="${shipTransfer.ship_transfer_sn }"/>
				<span class="span2">中转时间 : </span><input type="text" id="time" name="shipTransfer.ship_transfer_time" value="${shipTransfer.ship_transfer_time }" /><br />
				<span class="span">中转费 : </span><input class="input" id="y_transferFee" name="shipTransfer.ship_transfer_fee" value="${shipTransfer.ship_transfer_fee }" type="text" />
				</form>
			</div>
			
			<input class="button" type="button" value="确认修改" onclick="updateTransfer();"/>
			<input class="button2" type="button" value="返回"  onclick="parent.layer.closeAll();"/>
		</div>
	</body>
	
	<script type="text/javascript">
	$('#customerCombo').combobox({
	    url:'${ctx }/kd/customer/getTransferCustomer',
	    valueField:'customer_id',
	    textField:'customer_corp_name',
	    height: 30,
	    panelWidth: 200,
		panelHeight: 'auto',
	    formatter: formatItem,
	    value:'${shipTransfer.transfer_id}'
	});
	
	function formatItem(row){
		var s = '<span >'+
				'<span>' + row.customer_corp_name + '</span>&nbsp;' +
				'<span">' + row.customer_name + '</span>&nbsp;';
		return s;
	}
	
	function updateTransfer(){
		var numTest=/(^[1-9]\d*\.\d*$)|(^\d+$)/;
		if($("#customerCombo").combobox('getValue').length==0){
			Anfa.show("请选择中转方","#y_transfer");
			return;
		}
		
		if($("#y_transferSn").val().length==0){
			Anfa.show("请输入中转单号","#y_transferSn");
			return;
		}
		
		if($("#y_transferFee").val().length==0){
			Anfa.show("请输入中转费","#y_transferFee");
			return;
		}else if(!numTest.test($("#y_transferFee").val())){
			Anfa.show("请输入非负数正确费用","#y_transferFee");
			return;
		}
		
		if($("#time").val().length==0){
			Anfa.show("请选择中转时间","#time");
			return;
		}
		
		
		$('#transferForm').submit();
		
	}
	
	</script>
</html>
