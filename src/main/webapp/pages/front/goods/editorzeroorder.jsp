<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>零担再次发货</title>
		<link rel="stylesheet" href="${ctx }/static/pc/css/head2.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/online3.css?v=${version}"/>
		<link rel="stylesheet" href="${ctx }/static/pc/css/footer.css?v=${version}"/>
		<script src="${ctx }/static/pc/js/jquery.js"></script>
		<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
		<script src="${ctx }/static/pc/js/region_select.js"></script>
		<script src="${ctx }/static/pc/js/unslider.min.js"></script>
		<script src="${ctx }/static/pc/js/citys.js?v=${version}"></script>
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
	
	refresh();
	computePrice();
		
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
					  $("#ids").html( "总里程数为 :<span color:red;>"+ data.content.distance_km+"公里</span>" );
				  }
			  }
			});
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
		<input type="hidden" id="orderid" name="model.is_truckload" value="0"> 
			<div class="benner-translate">
				<h1 style="font-size: 16px;"><b style="font-weight: normal; font-size: 16px;">承运方：</b>${Linesdata.corpname}直达</h1>
				<ul> 
					<li>类型：</li>
					<c:if test="${order.is_truckload==0 }">
					<li><input id="active" type="button" onClick="zero()" value="零担" class="ld"/></li> 
					</c:if>
					<c:if test="${order.is_truckload==1 }">
					<li><input id="active" type="button" onclick="whole()" value="整车" class="zc"/></li>
					</c:if>
					<li><a class="a" href="${ctx }/front/goods/oftenlist">+ 选择我的常发货物</a></li>
				</ul><br />
				
				
				<div class="benner-translate-map">
                    <p><b class="benner-opsan">线路信息</b><span>* 必填</span>
				</p>
					<div id="location" class="citys">
						<span>*</span>&nbsp;&nbsp;&nbsp;发货地
						
							 <select id="province" name="province">
							 <option value="${fromaddr.province}">${fromaddr.province}</option>
							 </select>
		                     <select id="city" name="scity">
		                     <option value="${fromaddr.city}">${fromaddr.city}</option>
		                     
		                     </select>
		                     <select id="hairarea" name="hairarea">
		                     <option value="${fromaddr.region_code}" >${fromaddr.region_name}</option>
		                     </select>
		          <!--             <script type="text/javascript">
				                if(remote_ip_info){
				                    $('#location').citys({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
				                }
				            </script> -->
						<b>街道/门牌号：<input id="hairfulladdress" name="hairfulladdress" value="${hair.place}" type="text" placeholder="请填写详细信息,以便为你上门接货"/></b>
					</div>

					<div id="locations" class="citys">
						<span>*</span>&nbsp;&nbsp;&nbsp;收货地
							<select id="toprovince" name="province">
							
							    <option value="${toaddr.province}">${toaddr.province}</option>
							</select>
		                     <select id="tocity" name="ecity">
		                         <option value="${toaddr.city}">${toaddr.city}</option>
		                     </select>
		                     <select id="closedarea" name="closedarea">
		                         <option value="${toaddr.region_code}" >${toaddr.region_name}</option>
		                     </select>
		                     
		                   <!--    <script type="text/javascript">
				                if(remote_ip_info){
				                    $('#locations').citysclosed({province:remote_ip_info['province'],city:remote_ip_info['city'],area:remote_ip_info['district']});
				                }
				            </script> -->
						<b>街道/门牌号：<input id="closedfulladdress" name="closedfulladdress" value="${closed.place}"  type="text" placeholder="请填写详细信息,以便为你上门接货"/></b>
					</div>

					<p id="ids"></p>
				</div>
			</div>

			<div class="benner-translatet">
				<p><b class="benner-opsan">货物信息</b><span class="benner-translatet-span"> * 必填</span></p>
		
				<table id="questionTable" class="benner-translatet-table">
					<tr>
						<th></th>
						<th><span>*</span>&nbsp;&nbsp;货物名称</th>
						<th>总件数</th>
						<th><span>*</span>&nbsp;&nbsp;总重量</th>
						<th><span>*</span>&nbsp;&nbsp;总体积</th>
					</tr>
					<c:forEach items="${ordergoods}" var="item" varStatus="v">
					<tr>
						<td>
							<select name="Ordergoods[${v.index}].charge_by"  data="gz"  onchange="computePrice()">
								<option value="1" <c:if test="${item.charge_by eq 1}">selected="selected"</c:if>>规则货物收费(按重量体积收费)</option>
								<option value="2" <c:if test="${item.charge_by eq 1}">selected="selected"</c:if>>不规则货物收费(按件收费)</option>
							</select>
						</td>
						<td>
							<select name="Ordergoods[${v.index}].good_name">
								<option value="1"  <c:if test="${item.good_name eq 1}">selected="selected"</c:if>>设备</option>
								<option value="2" <c:if test="${item.good_name eq 2}">selected="selected"</c:if>>机械</option>
								<option value="3" <c:if test="${item.good_name eq 3}">selected="selected"</c:if>>煤炭</option>
								<option value="4" <c:if test="${item.good_name eq 4}">selected="selected"</c:if>>矿产</option>
								<option value="5" <c:if test="${item.good_name eq 5}">selected="selected"</c:if>>钢材</option>
								<option value="6" <c:if test="${item.good_name eq 6}">selected="selected"</c:if>>饲料</option>
								<option value="7" <c:if test="${item.good_name eq 7}">selected="selected"</c:if>>建材</option>
								<option value="8" <c:if test="${item.good_name eq 8}">selected="selected"</c:if>>木材</option>
								<option value="9" <c:if test="${item.good_name eq 9}">selected="selected"</c:if>>粮食</option>
								<option value="10" <c:if test="${item.good_name eq 10}">selected="selected"</c:if>>饮料</option>
								<option value="11" <c:if test="${item.good_name eq 11}">selected="selected"</c:if>>蔬菜</option>
								<option value="12" <c:if test="${item.good_name eq 12}">selected="selected"</c:if>>水果</option>
								<option value="13" <c:if test="${item.good_name eq 13}">selected="selected"</c:if>>畜产品</option>
								<option value="14" <c:if test="${item.good_name eq 14}">selected="selected"</c:if>>农资</option>
								<option value="15" <c:if test="${item.good_name eq 15}">selected="selected"</c:if>>服装鞋包</option>
								<option value="16" <c:if test="${item.good_name eq 16}">selected="selected"</c:if>>日用百货</option>
								<option value="17" <c:if test="${item.good_name eq 17}">selected="selected"</c:if>>药品</option>
								<option value="18" <c:if test="${item.good_name eq 18}">selected="selected"</c:if>>化工产品</option>
								<option value="19" <c:if test="${item.good_name eq 19}">selected="selected"</c:if>>电子电器</option>
								<option value="20" <c:if test="${item.good_name eq 20}">selected="selected"</c:if>>仪表仪器</option>
								<option value="21" <c:if test="${item.good_name eq 21}">selected="selected"</c:if>>汽车摩托</option>
								<option value="22" <c:if test="${item.good_name eq 22}">selected="selected"</c:if>>汽配摩配</option>
								<option value="23" <c:if test="${item.good_name eq 23}">selected="selected"</c:if>>图书音像</option>
								<option value="24" <c:if test="${item.good_name eq 24}">selected="selected"</c:if>>纸类包装</option>
								<option value="25" <c:if test="${item.good_name eq 25}">selected="selected"</c:if>>工艺礼品</option>
								<option value="26" <c:if test="${item.good_name eq 26}">selected="selected"</c:if>>文体用品</option>
								<option value="27" <c:if test="${item.good_name eq 27}">selected="selected"</c:if>>危险品</option>
							</select>
						</td>
						<td>
							<input name="Ordergoods[${v.index}].amount" type="text" value="${item.amount}" datatype="sl"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 件
						</td>
						<td>
							<input type="text" name="Ordergoods[${v.index}].weight" value="${item.weight}"  datatype="zl" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
							<select name="Ordergoods[${v.index}].weight_unit"  onchange="computePrice()">
								<option value="k" <c:if test="${item.weight_unit eq 'k'}">selected="selected"</c:if>>公斤</option>
								<option value="t" <c:if test="${item.weight_unit eq 't'}">selected="selected"</c:if>>吨</option>
							</select>
						</td>
						<td>
							<input name="Ordergoods[${v.index}].volume" type="text" value="${item.volume}" datatype="tj" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 立方
						</td>
					</tr>
					</c:forEach>
				</table>
				<div class="benner-translatet-button">
					<input id="btn" type="button" onclick="resetRows()" value="重置" >
					 <a href="javascript:void(0);" onclick="addRows()">+ 继续添加</a>
				</div>
				<p class="ts">重量和体积用于估量运费，以实际重量/体积为标准</p>
				<div id="totalInfos">
					<ul id="totalInfo">
						<li>总重量：<span id="tzl">${order.all_weight}</span>公斤</li>
						<li>总体积：<span id="ttj">${order.all_volume}</span>方</li>
						<li>总件数: <span id="tsl">${order.all_amount}</span>件</li>
						<li>专线运费预计: <span id="jiage">面议</span>元</li>
					</ul>
				</div>
			</div>
            <div class="benner-translateg">
            	<p><b class="benner-opsan">联系方式</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>* 必填</span></p>
            	<table cellspacing="0" class="benner-translateg-table">
            		<tr>
            			<th></th>
            			<th></th>
            		</tr>
            		<tr>
            			<td>发货人：<input id="sender" name="model.sender" type="text" value="${order.sender}"/> <span>* </span></td>
            			<td>发货人手机：<input id="sender_mobile" name="model.sender_mobile" type="text" value="${order.sender_mobile}"/> <span>* </span></td>
            	
            		</tr>
            		<tr>
            			<td>收货人：<input id="receiver" name="model.receiver" type="text" class="btn" value="${order.receiver}"/> <span>&nbsp;&nbsp;</span></td>
            			<td>收货人手机：<input id="receiver_mobile" name="model.receiver_mobile"  type="text" class="btn" value="${order.receiver_mobile}"/> <span>* </span></td>
            		</tr>
			    </table>
            	
            </div>
            <div class="benner-buttont">
	            	
	            	
	            	<input id="btnu" type="checkbox" name="model.send_status" value="1">&nbsp;&nbsp;&nbsp;保存为常用发货物
	            	<c:if test="${not empty Linesdata}">
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
	var fromregioncode = document.getElementById("fromregioncode").value;
	var toregioncode = document.getElementById("toregioncode").value;
	var orderid = document.getElementById("orderid").value;
	window.location.href="${ctx }/front/goods?fromregioncode="+fromregioncode+"&"+"toregioncode="+toregioncode+"&"+"orderid="+orderid+"&"+"orderid="+orderid;
}

function whole() {
	var fromregioncode = document.getElementById("fromregioncode").value;
	var toregioncode = document.getElementById("toregioncode").value;
	var orderid = document.getElementById("orderid").value;
	window.location.href="${ctx }/front/goods/vehiclelist?fromregioncode="+fromregioncode+"&"+"toregioncode="+toregioncode+"&"+"orderid="+orderid;
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

			+ "<td><input type='text' name=\"Ordergoods["+index+"].amount\" datatype=\"sl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>件</td>"

			+ "<td>"
			+ "<input name=\"Ordergoods["+index+"].weight\" type='text' datatype=\"zl\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>"
			+ "<select name=\"Ordergoods["+index+"].weight_unit\"  onchange=\"computePrice()\">"
			+ "<option value='k'>公斤</option>"
			+ "<option value='t'>吨</option>"
			+ "</select>"
			+ "</td>"

			+ "<td><input name=\"Ordergoods["+index+"].volume\" type='text' datatype=\"tj\"  onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\"/>立方</td>"

			+ "<td><input class='del' type='button' onclick='deltable(this);' value='删除'></td>"

			+ "</tr>";
	$("#questionTable").append(text);
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
