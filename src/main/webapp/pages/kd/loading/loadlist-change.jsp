<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>更换车辆</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/departure.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
		<%@ include file="../common/commonhead.jsp" %>
	</head>
<body>
<div id="MyDiv" class="white_content">
   <div class="tab-list">
   	 <p>配载单号 : ${load.load_sn}</p>
     <table>
     	<tr>
     		<td>原车牌号 : ${truck.truck_id_number}</td>
     		<td>原司机 : ${truck.truck_driver_name}</td>
     		<td>原司机电话 : ${truck.truck_driver_mobile}</td>
     	</tr>
     	<tr>
     		<td>出发地：${load.fromCity}</td>
			<td>出发地：${load.toCity}</td>
			<td>运费:${load.load_fee} </td>
		</tr>
		<tr>
			<td>配载网点：${load.snetworkName}</td>
			<td>到货网点：${load.enetworkName}</td>
     	</tr>
     </table>
   </div>
	<form onsubmit="return false;" id="searchFrom">
	  <div class="tab-list2">
	  	   <p>更换信息 :</p>
	  	   <input type="hidden" name="loadId" value="${load.load_id}">
	   	    <span class="span">车牌号 : </span><input type="text"  name="truck_id_number" id="truck_id_number"/><span class="red" id="truck_id_numberId">*</span>
	   	    <span>司机姓名: </span><input type="text" name="truck_driver_name" id="truck_driver_name"/><span class="red">*</span>
	   	    <span>司机电话: </span><input type="text"  name="truck_driver_mobile" id="truck_driver_mobile"/><span class="red">*</span><br>
	   	    <span class="span">发车时间: </span><input name="load_depart_time" id="load_depart_time" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value="${load.load_depart_time}" pattern="yyyy-MM-dd HH:mm:ss"/> "/><span class="red">*</span>
	   	    
	  </div>
	  <button onclick="save()">提交</button>
	</form>
</div>
<script type="text/javascript">
$(function(){
$("#truck_id_number").combogrid({
	panelWidth: 320,
	idField: 'truck_id_number',
	height:30,
	pagination: true,
	textField: 'truck_id_number',
	url: '/kd/truck/searchTruck',
	method: 'get',
	columns: [[
		{field:'truck_id_number',title:'车牌号',width:200},
		{field:'truck_driver_name',title:'司机名',width:150},
		{field:'truck_driver_mobile',title:'司机电话',width:200}
	]],
	keyHandler:{
        up: function() {},
        down: function() {},
        enter: function() {},
        query: function(q) {
            //动态搜索
           $('#truck_id_number').combogrid("grid").datagrid("reload", {'queryName': q});
           $('#truck_id_number').combogrid("setValue", q);
        }
    },
    onSelect:function(rowIndex, rowData){
    	$("#truck_driver_mobile").val(rowData.truck_driver_mobile);
    	$("#truck_driver_name").val(rowData.truck_driver_name);
    },
	fitColumns: true
})
})

function save(){
	if($("input[name='truck_id_number']").val()==""){
		Anfa.show("请填写车牌号码","#truck_id_numberId");
		return;
	}
	if($("#truck_driver_name").val()==0){
		Anfa.show("请填写司机姓名","#truck_driver_name");
		return;
	}
	if($("#truck_driver_mobile").val()==""){
		Anfa.show("请填写司机电话","#truck_driver_mobile");
		return;
	}
	if($("#load_depart_time").val()==""){
		Anfa.show("请填写发车时间","#load_depart_time");
		return;
	}
	var param = $("#searchFrom").serialize();
	$.ajax({
		type:'POST',
		url:'${ctx}/kd/loading/changetruck',
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
