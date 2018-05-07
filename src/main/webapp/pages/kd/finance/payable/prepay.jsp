<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/css/advance.css"/>
<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/common/js/common.js"></script>
</head>
<body>
   <div class="advance">
        <p class="p">预付详情</p>
        <div class="advance-list">
            <table cellspacing="0">
                <tr>
                   <td colspan="3" class="advance-list-str">配载单号  : PZ201711200001</td>
                </tr>
                <tr>
                   <td>付款状态 : 未付款</td>
                   <td colspan="2">发车日期 :<fmt:formatDate value="${load.load_depart_time}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                   <td>出发地 : ${load.fromCity}</td>
                   <td colspan="2">到达地 : ${load.toCity}</td>
                </tr>
                <tr>
                   <td>司机 : ${load.truck_driver_name}</td>
                   <td>司机电话 : ${load.truck_driver_mobile}</td>
                   <td>车牌号: ${load.truck_id_number}</td>
                </tr>
                <tr>
                   <td colspan="1">应付金额 : ${load.load_fee}元</td>
                   <td colspan="2">已付金额 : ${load.load_fee_prepay}元</td>
                </tr>
            </table>
        </div>
        <div  class="advance-list2">
           <p>追加预付 :</p>
           <span>预付 : <input class="input" type="text" id="loadPrePay" onkeyup="onlyNumber(this)"  onafterpaste="onlyNumber(this)" > 元</span>
        </div>
        <button class="button" onclick="comfirmModify()" >确定</button>
   </div>
<script type="text/javascript">

function comfirmModify(){
	var loadPrePay = $("#loadPrePay").val();
	if(loadPrePay==""){
		Anfa.show("请输入预付款金额","#loadPrePay");
		return;
	}
	var totalpay = parseFloat(loadPrePay) + parseFloat("${load.load_fee_prepay}")
	var totalfee = parseFloat("${load.load_fee}")
	console.log(totalfee+"--"+totalpay);
	if(totalpay>=totalfee){
		Anfa.show("预付加已预付金额要小于应付金额","#loadPrePay");
		return;
	}
	var flag=true;
	if(flag){
		flag=false;
		$.ajax({
			url : "/kd/finance/payable/confirmPrePaymodify",
			type : 'POST',
			data : {loadPrePay:loadPrePay,loadId:"${load.load_id}"},
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

</script>
</body>
</html>
