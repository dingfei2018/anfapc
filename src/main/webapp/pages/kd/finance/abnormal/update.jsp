<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/lineview.css" />

<link rel="stylesheet"
	href="${ctx}/static/pc/layui/css/layui.css?v=${version}" />
<script src="${ctx}/static/common/js/jquery.js"></script>
<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
</head>
<body>

	<div id="MyDiv3" class="white_content3">
		<p class="banner-list5-type">修改异动</p>

		<div class="banner-list5-div1">
			<p class="banner-div-title">异动信息</p>
			<ul>
				<li>
					<p>
						<input type="hidden" name="id" id ="id" value="${KdShipAbnormal.id}"/>
						运单号：<input type="text" disabled="disabled" KdShipAbnormal
							value="${KdShipAbnormal.ship_sn}" /></span>
					</p>
				</li>
				<li>
					<p>
						开单网点：<input type="text" disabled="disabled"
							value="${KdShipAbnormal.netWorkName}" /></span>

					</p>
				</li>
				<li>
					<p>
						<input type="hidden" name="id" id ="id" value="${KdShipAbnormal.id}"/>
						出发地：<input type="text" disabled="disabled" KdShipAbnormal
							value="${KdShipAbnormal.fromAdd}" /></span>
					</p>
				</li>
				<li>
					<p>
						到达地：<input type="text" disabled="disabled"
							value="${KdShipAbnormal.toAdd}" /></span>
					</p>
				</li>

				<li>
					<p>
						开单日期：<input type="text" disabled="disabled"
							value="${KdShipAbnormal.create_time}" /></span>

					</p>
				</li>
				<li>
					<p>
						托运方：<input type="text" disabled="disabled"
							value="${KdShipAbnormal.senderName}" /></span>
					</p>
				</li>
				<li>
					<p>
						收货方：<input type="text" disabled="disabled"
							value="${KdShipAbnormal.receiverName}" /></span>
					</p>
				</li>

		</div>

		<div class="banner-list5-div1">
			<br>
			<hr><br>
			<li>
				<p>
					异动增加：<input type="text" name="plus_fee"  id="plus_fee" value="${KdShipAbnormal.plus_fee}">
				</p>
			</li>

			<li>
				<p>
					异动减款：<input type="text" name="minus_fee"  id="minus_fee" value="${KdShipAbnormal.minus_fee}">
				</p>
			</li>

			<li style="float: none;width: 550px;margin-top:20px;display: inline-block;">
				<span>
					异动结算网点：
				</span>
				<select  name="networkid"  id="networkid">
					<c:forEach items="${networks}" var="net">
						<option value="${net.id}"
							${KdShipAbnormal.network_id==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
					</c:forEach>
					</select>
			</li>

			<li style="float: none;width: 600px;margin-top:20px;display: inline-block;">
				<p>
					异动原因：<input style="width: 500px" type="text"  name="cause"  id="cause" value="${KdShipAbnormal.cause}">
				</p>
			</li>
			</ul>


		</div><br><br>
		<button class="banner-list5-div1-button" onclick="saveLine()">确认修改</button>
		<br />
	</div>
	</div>
	<script type="text/javascript">

	
	function saveLine(){
		var networkid=$("#networkid").val();
		var cause=$("#cause").val();
		var id=$("#id").val();
		var plus_fee= ($("#plus_fee").val()==""?0:$("#plus_fee").val());
		var minus_fee= ($("#minus_fee").val()==""?0:$("#minus_fee").val());
		var numTest= /(^[0-9]\d*\.\d*$)|(^\d+$)/;
		if(networkid==""){
			Anfa.show("请选择异动结算网点","#networkId");
			return ;	
		}
		if(plus_fee>0&minus_fee>0){
            layer.msg("异动增加和运单减款不能同时有值",{time: 2000});
            return;
		}
		if(cause==""){
			Anfa.show("请输入异动原因","#cause");
			return ;
		}
		if(!numTest.test(plus_fee)){
			 Anfa.show("请输入正确的价格","#plus_fee");
 			 return;
 		}

		if(!numTest.test(minus_fee)){
			 Anfa.show("请输入正确的价格","#minus_fee");
 			 return;
 		}
		$.ajax({
			type:"post",
			dataType:"json",
			url:"${ctx}/kd/finance/abnormal/updateAbnormal",
			data:{id:id,networkId:networkid,plus_fee:plus_fee,minus_fee:minus_fee,cause:cause},
			success:function(data){
				if(data.state=="SUCCESS"){
					layer.msg(data.msg,{time: 1000},function(){
						
					parent.layer.closeAll();
					});
				}else{
					layer.msg(data.msg,{time: 2000});
				}

            }
		});
		
	}
	</script>
</body>
</html>