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
<body onload="shipTotalFee()">
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
		托运日期 : <span class="span"></span>
	</div>

	<div class="banner-list" id="mm"<%--onkeydown="keyDown(event)--%>">
		<div class="banner-list-left">
				<!-- 出发地城市code-->
				<input type="hidden" id="fromCode" name="kdShip.ship_from_city_code"/>
				<!-- 到达地城市code-->
				<input type="hidden" id="toCode" name="kdShip.ship_to_city_code"/>
				<!-- 托运人id-->
				<input type="hidden" id="shipSenderId" name="kdShip.ship_sender_id"/>
				<!-- 收货人id-->
				<input type="hidden" id="shipReceiverId" name="kdShip.ship_receiver_id"/>
			<table class="banner-list-left-table" cellspacing="0">
				<tr>
					<td>开单网点 :
						<input name="kdShip.network_id" id="netWorkId" />
					</td>
					<td>运单号 : <input type="text" id="shipSn" onblur="getGoodsSn();"  name="kdShip.ship_sn" /></td>
					<td>出发地 : 
					<input type="text" id="fromAddr" tabindex="-1" readonly/>

					</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>托运方</span>
					<input type="hidden" id="scode" name="address[0].region_code">
					<input type="hidden" id="suid"  name="address[0].uuid">
					<input type="hidden" id="sid" name="customer[0].customer_id">
					<input type="hidden"  name="customer[0].customer_type" value="2">
				</div>
				<div class="banner-list-lefttu2">
					<ul>
						<li><span>托运人 : </span><input type="text" id="senderName"  name="customer[0].customer_name" ></li>
						<li><span>联系电话 : </span><input type="text" id="senderMoblie" name="customer[0].customer_mobile" ></li>
						<li><span>详细地址 : </span><input type="text" id="senderAddress" name="address[0].tail_address" ></li>
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
					<tr>
						<td >
							<select id="pname1" name="kdProduct[0].product_name" class="easyui-combobox" style="width:95px; height:30px;" >
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
						<td><select id="unit1" name="kdProduct[0].product_unit" class="easyui-combobox" style="width:95px;" >
							<option value=""></option>
							<option value="1">散装</option>
							<option value="2">捆装</option>
							<option value="3">袋装</option>
							<option value="4">箱装</option>
							<option value="5">桶装</option>
						</select></td>
						<td><input type="text" name="kdProduct[0].product_amount" onblur="getGoodsSn();" id="product_amount0"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
						<td><input type="text"  name="kdProduct[0].product_volume"   onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[0].product_weight"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[0].product_price"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					</tr>
					<tr>
						<td>
							<select id="pname2" class="easyui-combobox" name="kdProduct[1].product_name" style="width:95px; height:30px;" >
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
						<td><input type="text" name="kdProduct[1].product_amount"  onblur="getGoodsSn();" id="product_amount1"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
						<td><input type="text"  name="kdProduct[1].product_volume"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[1].product_weight" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text" name="kdProduct[1].product_price"   onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
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
					<input type="text" id="toNetWorkName" readonly/>
					<input type="hidden" id="toNetWorkId" name="kdShip.to_network_id"/>
					</td>
					<td id="tddispatch"><input type="checkbox" id="dispatch" onfocus="$('#tddispatch').addClass('tdfocus');" onblur="$('#tddispatch').removeClass('tdfocus')" style="width:12px;height:12px;" name="kdShip.ship_is_dispatch"/> 急件</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>收货方</span>
					
					<input type="hidden" id="rcode"  name="address[1].region_code">
					<input type="hidden" id="ruid"  name="address[1].uuid">
					<input type="hidden" id="rid"  name="customer[1].customer_id">
					<input type="hidden"  name="customer[1].customer_type" value="1">
				</div>
				<div class="banner-list-lefttu2">
					<ul>
						<li><span>收货人 : </span><input type="text" id="receiverName" name="customer[1].customer_name" ></li>
						<li><span>联系电话 : </span><input type="text" id="receiverMoblie" name="customer[1].customer_mobile" ></li>
						<li><span>详细地址 : </span><input type="text" id="receiverAddress" name="address[1].tail_address" ></li>
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
						<td><input type="text"  name="kdShip.ship_fee" value="0"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"  onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_insurance_fee" value="0" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"  onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_package_fee" value="0" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();" ></td>
						<td><input type="text"  name="kdShip.ship_delivery_fee" value="0"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text"  name="kdShip.ship_pickup_fee" value="0"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text" id="addonFee"  name="kdShip.ship_addon_fee" value="0" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" onblur="shipTotalFee();"></td>
						<td><input type="text" id="totalFee" name="kdShip.ship_total_fee" value="0" onblur="shipTotalFee();" tabindex="-1" readonly></td>
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
						<td><select id="payWay" name="kdShip.ship_pay_way" onchange="changePayway();">
							<option value="1">现付</option>
							<option value="2">提付</option>
							<option value="3">回单付 </option>
							<option value="4">月结</option>
							<option value="5">短欠</option>
							<option value="6">货款扣</option>
							<option value="7">多笔付</option>
						</select></td>
						<td><input type="text" onblur="shipTotalFee();"  name="kdShip.ship_nowpay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" onblur="shipTotalFee();" name="kdShip.ship_pickuppay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" onblur="shipTotalFee();"  name="kdShip.ship_receiptpay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" onblur="shipTotalFee();"  name="kdShip.ship_monthpay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" onblur="shipTotalFee();"  name="kdShip.ship_arrearspay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
						<td><input type="text" onblur="shipTotalFee();"  name="kdShip.ship_goodspay_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="banner-list2">
			<table cellspacing="0">
				<tr>
					<td>回单要求 : <select name="kdShip.ship_receipt_require" >
						<option value="1">回单</option>
						<option value="2">电子回单</option>
						<option value="3">原单</option>
						<option value="4">收条</option>
						<option value="5">信封</option>
					</select></td>
					<td>回单份数 :<input type="text" name="kdShip.ship_receipt_num" oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')" ></td>
					<td colspan="4">回单备注 :<input style="width:690px;" type="text"  name="kdShip.ship_receipt_remark"></td>
				</tr>
				<tr>
					<td>货号 : <input type="text" id="goodsSn" name="kdShip.goods_sn" ></td>
					<td>送货方式 :<select id="deliveryMethod" name="kdShip.ship_delivery_method" >
						<option value="1">自提</option>
						<option value="2">送货上门</option>
						<option value="3">送货上楼</option>
						<option value="4">送货卸货</option>
					</select></td>
					<td>代收货款 : <input type="text" name="kdShip.ship_agency_fund" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					<td>客户单号 : <input type="text" name="kdShip.ship_customer_number" ></td>
					<td>回扣 : <input type="text" name="kdShip.ship_rebate_fee" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					<td id="rebate"><input type="checkbox" onfocus="$('#rebate').addClass('tdfocus');" onblur="$('#rebate').removeClass('tdfocus')" style="width:12px;height:12px;" name="kdShip.ship_is_rebate" /> 回扣已返</td>
				</tr>
				<tr>
					<td colspan="5" style="text-align: left;">开单备注 :<input type="text" id="remark" name="kdShip.remark" style="width:800px;"></td>
					<td>经办个人 : <span>${user.realname}</span></td>
				</tr>
			</table>
		</div> 
	</div>
	<button class="button" id="saveButton" style="margin-left:450px;" onclick="saveShip();" >保存(F9)</button>
	<button class="button" style="margin-left:5px;" onclick="saveShip(1);">保存并打印(F10)</button>
	<button class="button" style="margin-left:5px;" onclick="parent.layer.closeAll();">关闭(ESC)</button>
</div>
</form>
<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>	
 <script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
<script src="${ctx}/static/kd/js/waybill/addwaybill.js"></script>
</body>
</html>