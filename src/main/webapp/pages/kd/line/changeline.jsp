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
	<div id="fade3" class="black_overlay3"></div>
	<div id="MyDiv3" class="white_content3">
		<p class="banner-list5-type">修改专线</p>

		<div class="banner-list5-div1">
			<p class="banner-div-title">专线信息</p>
			<ul>
				<li>
					<p>
						<input type="hidden" name="id" id ="id" value="${sysLine.id}"/>
						<input type="hidden" name="network_id" id ="network_id" value="${sysLine.network_id}"/>
						<input type="hidden" name="from_city_code" id ="from_city_code" value="${sysLine.from_city_code}"/>
						<input type="hidden" name="to_city_code" id ="to_city_code" value="${sysLine.to_city_code}"/>
						出发地：<input type="text" disabled="disabled" id=""
							value="${sysLine.fromAdd}" /></span>
					</p>
				</li>
				<li>
					<p>
						到达地：<input type="text" disabled="disabled"
							value="${sysLine.toAdd}" /></span>
					</p>
				</li>
				<li style="clear: both;">
					<p>
						出发网点：<select name="networkId" id="networkId"  onblur="checkNetWorkName();">
							<option value="">请选择网点</option>
							<c:forEach items="${networks}" var="net">
								<option value="${net.id}"
									${sysLine.network_id==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
							</c:forEach>
						</select>
					</p>
				</li>
				<li>
					<p>
						到达网点：<select name="arriveNetworkId" id="arriveNetWorkId">
							<option value="" >请选择网点</option>
							<c:forEach items="${arriveNetworks}" var="net">
								<option value="${net.id}"
									${sysLine.arrive_network_id==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
							</c:forEach>
						</select>
					</p>
				</li>
				<li style="clear: both;">
					<p>
						重货价格：<input type="text" name="priceHeavy" id="priceHeavy" value="${sysLine.price_heavy}">
					</p>
				</li>
				<li>
					<p>
						轻货价格：<input type="text" name="priceSmall" id="priceSmall"  value="${sysLine.price_small}">
					</p>
				</li>
				<li>
					<p>
						最低收费：<input type="text" name="startingPrice"  id="startingPrice" value="${sysLine.starting_price}">
					</p>
				</li>
			</ul>
		</div><div></div><br><br><br>
		<button class="banner-list5-div1-button" onclick="saveLine()">提交</button>
		<br />
	</div>
	</div>
	<script type="text/javascript">
	
	 function checkNetWorkName(){
			var flag=false;
			var networkid = $('#network_id').val();
			var updateFlag=	networkid!= $('#networkId').val();
			if(updateFlag){
			$.ajax({
				  url : "${ctx}/kd/line/checkLine?networkId="+$('#networkId').val()+"&from_city_code="+$('#from_city_code').val()+"&to_city_code="+$('#to_city_code').val(),
				  type : 'POST',
				  dataType:'json',
				  async: false,
				  success:function(data){
					  if(data){
						  flag= false;
					  }else{
						  Anfa.show("该网点下此专线已存在","#networkId"); 
						  flag= true;
					  }
				  }
				});
			}
			return flag;
		}
	
	function saveLine(){
		var networkid=$("#networkId").val();
		var arriveNetWorkId=$("#arriveNetWorkId").val();
		var id=$("#id").val();
		var price_heavy= $("#priceHeavy").val();
		var price_small= $("#priceSmall").val();
		var startingprice= $("#startingPrice").val();
		var numTest= /(^[0-9]\d*\.\d*$)|(^\d+$)/;
		if(networkid==""){
			Anfa.show("请选择专线网点","#networkId");
			return ;	
		}
		if(checkNetWorkName()){
			 Anfa.show("该网点下此专线已存在","#networkId"); 
 			 return;
 		}
		if(!numTest.test(price_heavy)){
			 Anfa.show("请输入正确的价格","#priceHeavy"); 
 			 return;
 		}
		if(!numTest.test(price_small)){
			 Anfa.show("请输入正确的价格","#priceSmall"); 
 			 return;
 		}
		if(!numTest.test(startingprice)){
			 Anfa.show("请输入正确的价格","#startingPrice"); 
 			 return;
 		}
		$.ajax({
			type:"post",
			dataType:"json",
			url:"${ctx}/kd/line/updateLine", 
			data:{id:id,networkId:networkid,arriveNetWorkId:arriveNetWorkId,price_heavy:price_heavy,price_small:price_small,startingprice:startingprice},
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