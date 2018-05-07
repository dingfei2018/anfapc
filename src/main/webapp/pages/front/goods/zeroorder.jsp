<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>零担下单发货</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/online3.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx }/static/pc/js/region_select.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
		<script src="${ctx }/static/pc/js/citys.js?v=${version}"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
		<script src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
	</head>	
<script type="text/javascript">
	$(function() {
		$(document).on("change", ".benner-translatet-table input[type='text']", changeEvent);
		if("${Linesdata.starting_price}" != null){
			startPrice = parseFloat('${Linesdata.starting_price}');
			smallPrice = parseFloat('${Linesdata.price_small}');
			heavyPrice = parseFloat('${Linesdata.price_heavy}');
		}
	  	var provinceoptions=$("#province option:selected"); 
		var  province=provinceoptions.val();
		var cityoptions=$("#city option:selected"); 
		var  city=cityoptions.val();
		var hairareaoptions=$("#hairarea option:selected"); 
		var  hairarea=hairareaoptions.text();
		var fo =province+city+hairarea+"|"+city;
	  	var toprovinceoptions=$("#toprovince option:selected"); 
		var  toprovince=toprovinceoptions.val();
		var tocityoptions=$("#tocity option:selected"); 
		var  tocity=tocityoptions.val();
		var closedareaoptions=$("#closedarea option:selected"); 
		var  closedarea=closedareaoptions.text();
		var tor =toprovince+tocity+closedarea+"|"+tocity;
		if(fo!="|"&&tor!="|"){
			$.ajax({
				type : "post",
				url : "${ctx}/map/getDistance",
				data : {from:fo,to:tor},
				  success:function(data){
					  if(data.status==200){
						  $("#ids").html( "总里程数为 :<b>"+ data.content.distance_km+"公里</b>" );
					  }else{
						  layer.msg(data.msg);
					  }
				  }
				});
		}
		if("${isSame}"=="true"){
			layer.msg("自己家的专线，不能给自己发货哦！");
		}
		if("${Linesdata}"==""){
			layer.msg("您要发货的专线已经下线了，请选择其他专线！");
		}
	});
	
