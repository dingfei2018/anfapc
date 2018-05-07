<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/modify.css" />
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
		<script src="${ctx }/static/pc/js/citys.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	</head>
	<body>
	
	<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" class="white_content">
			<!--<div class="white_contents">
				新增收货方
				<span class="white_contents-span"></span>
			</div>-->
		   <div class="tab-list">
		   	 <p>运单号 : ${kdShip.ship_sn}</p>
		   	 
		     <table>
		     	<tr>
		     		<td>开单日期 :${kdShip.create_time}</td>
		     		<td>托运方 :${kdShip.senderName}</td>
		     		<td>收货方 :${kdShip.receiverName}</td>
		     	</tr>
		     	<tr>
		     		<td>开单网点 : ${kdShip.netWorkName}</td>
		     		<td>出发地 : ${kdShip.fromAdd}</td>
		     		<td>到达地 : ${kdShip.toAdd}</td>
		     	</tr>
		     	<tr>
		     		<td>体积 : ${kdShip.ship_volume}立方</td>
		     		<td>重量 : ${kdShip.ship_weight}公斤</td>
		     		<td>件数 : ${kdShip.ship_amount}</td>
		     	</tr>
		     	<tr>
		     		<td>应收总金额 :${kdShip.ship_total_fee}元</td>
		     	</tr>
		     </table>
		   </div>
		   <form action="#" method="post" id="myFrom" onsubmit="return false">
			   <input type="hidden" name="kdShip.ship_id" value="${kdShip.ship_id}">
		   <div class="tab-list2">
		   	<p>更换信息 :</p>
		   	<span>付款方式 :</span><select name="kdShip.ship_pay_way">
			   <option value="1" <c:if test="${kdShip.ship_pay_way==1}">selected</c:if> >现付</option>
		   		<option value="2" <c:if test="${kdShip.ship_pay_way==2}">selected</c:if>>提付</option>
		   		<option value="3" <c:if test="${kdShip.ship_pay_way==3}">selected</c:if>>到付</option>
		   		<option value="4" <c:if test="${kdShip.ship_pay_way==4}">selected</c:if>>回单付</option>
		   		<option value="5" <c:if test="${kdShip.ship_pay_way==5}">selected</c:if>>月付</option>
		   	</select>
		   	    <table cellpadding="0">
		   	    	<tr>
		   	    		<th>运费</th>
		   	    		<th>提货费</th>
		   	    		<th>送货费</th>
		   	    		<th>保费</th>
		   	    		<th>包装费</th>
		   	    		<th>其他费</th>
		   	    	</tr>
		   	    	<tr>
		   	    		<td><input type="text" name="kdShip.ship_fee" value="${kdShip.ship_fee}"/></td>
		   	    		<td><input type="text" name="kdShip.ship_pickup_fee" value="${kdShip.ship_pickup_fee}"/></td>
		   	    		<td><input type="text" name="kdShip.ship_delivery_fee" value="${kdShip.ship_delivery_fee}"/></td>
		   	    		<td><input type="text" name="kdShip.ship_insurance_fee" value="${kdShip.ship_insurance_fee}"/></td>
		   	    		<td><input type="text" name="kdShip.ship_package_fee" value="${kdShip.ship_package_fee}"/></td>
		   	    		<td><input type="text" name="kdShip.ship_addon_fee" value="${kdShip.ship_addon_fee}"/></td>
		   	    	</tr>
		   	    </table>
		   </div>
		   <button onclick="update()">确定</button><a class="ac" href="javascript:parent.layer.closeAll();">返回</a>
		</div>
	</form>
	<script>
		function update() {
            $.ajax({
                type : "post",
                url : "${ctx}/kd/finance/receivable/updateShip",
                data : $('#myFrom').serialize(),
				dataType:"json",
                success : function(data) {
                    console.log(data);
                    if (data="success") {
                        layer.msg("更新成功！",{time: 2000},function(){
                           window.location.reload();
                            parent.layer.closeAll();
                        });
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
		}
	</script>

	</body>
</html>
