<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>物流托运单</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/waybill.css" />
	<%@ include file="../common/commonhead.jsp" %>
</head>
<body>
<style>
input:focus
{ 
background-color:#ADD8E6;
}
select:focus
{ 
background-color:#ADD8E6;
}
.tdfocus{
background-color:#ADD8E6;
}

</style>
<form id="formid" onsubmit="return false;">
<div class="banner">
	<div class="banner-liu">
		${company.corpname}物流托运单
		<!-- <ul>
           <li></li>
           <li></li>
        </ul> -->
	</div>
	<div class="banner-right">
		托运日期 : <span class="span"><fmt:formatDate value="${kdShip.create_time}"   pattern="yyyy-MM-dd hh:mm " type="date"  /> </span>
	</div>

	<div class="banner-list" id="mm"<%--onkeydown="keyDown(event)--%>">
		<div class="banner-list-left">
			<input type="hidden"  name="kdShip.create_time" value="${kdShip.create_time}"/>
			<input type="hidden"  name="kdShip.ship_id" value="${kdShip.ship_id}"/>
				<!-- 出发地城市code-->
				<input type="hidden" id="fromCode" name="kdShip.ship_from_city_code" value="${kdShip.ship_from_city_code}"/>
				<!-- 到达地城市code-->
				<input type="hidden" id="toCode" name="kdShip.ship_to_city_code" value="${kdShip.ship_to_city_code}"/>
				<!-- 托运人id-->
				<input type="hidden" id="shipSenderId" name="kdShip.ship_sender_id" value="${kdShip.ship_sender_id}"/>
				<!-- 收货人id-->
				<input type="hidden" id="shipReceiverId" name="kdShip.ship_receiver_id" value="${kdShip.ship_receiver_id}"/>
			<table class="banner-list-left-table" cellspacing="0">
				<tr>
					<td>开单网点 :
						<input name="kdShip.network_id" id="netWorkId" readonly="readonly"/>
					</td>
					<td>运单号 : <input type="text" id="shipSn"  value="${kdShip.ship_sn}"  readonly  name="kdShip.ship_sn" /></td>
					<td>出发地 : 
					<input type="text" id="fromAddr" tabindex="-1" readonly/>

					</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>托运方</span>
					<input type="hidden" id="scode" name="address[0].region_code" value="${kdShip.ship_from_city_code}">
					<input type="hidden" id="suid"  name="address[0].uuid" value="${kdShip.suuid}">
					<input type="hidden" id="sid" name="customer[0].customer_id" value="${kdShip.ship_sender_id}">
					<input type="hidden"  name="customer[0].customer_type" value="2">
				</div>
				<div class="banner-list-lefttu2">
					<ul>
						<li><span>托运人 : </span><input type="text" id="senderName"  name="customer[0].customer_name" value="${senderCustomer.customer_name}" ></li>
						<li><span>联系电话 : </span><input type="text" id="senderMoblie" name="customer[0].customer_mobile"   value="${kdShip.sendMobile}"></li>
						<li><span>详细地址 : </span><input type="text" id="senderAddress" name="address[0].tail_address" value="${kdShip.senderAdd}" ></li>
					</ul>
				</div>
			</div>
			<div class="banner-list-left3">
				<table cellspacing="0">
					<tr>
						<th>货物名称</th>
						<th>包装</th>
						<th>件数</th>
						<th>体积/方</th>
						<th>重量/公斤</th>
						<th>声明货值</th>
					</tr>
					<c:forEach items="${kdProducts}" var="product" varStatus="v">
					<tr>
						<td >
							<select id="pname1" name="kdProduct[${v.index}].product_name" class="easyui-combobox" style="width:95px; height:30px;" >
								<option value="${product.product_name}">${product.product_name}</option>
								<option value="配件"<c:if test="${product.product_name eq '配件'}"> selected</c:if>>配件</option>
								<option value="服装" <c:if test="${product.product_name eq '服装'}"> selected</c:if> >服装</option>
								<option value="食品" <c:if test="${product.product_name eq '食品'}"> selected</c:if>>食品</option>
								<option value="文具" <c:if test="${product.product_name eq '文具'}"> selected</c:if>>文具</option>
								<option value="化妆品" <c:if test="${product.product_name eq '化妆品'}"> selected</c:if>>化妆品</option>
								<option value="涂料" <c:if test="${product.product_name eq '涂料'}"> selected</c:if>>涂料</option>
								<option value="电器" <c:if test="${product.product_name eq '电器'}"> selected</c:if>>电器</option>
								<option value="茶叶" <c:if test="${product.product_name eq '茶叶'}"> selected</c:if>>茶叶</option>
								<option value="家具" <c:if test="${product.product_name eq '家具'}"> selected</c:if>>家具</option>
								<option value="其它" <c:if test="${product.product_name eq '其它'}"> selected</c:if>>其它</option>
								</select>
						</td>
						<td><select id="unit1" name="kdProduct[${v.index}].product_unit" class="easyui-combobox" style="width:95px;" >
							<option value="1" <c:if test="${product.product_unit==1}">selected</c:if>>散装</option>
							<option value="2" <c:if test="${product.product_unit==2}">selected</c:if>>捆装</option>
							<option value="3" <c:if test="${product.product_unit==3}">selected</c:if>>袋装</option>
							<option value="4" <c:if test="${product.product_unit==4}">selected</c:if>>箱装</option>
							<option value="5" <c:if test="${product.product_unit==5}">selected</c:if>>桶装</option>
						</select></td>
						<td><input type="text" name="kdProduct[${v.index}].product_amount" value="${product.product_amount}" onblur="getGoodsSn();" id="product_amount${v.index}"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_volume"   value="${product.product_volume}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_weight"  value="${product.product_weight}"   onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_price"  value="${product.product_price}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					</tr>
					</c:forEach>
					<c:if test="${fn:length(kdProducts)==1}">
						<tr>
							<td>
								<select id="pname2"  name="kdProduct[1].product_name" class="easyui-combobox" style="width:95px; height:30px;" >
									<option value=""></option>
									<option value="配件">配件</option>
									<option value="服装">服装</option>
									<option value="食品">食品</option>
									<option value="文具">文具</option>
									<option value="化妆品">化妆品</option>
									<option value="涂料">涂料</option>
									<option value="电器">电器</option>
									<option value="茶叶">茶叶</option>
									<option value="家具">家具</option>
									<option value="其它">其它</option>
								</select>
							</td>
							<td><select id="unit2" name="kdProduct[1].product_unit" class="easyui-combobox" style="width:95px;" >
								<option value=""></option>
								<option value="1">散装</option>
								<option value="2">捆装</option>
								<option value="3">袋装</option>
								<option value="4">箱装</option>
								<option value="5">桶装</option>
							</select></td>

							<td><input type="text"  name="kdProduct[1].product_amount" onblur="getGoodsSn();" id="product_amount1"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
							<td><input type="text"  name="kdProduct[1].product_volume"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text" name="kdProduct[1].product_weight"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text"  name="kdProduct[1].product_price"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						</tr>
						<tr>
							<td>
								<select id="pname3"  name="kdProduct[2].product_name" class="easyui-combobox" style="width:95px; height:30px;" >
									<option value=""></option>
									<option value="配件">配件</option>
									<option value="服装">服装</option>
									<option value="食品">食品</option>
									<option value="文具">文具</option>
									<option value="化妆品">化妆品</option>
									<option value="涂料">涂料</option>
									<option value="电器">电器</option>
									<option value="茶叶">茶叶</option>
									<option value="家具">家具</option>
									<option value="其它">其它</option>
								</select>
							</td>
							<td><select id="unit3" name="kdProduct[2].product_unit" class="easyui-combobox" style="width:95px;" >
								<option value=""></option>
								<option value="1">散装</option>
								<option value="2">捆装</option>
								<option value="3">袋装</option>
								<option value="4">箱装</option>
								<option value="5">桶装</option>
							</select></td>

							<td><input type="text"  name="kdProduct[2].product_amount" onblur="getGoodsSn();" id="product_amount2"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
							<td><input type="text"  name="kdProduct[2].product_volume"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text" name="kdProduct[2].product_weight"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text"  name="kdProduct[2].product_price"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						</tr>
					</c:if>
					<c:if test="${fn:length(kdProducts)==2}">
						<tr>
							<td>
								<select id="pname3"  name="kdProduct[2].product_name" class="easyui-combobox" style="width:95px; height:30px;" >
									<option value=""></option>
									<option value="配件">配件</option>
									<option value="服装">服装</option>
									<option value="食品">食品</option>
									<option value="文具">文具</option>
									<option value="化妆品">化妆品</option>
									<option value="涂料">涂料</option>
									<option value="电器">电器</option>
									<option value="茶叶">茶叶</option>
									<option value="家具">家具</option>
									<option value="其它">其它</option>
								</select>
							</td>
							<td><select id="unit3" name="kdProduct[2].product_unit" class="easyui-combobox" style="width:95px;" >
								<option value=""></option>
								<option value="1" >散装</option>
								<option value="2">捆装</option>
								<option value="3">袋装</option>
								<option value="4">箱装</option>
								<option value="5">桶装</option>
							</select></td>

							<td><input type="text"  name="kdProduct[2].product_amount" onblur="getGoodsSn();" id="product_amount2"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
							<td><input type="text"  name="kdProduct[2].product_volume"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text" name="kdProduct[2].product_weight"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
							<td><input type="text"  name="kdProduct[2].product_price"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>

		<div class="banner-list-left" style="margin-left: 16px;">
			<table class="banner-list-left-table" cellspacing="0">
				<tr>
					<td>到达地 : 
					<input type="text" id="toAddr" />
					<input type="text" id="toAddrCounty" />
					</td>
					<td>到货网点 : 
					<input type="text" id="toNetWorkName" value="${kdShip.toNetWorkName }" tabindex="-1" readonly/>
					<input type="hidden" id="toNetWorkId" name="kdShip.to_network_id" value="${kdShip.to_network_id }"/>
					</td>
					<td id="tddispatch"><input type="checkbox" id="dispatch" <c:if test="${kdShip.ship_is_dispatch}"> checked</c:if>   onfocus="$('#tddispatch').addClass('tdfocus');" onblur="$('#tddispatch').removeClass('tdfocus')" style="width:12px;height:12px;" name="kdShip.ship_is_dispatch"/> 急件</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>收货方</span>
					
					<input type="hidden" id="rcode"  name="address[1].region_code" value="${kdShip.ship_to_city_code}">
					<input type="hidden" id="ruid"  name="address[1].uuid" value="${kdShip.ruuid}">
					<input type="hidden" id="rid"  name="customer[1].customer_id" value="${kdShip.ship_receiver_id}">
					<input type="hidden"  name="customer[1].customer_type" value="1">
				</div>
				<div class="banner-list-lefttu2">
					<ul>
						<li><span>收货人 : </span><input type="text" id="receiverName" name="customer[1].customer_name" value="${receiverCustomer.customer_name}" ></li>
						<li><span>联系电话 : </span><input type="text" id="receiverMoblie" name="customer[1].customer_mobile"  value="${kdShip.receiverMobile}"></li>
						<li><span>详细地址 : </span><input type="text" id="receiverAddress" name="address[1].tail_address" value="${kdShip.receiverAdd}" ></li>
					</ul>
				</div>
			</div>
			<div class="banner-list-left3">
				<table cellspacing="0">
					<tr>
						<th>运费</th>
						<th>保价费</th>
						<th>包装费</th>
						<th>送货费</th>
						<th>提货费</th>
						<th>其他费</th>
						<th>合计</th>
					</tr>
					<tr>
						<td><input type="text"  name="kdShip.ship_fee" value="${kdShip.ship_fee}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"  onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_insurance_fee" value="${kdShip.ship_insurance_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"  onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_package_fee"  value="${kdShip.ship_package_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_delivery_fee"   value="${kdShip.ship_delivery_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();" ></td>
						<td><input type="text"  name="kdShip.ship_pickup_fee"  value="${kdShip.ship_pickup_fee}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_addon_fee" value="${kdShip.ship_addon_fee}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_total_fee"  value="${kdShip.ship_total_fee}" onblur="shipTotalFee();" tabindex="-1" readonly></td>
					</tr>
					<tr>
						<th>付款方式</th>
						<th>现付</th>
						<th>提付</th>
						<th>回单付</th>
						<th>月结</th>
						<th>短欠</th>
						<th>货款扣</th>
					</tr>
					<tr>
						<td><select id="payWay" name="kdShip.ship_pay_way" onchange="changePayway()">
							<option value="1" <c:if test="${kdShip.ship_pay_way==1}"> selected</c:if> >现付</option>
							<option value="2" <c:if test="${kdShip.ship_pay_way==2}"> selected</c:if> >提付</option>
							<option value="3" <c:if test="${kdShip.ship_pay_way==3}"> selected</c:if> >回单付 </option>
							<option value="4" <c:if test="${kdShip.ship_pay_way==4}"> selected</c:if> >月结</option>
							<option value="5" <c:if test="${kdShip.ship_pay_way==5}"> selected</c:if> >短欠</option>
							<option value="6" <c:if test="${kdShip.ship_pay_way==6}"> selected</c:if> >货款扣</option>
							<option value="7" <c:if test="${kdShip.ship_pay_way==7}"> selected</c:if> >多笔付</option>
						</select></td>
						<td><input type="text"  name="kdShip.ship_nowpay_fee" value="${kdShip.ship_nowpay_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" name="kdShip.ship_pickuppay_fee" value="${kdShip.ship_pickuppay_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text"  name="kdShip.ship_receiptpay_fee" value="${kdShip.ship_receiptpay_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text"  name="kdShip.ship_monthpay_fee"  value="${kdShip.ship_monthpay_fee}"onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text"  name="kdShip.ship_arrearspay_fee" value="${kdShip.ship_arrearspay_fee}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text"  name="kdShip.ship_goodspay_fee"  value="${kdShip.ship_goodspay_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="banner-list2">
			<table cellspacing="0">
				<tr>
					<td>回单要求 : <select name="kdShip.ship_receipt_require" >
						<option value="1" <c:if test="${kdShip.ship_receipt_require==1}"> selected</c:if> >回单</option>
						<option value="2" <c:if test="${kdShip.ship_receipt_require==2}"> selected</c:if> >电子回单</option>
						<option value="3" <c:if test="${kdShip.ship_receipt_require==3}"> selected</c:if> >原单</option>
						<option value="4" <c:if test="${kdShip.ship_receipt_require==4}"> selected</c:if> >收条</option>
						<option value="5" <c:if test="${kdShip.ship_receipt_require==5}"> selected</c:if> >信封</option>
					</select></td>
					<td>回单份数 :<input type="text" name="kdShip.ship_receipt_num" value="${kdShip.ship_receipt_num}" oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
					<td colspan="4">回单备注 :<input style="width:690px;" type="text" value="${kdShip.ship_receipt_remark}"  name="kdShip.ship_receipt_remark"></td>
				</tr>
				<tr>
					<td>货号 : <input type="text" id="goodsSn" name="kdShip.goods_sn" value="${kdShip.goods_sn}" ></td>
					<td>送货方式 :<select name="kdShip.ship_delivery_method" >
						<option value="1" <c:if test="${kdShip.ship_delivery_method==1}"> selected</c:if>>自提</option>
						<option value="2" <c:if test="${kdShip.ship_delivery_method==2}"> selected</c:if>>送货上门</option>
						<option value="3" <c:if test="${kdShip.ship_delivery_method==3}"> selected</c:if>>送货上楼</option>
						<option value="4" <c:if test="${kdShip.ship_delivery_method==4}"> selected</c:if>>送货卸货</option>
					</select></td>
					<td>代收货款 : <input type="text" name="kdShip.ship_agency_fund" value="${kdShip.ship_agency_fund}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					<td>客户单号 : <input type="text" name="kdShip.ship_customer_number" value="${kdShip.ship_customer_number}" ></td>
					<td>回扣 : <input type="text" name="kdShip.ship_rebate_fee" value="${kdShip.ship_rebate_fee}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					<td id="rebate"><input type="checkbox" <c:if test="${kdShip.ship_is_rebate}"> checked</c:if>  onfocus="$('#rebate').addClass('tdfocus');" onblur="$('#rebate').removeClass('tdfocus')" style="width:12px;height:12px;" name="kdShip.ship_is_rebate" /> 回扣已返</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: left;">开单备注 :<input name="kdShip.remark" type="text"  style="width:800px;"></td>
					<td>经办个人 : <span>${kdShip.userName}</span></td>
				</tr>
			</table>
		</div> 
	</div>

	<button class="button" style="margin-left:450px;" onclick="saveShip();" >保存(F9)</button>
	<button class="button" style="margin-left:5px;" onclick="closeDiv()">返回</button>
	<button class="button" style="margin-left:5px;" onclick="parent.layer.closeAll();">关闭(ESC)</button>
	
</div>
</form>
<script src="${ctx}/static/kd/js/waybill/upwaybill.js"></script>
<script>
	var flag=${not empty toAddrCounty};
    netWorkCombobox();
    $('#netWorkId').combobox('setValue',${kdShip.network_id});
    $("#fromAddr").val('${kdShip.fromAdd}');
    toAddrCombobox(${kdShip.network_id});

	if(flag){
        $('#toAddr').combobox('setValue','${toAddrCounty}');
        toAddrCountyCombobox ('${toAddrCounty}');
        $('#toAddrCounty').combobox('setValue','${kdShip.ship_to_city_code}');
        $('#toCode').val('${toAddrCounty}');
        $('#rcode').val('${toAddrCounty}');
	}else{
        toAddrCountyCombobox ('${kdShip.ship_to_city_code}');
        $('#toAddr').combobox('setValue','${kdShip.ship_to_city_code}');
        $('#toCode').val('${kdShip.ship_to_city_code}');
        $('#rcode').val('${kdShip.ship_to_city_code}');
    }
</script>
</body>
</html>