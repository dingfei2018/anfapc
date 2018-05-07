<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改信息</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/handle.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/study/css/city-picker.css">
		<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.data.js"></script>
		<script src="${ctx}/static/pc/study/js/city-picker.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	</head>
<body>
<div id="MyDiv" class="white_content">
	<!--<div class="white_contents">
	新增收货方
	<span class="white_contents-span"></span>
</div>-->
   <div class="tab-list">
   	 <p>配载单号 : ${load.load_sn}</p>
   	 
     <table>
     	<tr>
     		<td>配载网点 :${load.snetworkName}</td>
     		<td>发车日期 : <fmt:formatDate value="${load.load_depart_time}" pattern="yyyy-MM-dd"/> </td>
     		<td>运输类型: ${load.load_sn}</td>
     	</tr>
     	<tr>
     		<td>出发地 :${load.fromCity}</td>
     		<td>到达地 : ${load.toCity}</td>
     		<td>司机 : ${load.truck_driver_name}</td>
     	</tr>
     	<tr>
     		<td>司机电话 : ${load.truck_driver_mobile}</td>
     		<td>车牌号 : ${load.truck_id_number}</td>
     		<td>总重量 : ${load.load_weight}</td>
     	</tr>
     	<tr>
     		<td>总件数 : ${load.load_amount}</td>
     		<td>总体积 : ${load.load_volume}立方</td>
     		<td>收货网点:${load.enetworkName}</td>
     	</tr>
     	<tr>
     		<td>预付金额: ${load.load_fee_prepay}元</td>
     	</tr>
     </table>
   </div>
   <div class="tab-list2">
   	<p>更换信息 :</p>
   	<span>运费(元) :</span><input type="text" value="${load.load_fee}" id="fee"  onkeyup="onlyNumber(this)"  onafterpaste="onlyNumber(this)" >
    <span class="span2">费用分摊方式 :</span>
     <select id="payId" onchange="payChange()">
    	   <option value="2" ${load.load_fee_allocation_way==2?"selected='selected'":""}>按体积</option>
    	   <option value="3" ${load.load_fee_allocation_way==3?"selected='selected'":""}>按重量</option>
    	   <option value="1" ${load.load_fee_allocation_way==1?"selected='selected'":""}>按单</option>
    </select>
   	    <table cellpadding="0" id="shiplist">
   	    	<tr>
   	    		<th>序号</th>
   	    		<th>运单号</th>
   	    		<th>开单日期</th>
   	    		<th>托运方</th>
   	    		<th>收货方</th>
   	    		<th>出发地</th>
   	    		<th>到达地</th>
   	    		<th>货物名称</th>
   	    		<th>件数</th>
   	    		<th>体积/立方</th>
   	    		<th>重量/公斤</th>
   	    		<th>分摊金额</th>
   	    	</tr>
   	    	
   	    	<c:forEach items="${ships}" var="ship" varStatus="i">
	   	    	<tr volume="${ship.ship_volume}" weight="${ship.ship_weight}" data="${ship.id}">
	   	    		<td>${i.index+1}</td>
	   	    		<td>${ship.ship_sn}</td>
	   	    		<td><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd"/></td>
	   	    		<td>${ship.sendName}</td>
	   	    		<td>${ship.receiverName}</td>
	   	    		<td>${ship.fromCity}</td>
	   	    		<td>${ship.toCity}</td>
	   	    		<td>${ship.proname}</td>
	   	    		<td>${ship.ship_amount}</td>
	   	    		<td>${ship.ship_volume}</td>
	   	    		<td>${ship.ship_weight}</td>
	   	    		<td><input type="text" value="${ship.fee}" onkeyup="onlyNumber(this)"  onafterpaste="onlyNumber(this)" /></td>
	   	    	</tr>
   	    	</c:forEach>
   	    </table>
   </div>
   <button onclick="check()">确定修改</button><a class="ac" href="javascript:parent.layer.closeAll();">返回</a>
