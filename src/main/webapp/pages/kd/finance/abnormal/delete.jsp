<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/lineview.css" />

<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}" />
<script src="${ctx}/static/common/js/jquery.js"></script>
<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
</head>
<body>

	<div id="MyDiv3" class="white_content3">
		<p class="banner-list5-type">删除异动</p>

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
						<input type="hidden" name="ship_id" id ="ship_id" value="${KdShipAbnormal.ship_id}"/>
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

			<li style="float: none; " >
				<p>
					异动结算网点：<input type="text"  name="networkid"  id="networkid" value="${KdShipAbnormal.abnormalNetWorkName}" >

				</p>
			</li>

			<li style="float: none">
				<p>
					异动原因：<input style="width: 500px" type="text"  name="cause"  id="cause" value="${KdShipAbnormal.cause}">
				</p>
			</li>
			</ul>


		</div><br><br>
		<button class="banner-list5-div1-button" onclick="deleteAbnormal();">确认删除</button>

		<button onclick="parent.layer.closeAll();">取消</button>
		<br />
	</div>
	</div>
	<script type="text/javascript">

	
	function deleteAbnormal(){
        var ship_id=$("#ship_id").val();
        var id=$("#id").val();

		$.ajax({
			type:"post",
			dataType:"json",
			url:"${ctx}/kd/finance/abnormal/deleteAbnormal",
			data:{ship_id:ship_id,id:id},
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