</script>
<body>
	<%@ include file="../common/loginhead.jsp" %>
		<div class="benner">
		<form id="formid" action="" method="post">
		<input type="hidden" id="orderid" name="orderid" value="${Linesdata.id}"> 
		<input type="hidden" name="model.is_truckload" value="0"> 
			<div class="benner-translate">
				<h1 style="font-size: 16px;"><b style="font-weight: normal; font-size: 16px;">承运方：</b>${Linesdata.corpname}直达</h1>
				<ul>
					<li>选择类型：</li>
					<li class="active" style="margin-left: 7px;"><input type="button" onClick="zero()" value="零担" class="ld"/></li> 
					<li class=""><input type="button" onclick="whole()" value="整车"  class="zc"/></li>
					<li><a href="${ctx }/front/goods/oftenlist">+ 我的常发货物</a></li>
				</ul><br />
				
				
				<div class="benner-translate-map">
                    <p><b class="benner-opsan">线路信息</b><span>* 必填</span>
					<%-- <a href="${ctx }/front/goods/oftenlist">+ 我的常发货物</a> --%>
					
				</p>
					<div id="location" class="citys">
						&nbsp;&nbsp;&nbsp;发货地
						
							 <select id="province" name="province">
							 <option value="${fromaddr.province}">${fromaddr.province}</option>
							 </select>
		                     <select id="city" name="scity">
		                     <option value="${fromaddr.city}">${fromaddr.city}</option>
		                     
		                     </select>
		                     <select id="hairarea" name="hairarea">
		                     <option value="${fromaddr.region_code}" >${fromaddr.region_name}</option>
		                     </select><span>*</span>
		          <!--             <script type="text/javascript">
				                if(remote_ip_info){
				                    $('#location').citys({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
				                }
				            </script> -->
						<b>街道/门牌号：<input id="hairfulladdress" name="hairfulladdress" type="text" placeholder="请填写详细信息,以便为你上门接货"/></b>
					</div>

					<div id="locations" class="citys">
						&nbsp;&nbsp;&nbsp;收货地
							<select id="toprovince" name="province">
							
							    <option value="${toaddr.province}">${toaddr.province}</option>
							</select>
		                     <select id="tocity" name="ecity">
		                         <option value="${toaddr.city}">${toaddr.city}</option>
		                     </select>
		                     <select id="closedarea" name="closedarea">
		                         <option value="${toaddr.region_code}" >${toaddr.region_name}</option>
		                     </select><span>*</span>
		                     
		                   <!--    <script type="text/javascript">
				                if(remote_ip_info){
				                    $('#locations').citysclosed({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
				                }
				            </script> -->
						<b>街道/门牌号：<input id="closedfulladdress" name="closedfulladdress" type="text" placeholder="请填写详细信息,以便为你上门接货"/></b>
					</div>

					<p id="ids"></p>
				</div>
			</div>
			
            <div class="benner-gunp">
                 <p>物流公司报价信息</p>
                 <table>
                     <tr>
                        <th>承运方</th>
                        <th>起步价</th>
                        <th>重货价/公斤</th>
                        <th>轻货价/立方</th>
                        <th>时效性</th>
                        <th>发车频次</th>
                        <th>联系电话</th>
                     </tr>
                      <c:if test="${Linesdata !=null}">
                     <tr>
							<td>${Linesdata.corpname}</td>
							<td style="color: #ff7800;font-weight: bold;">${Linesdata.starting_price}元</td>
							<td style="color: #ff7800;font-weight: bold;">${Linesdata.price_heavy}元/公斤</td>
							<td style="color: #ff7800;font-weight: bold;">${Linesdata.price_small}元/立方</td>
							<td>${Linesdata.survive_time}小时</td>
							<td>${Linesdata.frequency}次/天</td>
							<td>${Linesdata.phone}</td>
                     </tr>
                     </c:if>
                 </table>
            </div>

			<div class="benner-translatet">
				<p><b class="benner-opsan">货物信息</b><span class="benner-translatet-span">* 必填</span></p>
		
				<table id="questionTable" class="benner-translatet-table">
					<tr>
						<th>货物类别</th>
						<th><span>*</span>&nbsp;&nbsp;货物名称</th>
						<th>总件数</th>
						<th><span>*</span>&nbsp;&nbsp;总重量</th>
						<th><span>*</span>&nbsp;&nbsp;总体积</th>
					</tr>
					<tr>
						<td>
							<select name="Ordergoods[0].charge_by"  data="gz" onchange="computePrice()">
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
							<input type="text" name="Ordergoods[0].weight"  datatype="zl" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							<select name="Ordergoods[0].weight_unit" onchange="computePrice()">
								<option value="k">公斤</option>
								<option value="t">吨</option>
							</select>
						</td>
						<td>
							<input name="Ordergoods[0].volume" type="text" datatype="tj" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 立方
						</td>
						<td width="90"> <input style="display: none;" class="del" type="button" onclick="deltable(this);" value="删除"></td>
					</tr>
				</table>
				<div class="benner-translatet-button">
				 <a href="javascript:void(0);" onclick="addRows()">+继续添加</a>
					<input id="btn" type="button" onclick="resetRows()" value="重置" >
					
					
				</div>
				
				<p class="ts">重量和体积用于估量运费，以实际重量/体积为标准</p>
				<div id="totalInfos">
				   <ul id="totalInfo">
						<li>总重量：<span id="tzl">0</span>公斤</li>
						<li>总体积：<span id="ttj">0</span>方</li>
						<li>总件数: <span id="tsl">0</span>件</li>
						<li>专线运费预计: <span id="jiage">面议</span>元</li>
					</ul>
				</div>
			</div>
            <div class="benner-translateg">
            	<p><b class="benner-opsans">联系方式</b><span>* 必填</span></p>
            	<table cellspacing="0" class="benner-translateg-table">
            		<tr>
            			<th></th>
            			<th></th>
            		</tr>
            		<tr>
            			<td>发货人：<input id="sender" name="model.sender" type="text"/> <span>* </span></td>
            			<td>发货人手机：<input id="sender_mobile" name="model.sender_mobile" type="text"/> <span>* </span></td>
            	
            		</tr>
            		<tr>
            			<td>收货人：<input id="receiver" name="model.receiver" type="text" class="btn"/> <span>&nbsp;&nbsp;</span></td>
            			<td>收货人手机：<input id="receiver_mobile" name="model.receiver_mobile"  type="text" class="btn"/> <span>* </span></td>
            		</tr>
			    </table>
            	
            </div>
            <div class="benner-buttont">
	            	
	            	
	            	<input id="btnu" type="checkbox" name="model.send_status" value="1">&nbsp;&nbsp;&nbsp;保存为常发货物
	            	<c:if test="${isSame==false && not empty Linesdata}">
	            		<input id="btns" type="button" onclick = "checkOrder();" value="发货" />
	            	</c:if>
            	</div>
            </form>
		</div>
	
<%@ include file="../common/loginfoot.jsp" %>
<script type="text/javascript">
var startPrice= 0;
var heavyPrice= 0;
var smallPrice= 0;
function checkOrder() {
	var hairfulladdress = document.getElementById("hairfulladdress").value;
	var closedfulladdress = document.getElementById("closedfulladdress").value;
	var sender = document.getElementById("sender").value;
	var sender_mobile = document.getElementById("sender_mobile").value;
	//var receiver = document.getElementById("receiver").value;
	var receiver_mobile = document.getElementById("receiver_mobile").value;
	if (hairfulladdress == "") {
		layer.msg("发货街道门牌号不能为空");
		return false;
	}
	if (closedfulladdress == "") {
		layer.msg("收货街道门牌号不能为空");
		return false;
	}
	if (sender == "") {
		layer.msg("发货人不能为空");
		return false;
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
		layer.msg("请输入总体积/总重量!");
		return
	}
	var isyc = true;
	if(isyc){
		isyc = false;
		Anfa.ajax({
			type : "post",
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
function zero() {
	window.location.href="${ctx }/front/goods?fromregioncode=${Linesdata.from_region_code}&toregioncode=${Linesdata.to_region_code}&fromcitycode=${Linesdata.to_city_code}&fromcitycode=${Linesdata.to_city_code}&orderid=${orderid}";
}

function whole() {
	window.location.href="${ctx }/front/goods/vehiclelist?fromregioncode=${Linesdata.from_region_code}&toregioncode=${Linesdata.to_region_code}&fromcitycode=${Linesdata.to_city_code}&fromcitycode=${Linesdata.to_city_code}&orderid=${orderid}";
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


var reg = /^[0-9]*$/;
function addRows() {
	if(checkInputs()){
		layer.msg("请输入总体积/总重量!");
		return
	}
	var index = $("#questionTable").find("tr").length-1;
	var text = "<tr>"
			+ "<td><select name=\"Ordergoods["+index+"].charge_by\"  data=\"gz\"   onchange=\"computePrice()\">"
			+ "<option value='1'>规则货物收费(按重量体积收费)</option>"
			+ "<option value='2'>不规则货物收费(按件收费)</option>"
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

			+ "<td><input type='text' name=\"Ordergoods["+index+"].amount\" datatype=\"sl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>&nbsp;件</td>"

			+ "<td>"
			+ "<input name=\"Ordergoods["+index+"].weight\" type='text' datatype=\"zl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>"
			+ " <select name=\"Ordergoods["+index+"].weight_unit\"  onchange=\"computePrice()\">"
			+ "<option value='k'>公斤</option>"
			+ "<option value='t'>吨</option>"
			+ "</select>"
			+ "</td>"

			+ "<td><input name=\"Ordergoods["+index+"].volume\" type='text' datatype=\"tj\"  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>&nbsp;立方</td>"

			+ "<td><input type='button' class='del' onclick='deltable(this);' value='删除'></td>"

			+ "</tr>";
	$("#questionTable").append(text);
	index += 1;
}

function refresh(){
	var names = ["sl","zl","tj"];
	for(var i=0; i<names.length;i++){
		compute(names[i]);
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

function changeEvent(){
	 this.value=this.value.replace(/\D/g,'')
	 var name = $(this).attr("datatype");	
	 compute(name);
	 if(name!='sl')computePrice();
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
</script>
	</body>
</html>