</div>
<script type="text/javascript">
function payChange(){
	var feeType = $("#payId").val();
	var totalfee = $("#fee").val();
	var size = parseInt("${load.load_count}");
	var avgfee = 0;
	var remain = 0;
	var inrfee = 0;
	//费用分摊
	if(feeType==1){//按单
		var count = "${load.load_count}";
		avgfee = (totalfee/count).toFixed(2);
		var temp = ((count-1)*avgfee).toFixed(2);
		remain = (totalfee-temp).toFixed(2);
		console.log("按单分摊${load.load_count}，avgfee=" + avgfee + ",remain=" + remain + ", prefee=" +temp);
	}else if(feeType==2){//按体积
		var count = "${load.load_volume}";
		if(count>0){
			avgfee = (totalfee/count).toFixed(2);;
		}
		console.log("按体积分摊${load.load_volume}，avgfee=" + avgfee);
	}else if(feeType==3){//按重量
		var count = "${load.load_weight}";
		if(count>0){
			avgfee = (totalfee/count).toFixed(2);;;
		}
		console.log("按按重量分摊${load.load_weight}，avgfee=" + avgfee);
	}
	
	if(feeType==1){//按单
		$("#shiplist  tr:gt(0)").each(function(i,o){
			if(size==(i+1)){
				$(o).find("input").val(remain);
			}else{
				$(o).find("input").val(avgfee);
			}
		})
	}else if(feeType==2){//按体积
		$("#shiplist  tr:gt(0)").each(function(i,o){
			if(size==(i+1)){
				$(o).find("input").val(remain);
				remain = (totalfee - inrfee).toFixed(2);
				$(o).find("input").val(remain);
				console.log("按体积，avgfee=" + avgfee + ",remain=" + remain + ", prefee=" +inrfee);
			}else{
				var volume = $(o).attr("volume");
				var temp = (avgfee*volume).toFixed(2);
				inrfee += temp;
				$(o).find("input").val(temp);
			}
		})
	}else if(feeType==3){//按重量
		$("#shiplist  tr:gt(0)").each(function(i,o){
			if(size==(i+1)){
				remain = (totalfee - inrfee).toFixed(2);
				$(o).find("input").val(remain);
				console.log("按单重量，avgfee=" + avgfee + ",remain=" + remain + ", prefee=" +inrfee);
			}else{
				var wieght = $(o).attr("weight");
				var temp = (avgfee*wieght).toFixed(2);
				inrfee += temp;
				$(o).find("input").val(temp);
			}
		})
	}
}

function check(){
	var total = 0;
	var totalfee = $("#fee").val();
	if(totalfee<=0){
		layer.msg("运费不能小于零");
		return;
	}
	
	var prefee = parseFloat("${load.load_fee_prepay}");
	if(prefee>=totalfee){
		layer.msg("运费必须要大于预付款金额");
		return;
	}
	var data = "";
	$("#shiplist tr:gt(0)").each(function(i,o){
		var num = parseFloat($(o).find("input").val());
		total=(total+num);
		data+=$(o).attr("data")+":"+num+",";
	})
	if(totalfee!=total){
		layer.msg("分摊费用和与总费用不一致，请重新确认");
		return;
	}
	data=data.substring(0,data.length-1);
	var flag=true;
	if(flag){
		flag=false;
		$.ajax({
			url : "/kd/finance/payable/confirmmodify",
			type : 'POST',
			data : {udata:data, loadfee:totalfee,loadId:"${load.load_id}",allocationWay:$("#payId").val()},
			success:function(data){
				if (data.success) {
					layer.msg("修改成功");
					setTimeout(function(){parent.layer.closeAll();}, 1000);
				} else {
					layer.msg(data.msg);
				}
				flag=true;
			}
		});
	}
	
}

function onlyNumber(obj){
	//先把非数字的都替换掉，除了数字和. 
	obj.value = obj.value.replace(/[^\d\.]/g,''); 
	//必须保证第一个为数字而不是. 
	obj.value = obj.value.replace(/^\./g,''); 
	//保证只有出现一个.而没有多个. 
	obj.value = obj.value.replace(/\.{2,}/g,'.'); 
	//保证.只出现一次，而不能出现两次以上 
	obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
}

	
function replaceset(index, val) {//匹配第一次非数字字符
	
	obj.value = obj.value.replace(/[^\d\.]/g,''); 
	//必须保证第一个为数字而不是. 
	obj.value = obj.value.replace(/^\./g,''); 
	//保证只有出现一个.而没有多个. 
	obj.value = obj.value.replace(/\.{2,}/g,'.'); 
	//保证.只出现一次，而不能出现两次以上 
	obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');
	
    var rtextRange = null;
    if (obj.setSelectionRange) {
        obj.setSelectionRange(index, index);
    } else {//支持ie
        rtextRange = obj.createTextRange();
        rtextRange.moveStart('character', index);
        rtextRange.collapse(true);
        rtextRange.select();
    }
 }
</script>
</body>
</html>