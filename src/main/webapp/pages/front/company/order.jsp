<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>零担下单发货</title>
		<link rel="stylesheet" href="${ctx}/static/pc/logistics/css/Vehicle.css?v=${version}" />
		
		
		<script src="${ctx }/static/pc/study/js/jquery.js"></script>
		<script src="${ctx}/static/pc/stats/js/YuxiSlider.jQuery.min.js"></script>
		<script src="${ctx}/static/pc/js/city.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>

		<script type="text/javascript">
		function distance(){
			var sprov = $("#city_2").find("[name='province'] option:selected").text();
			var scity = $("#city_2").find("[name='scity'] option:selected").text();
			var sdict = $("#city_2").find("[name='hairarea'] option:selected").text();
			var saddress = $("#city_2").find("input").val();
			var eprov = $("#city_3").find("[name='province'] option:selected").text();
			var ecity = $("#city_3").find("[name='ecity'] option:selected").text();
			var edict = $("#city_3").find("[name='closedarea'] option:selected").text();
			var eaddress = $("#city_3").find("input").val();
/* 			if(sdict==null||sdict==' - 请选择 - '||edict==null||edict==' - 请选择 - '){
				return;
			} */
			$.ajax({
				type : "post",
				url : "${ctx}/map/getDistance",
				data : {id:"${id}", from:sprov+scity+sdict+saddress+"|"+scity,to:eprov+ecity+edict+eaddress+"|"+ecity},
				success : function(data) {
					if(data.status==200){
						$(".btnss").text(data.content.distance_km+" 公里");
					}
				}
			});
		}
		</script>
	</head>

	<body>
		<%@ include file="common/header.jsp" %>
		<div class="benner">
		<form id="formid" action="" method="post">
			<input type="hidden" id="orderid" name="orderid" value="${line.id}"> 
			<div class="benner-translate">
				<h1>承运方：<span class="benner-translate-span">${company.corpname}专线直达</span></h1>
				<div class="benner-translate-hgn">
					<b>选择类型：</b>
					<ul>
					    <li  class="active"><a class="ld_xz" href="${ctx}/company/order?id=${id}&lineId=${line.id}">零担</a></li>
					    <li><a  class="zc_xz" href="${ctx}/company/vehicle?id=${id}&lineId=${line.id}">整车</a></li>
					    <li><a href="${ctx}/front/goods/oftenlist" class="benner-translate-hgn-a">+ 我的常发货物</a></li>
					</ul>
				</div>
				
				<div class="benner-translate-map">
                    <p class="zsxd_title1">在线信息<span>* 必填</span></p>
					<div id="city_2">
						&nbsp;&nbsp;&nbsp;发货地 :
					    <select class="prov" name="province" <c:if test="${line !=null}">disabled</c:if>></select>
						<select class="city" name="scity" <c:if test="${line !=null}">disabled</c:if>></select>
						<select class="dist" name="hairarea" id="sareaId"  <c:if test="${line !=null}">disabled</c:if> onchange="loadLines()"></select><span>*</span>
						<b>街道/门牌号：<input id="closedfulladdress" name="closedfulladdress"  type="text" onblur="distance()"/></b>
						 <script type="text/javascript">
					       		$("#city_2").region({areaField:"hairarea",cityField:"scity",domain:"${ctx}",currAreaCode:"${empty line.from_region_code?line.from_city_code:line.from_region_code}",callback:function(){distance();}});
					       </script>
					</div>

					<div id="city_3">
						&nbsp;&nbsp;&nbsp;收货地 :
						
						<select class="prov" name="province" <c:if test="${line !=null}">disabled</c:if>></select>
						<select class="city" name="ecity" <c:if test="${line !=null}">disabled</c:if>></select>
						<select class="dist" name="closedarea" id="eareaId"  <c:if test="${line !=null}">disabled</c:if> onchange="loadLines()"></select><span>*</span>
						<b>街道/门牌号：<input  id="hairfulladdress" name="hairfulladdress" type="text" onblur="distance()"/></b>
						<script type="text/javascript">
					       		$("#city_3").region({areaField:"closedarea",cityField:"ecity",domain:"${ctx}",currAreaCode:"${empty line.to_region_code?line.to_city_code:line.to_region_code}",callback:function(){distance();}});
					     </script>
					</div>
					<p class="btns">路程合计约 : <a class="btnss">0公里</a></p>
				</div>
			</div>

			<div class="benner-translates">
				<h2>物流公司报价信息</h2>
				<table cellspacing="0" class="benner-tables">
					<tr>
						<th>承运方</th>
						<th>起步价</th>
						<th>重货价/公斤公斤(元)</th>
						<th>轻货价/立方</th>
						<th>时效性</th>
						<th>发车频次</th>
						<th>联系电话</th>
					</tr>
					<c:if test="${line !=null}">
						<tr>
							<td>${line.corpname}</td>
							<td class="huang">${line.starting_price}元</td>
							<td class="huang">${line.price_heavy}元/公斤</td>
							<td class="huang">${line.price_small}元/立方</td>
							<td>${line.survive_time}小时</td>
							<td>${line.frequency}次/天</td>
							<td>${line.phone}</td>
						</tr>
					</c:if>
				</table>
				
				<div id="editInfo" style="z-index:99; float:left; width:60px; height: 110px; position: fixed;top:300px; right:50%; margin-right: -680px;">
				        <ul >
				           <li><a href="${ctx}/"></a></li><br><br><br>
				           <li><a class="content-right-okl" href="#"></a></li>
				        </ul>
			        </div>
			</div>

			<div class="benner-translatet">
				<p class="zsxd_title3">货物信息<span class="benner-translatet-span">* 必填</span></p>
		
				<table class="benner-translatet-table">
					<tr>
						<th>货物类别</th>
						<th><span class="benner-translatet-spans">*</span>&nbsp;&nbsp;货物名称</th>
						<th><span class="benner-translatet-spans"></span>总件数</th>
						<th><span class="benner-translatet-spans">*</span>&nbsp;&nbsp;总重量</th>
						<th><span class="benner-translatet-spans">*</span>&nbsp;&nbsp;总体积</th>
					</tr>
					<tr>
						<td>
							<select name="Ordergoods[0].charge_by" data="gz" onchange="computePrice()">
								<option value="1">规则货物收费(按重量体积收费)</option>
								<option value="2">不规则货物收费(按件收费)</option>
							</select>
						</td>
						<td>
							<select name="Ordergoods[0].good_name">
								<option value="1">设备</option>
								<option value="2">机械</option>
								<option value="3">煤炭</option>
								<option value="4">矿产</option>
								<option value="5">钢材</option>
								<option value="6">饲料</option>
								<option value="7">建材</option>
								<option value="8">木材</option>
								<option value="9">粮食</option>
								<option value="10">饮料</option>
								<option value="11">蔬菜</option>
								<option value="12">水果</option>
								<option value="13">畜产品</option>
								<option value="14">农资</option>
								<option value="15">服装鞋包</option>
								<option value="16">日用百货</option>
								<option value="17">药品</option>
								<option value="18">化工产品</option>
								<option value="19">电子电器</option>
								<option value="20">仪表仪器</option>
								<option value="21">汽车摩托</option>
								<option value="22">汽配摩配</option>
								<option value="23">图书音像</option>
								<option value="24">纸类包装</option>
								<option value="25">工艺礼品</option>
								<option value="26">文体用品</option>
								<option value="27">危险品</option>
							</select>
						</td>
						<td>
							<input name="Ordergoods[0].amount" type="text" datatype="sl"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 件
						</td>
						<td>
							<input type="text" name="Ordergoods[0].weight"  datatype="zl" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/><select name="Ordergoods[0].weight_unit"  onchange="computePrice()">
								<option value="k">公斤</option>
								<option value="t">吨</option>
							</select>
						</td>
						<td>
							<input name="Ordergoods[0].volume" type="text"  datatype="tj" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 立方
						</td>
						<td width="90"> <input style="display: none;" class="del" type="button" onclick="deltable(this);" value="删除"></td>
					</tr>
				</table>
				<div class="benner-translatet-button">
					
					
					<a href="javascript:void(0);" onclick="addRow()">+ 继续添加</a>
					
					<input type="button" onclick="resetRows()" value="重置" class="benner-translatet-buttons">
				</div>
				<b>重量和体积用于估量运费，以实际重量/体积为标准</b>
				<div class="btnulo">
				   <ul id="totalInfo">
						<li>总重量：<span id="tzl">0</span>公斤</li>
						<li>总体积：<span id="ttj">0</span>方</li>
						<li>总件数: <span id="tsl">0</span>件</li>
						<li>专线运费预计: <span id="jiage">面议</span>元</li>
					</ul>
				</div>
			</div>
 
            <div class="benner-translateg">
            	<p class="zsxd_title5">联系方式<span>* 必填</span></p>
            	<table cellspacing="0" class="benner-translateg-table">
            		<tr>
            			<th></th>
            			<th></th>
            		</tr>
            		<tr>
            			<td>发货人：<input  id="sender" name="model.sender" type="text"/><span>* </span></td>
            			<td>发货人手机：<input id="sender_mobile" name="model.sender_mobile"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text"/><span>* </span></td>
            		</tr>
            		<tr>
            			<td>收货人：<input  id="receiver" name="model.receiver" type="text" class="btn"/><span style="color:white;">* </span></td>
            			<td>收货人手机：<input id="receiver_mobile" name="model.receiver_mobile"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" class="btn"/><span>* </span></td>
            		</tr>
            	</table>
               
            </div>
             <div class="benner-buttont">
	            	<input class="checkbox" type="checkbox" name="model.send_status" value="1">&nbsp;&nbsp;保存为常发货物
	            	<c:if test="${isSame==false && not empty line}">
	            		<input type="button" onclick="checkOrder()" value="发货" class="benner-buttont-button">
	            	</c:if>
            	</div> 	
			</form>
		</div>
		<br><br>
		<%@ include file="common/foot.jsp" %>
		
		<script type="text/javascript">
		var reg = /^[0-9]*$/;
		var startPrice= 0;
		var heavyPrice= 0;
		var smallPrice= 0;
		function addRow() {
			
			if($("#orderid").val()==0){
				layer.msg("没有专线信息，请选择出发地收货地");
				return;
			}
			
			if(checkInputs()){
				layer.msg("请输入总体积/总重量!");
				return
			}
			var index = $(".benner-translatet-table").find("tr").length-1;
			var text = "<tr>"
					+ "<td><select name=\"Ordergoods["+index+"].charge_by\" data=\"gz\"  onchange=\"computePrice()\">"
					+ "<option value='1'>规则货物收费(按重量体积收费)</option>"
					+ "<option value='2'>不规则货物收费(按记收费)</option>"
					+ "</select>"
					+ "</td>"
		
					+ "<td><select name=\"Ordergoods["+index+"].good_name\" >"
					+'<option value="1">设备</option>'
					+'<option value="2">机械</option>'
					+'<option value="3">煤炭</option>'
					+'<option value="4">矿产</option>'
					+'<option value="5">钢材</option>'
					+'<option value="6">饲料</option>'
					+'<option value="7">建材</option>'
					+'<option value="8">木材</option>'
					+'<option value="9">粮食</option>'
					+'<option value="10">饮料</option>'
					+'<option value="11">蔬菜</option>'
					+'<option value="12">水果</option>'
					+'<option value="13">畜产品</option>'
					+'<option value="14">农资</option>'
					+'<option value="15">服装鞋包</option>'
					+'<option value="16">日用百货</option>'
					+'<option value="17">药品</option>'
					+'<option value="18">化工产品</option>'
					+'<option value="19">电子电器</option>'
					+'<option value="20">仪表仪器</option>'
					+'<option value="21">汽车摩托</option>'
					+'<option value="22">汽配摩配</option>'
					+'<option value="23">图书音像</option>'
					+'<option value="24">纸类包装</option>'
					+'<option value="25">工艺礼品</option>'
					+'<option value="26">文体用品</option>'
					+'<option value="27">危险品</option>'
					+ "</select>"
					+ "</td>"
		
					+ "<td><input type='text' name=\"Ordergoods["+index+"].amount\" datatype=\"sl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>件</td>"
		
					+ "<td>"
					+ "<input name=\"Ordergoods["+index+"].weight\" type='text' datatype=\"zl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>"
					+ "<select name=\"Ordergoods["+index+"].weight_unit\" onchange=\"computePrice()\">"
					+ "<option value='k'>公斤</option>"
					+ "<option value='t'>吨</option>"
					+ "</select>"
					+ "</td>"
		
					+ "<td><input name=\"Ordergoods["+index+"].volume\" type='text' datatype=\"tj\"  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>立方</td>"
		
					+ "<td><input class='del' type='button' onclick='deltable(this);' value='删除'></td>"
		
					+ "</tr>";
			$(".benner-translatet-table").append(text);
			$(".benner-translatet-table input[type='text']").bind("change", changeEvent);
		}
		
		function deltable(delbtn) {
			if ($("tr").length < 2) {
				layer.msg("至少保留一行!");
			} else {
				layer.confirm('您确定要删除？', {
				  	btn: ['删除','取消']
				}, function(index){
					$(delbtn).parents("tr").remove();
					refresh();
					computePrice();
					layer.close(index);
				}, function(){})
			}
		}
		
		function resetRows() {
			$.each($(".benner-translatet-table").find("tr"), function(i, n){
				if(i>1){
					$(n).remove();
				}
			});
			$(".benner-translatet-table input").val("");
			$("#totalInfo span").text("0");
		}
		
		function checkOrder() {
			var hairfulladdress = document.getElementById("hairfulladdress").value;
			var closedfulladdress = document.getElementById("closedfulladdress").value;
			var sender = document.getElementById("sender").value;
			var sender_mobile = document.getElementById("sender_mobile").value;
			//var receiver = document.getElementById("receiver").value;
			var receiver_mobile = document.getElementById("receiver_mobile").value;
			var s = $("#sareaId").val();
			var e = $("#eareaId").val();
			if(s==null||e==null){
				layer.msg("请选择发货地和收货地");
				return;
			}
			
			if($("#orderid").val()==0){
				layer.msg("没有专线信息，请选择出发地收货地");
				return;
			}
			
			if (hairfulladdress == "") {
				layer.msg("发货街道门牌号不能为空");
				return;
			}
			if (closedfulladdress == "") {
				layer.msg("收货街道门牌号不能为空");
				return;
			}
			
			if (sender == "") {
				layer.msg("发货人不能为空");
				return;
			}
			if (sender_mobile == "") {
				layer.msg("发货人手机号码不能为空");
				return false;
			}
			if(!(/^1[345678]\d{9}$/.test(sender_mobile))){
			   layer.msg("请输入正确的发货人手机号码");
			   return false;
		    }
			if (receiver_mobile == "") {
				layer.msg("收货人手机号不能为空");
				return false;
			}
			
			if(!(/^1[345678]\d{9}$/.test(receiver_mobile))){
			   layer.msg("请输入正确的收货人手机号码");
			   return false;
		    }
			
			if(checkInputs()){
				layer.msg("请输入总件数/总体积/总重量!");
				return
			}
			
			$("select").removeAttr("disabled");
			var isyc = true;
			if(isyc){
				isyc = false;
				$.ajax({
					type : "post",
					dataType : "html",
					url : "${ctx}/front/goods/ordersave",
					data : $('#formid').serialize(),
					success : function(data) {
						isyc = true;
						if (data.success) {
							layer.msg("下单成功！",{time: 2000},function(){
								location.reload();
							});
						} else {
							layer.msg(data.msg);
						}
					}
				});
			}
		}
		
		function loadLines(){
			var s = $("#sareaId").val();
			var e = $("#eareaId").val();
			if(s==null||e==null){
				return false;
			}
			$.ajax({
				type : "post",
				url : "${ctx}/company/loadLines",
				data : {id:"${id}", fromRegionCode:s,toRegionCode:e},
				success : function(data) {
					if(data.length==0){
						startPrice = 0;
						smallPrice = 0;
						heavyPrice = 0;
						layer.msg("您选择地点的没有运输专线，请选择其他地点");
					}
					appendHtml(data);
					computePrice();
				}
			});
			distance();
		}
		
		function appendHtml(data){
			var html = "<tr><th>承运方</th><th>起步价</th><th>重货价/公斤</th><th>轻货价/立方</th><th>时效性</th><th>发车频率</th><th>联系电话</th></tr>";
			var id = 0;
			$.each(data, function(index, obj) {
				if(index==0){
					var small = obj.price_small;
					var heavy = obj.price_heavy;
				    html+="<tr><td>"+obj.corpname+"</td><td>"+obj.starting_price+"元</td><td>"+small.toFixed(2)+"元/公斤</td><td>"+heavy.toFixed(2)+"元/立方</td><td>"+obj.survive_time+"小时</td><td>"+obj.frequency+"次/天</td><td>"+obj.phone+"</td></tr>";
				    id = obj.id;
				    startPrice = obj.starting_price;
					smallPrice = obj.price_small;
					heavyPrice = obj.price_heavy;
				}
			});
			$(".benner-tables tbody").empty();
			 $("#orderid").val(id);
			$(".benner-tables tbody").append(html);
		}
		
		function refresh(){
			var names = ["sl","zl","tj"];
			for(var i=0; i<names.length;i++){
				compute(names[i]);
			}
		}
		
		function compute(name){
			 var total = 0;
			 $.each($("input[datatype='"+name+"']"), function(i, n){
				 if(reg.test($(n).val())&&$(n).val()!=null&&$(n).val()!=""){
					 var type = $(this).next("select").val();
					 var zl = $(n).val();
					 if(type=='t')zl=1000*zl;
					 total += parseInt(zl)
				 }
			 });
			 $("#t"+name).text(total);
		}
		
		function checkInputs(){
			var names = ["zl","tj"];
			var flag = false;
			for(var i=0; i<names.length;i++){
				flag = checkInput(names[i]);
				if(flag)break;
			}
			return flag;
		}
		
		function checkInput(name){
			 var flag = false;
			 $.each($("input[datatype='"+name+"']"), function(i, n){
				 if($(n).val()==null||$(n).val()==""){
					 flag = true;
					 return false;
				 }
			 });
			 return flag;
		}
		
		
		function computePrice(){
			 var res = false;
			 $(".benner-translatet-table").find("select[data='gz']").each(function(i){
				  if($(this).val()==2) {
					  res = true;
					  return false;
				  }
			 });
			 if(res){
				 $("#jiage").text("面议");
				 return
			 }
			 
			 if($("#orderid").val()==0){
				return;
			 }
			 
			 var total = 0;
			 $("input[datatype='zl']").each(function(i){
				 var type = $(this).next("select").val();
				 var zl = $(this).val();
				 if(type=='t')zl=1000*zl;
				 total += parseInt(zl)
			 });
			 $("#tzl").text(total);
			 total = 0;
			 
			 $(".benner-translatet-table").find("tr").each(function(){
					 var zl = $(this).find("input[datatype='zl']").val();
					 var tj = $(this).find("input[datatype='tj']").val();
					 if(zl!=""&&zl!=null&&tj!=""&&tj!=null){
						 var type = $(this).find("select:eq(2)").val();
						 if(type=='t')zl=1000*zl;
						 var res = parseFloat(tj/zl);
						 var fi = res.toFixed(3);
						 var price = 0;
						 if(fi>0.006){
							 price = parseFloat(smallPrice)*parseFloat(tj)
						 }else{
							 price = parseFloat(heavyPrice)*parseFloat(zl)
						 }
						 total += price;
					 }
			 });
			 if(total==0)return;
			 if(total<parseFloat(startPrice))total=parseFloat(startPrice);
			 $("#jiage").text(total);
		}
		
		
		function changeEvent(){
			 this.value=this.value.replace(/\D/g,'')
			 var name = $(this).attr("datatype");	
			 compute(name);
			 if(name!='sl')computePrice();
		}
		
		$(function(){
			$(".benner-translatet-table input[type='text']").on("change", changeEvent);
			if("${line.starting_price}" != null){
				startPrice = parseFloat('${line.starting_price}');
				smallPrice = parseFloat('${line.price_small}');
				heavyPrice = parseFloat('${line.price_heavy}');
			}
			if("${isSame}"=="true"){
				layer.msg("自己家的专线，不能给自己发货哦！");
			}
			if("${line}"==""){
				layer.msg("您要发货的专线已经下线了，请选择其他专线！");
			}
		});
		
		</script>
</body>
</html>