<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运单详情</title>
    <link rel="stylesheet" href="${ctx}/static/kd/css/info.css" />
   
    <link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
    <link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
    <%@ include file="../common/commonhead.jsp" %>
</head>
<body>
	<%@ include file="../common/head2.jsp" %>
	<%@ include file="../common/head.jsp" %>
	<div class="banner">
    <%@ include file="../common/fahuoleft.jsp" %>
    <script type="text/javascript">
		     $(function(){
				  var _width=$("body").width();
				  var _widths = $(".banner-left").width();
				  var _widthd = _width - _widths - 80;
				  parseInt(_widthd);
				  $('.banner-right').css('width',_widthd+'px');
		     });
		     $(window).resize(function(){ 
		    	  var Width = $(window).width();
	    	      var _widths = $(".banner-left").width();
		  		  var _widthd = Width - _widths - 80;
		  		  parseInt(_widthd);
		  		  $('.banner-right').css('width',_widthd+'px');
		     });
	  </script>
    <div class="banner-right">
       <div class="banner-right-ultitle" style="overflow: hidden;">
				<ul>
		            <li>
		                <a href="/kd/waybill/viewDetail?shipId=${shipId}"  class="activet at">运单信息</a>
		            </li>
		            <li>
		                <a href="/kd/waybill/shipLog?shipId=${shipId}"  class="at" >操作日志</a>
		            </li>
		             <li>
		                <a href="/kd/waybill/shipTransportInfo?shipId=${shipId}"  class="at">物流跟踪</a>
		            </li>
					<li>
						<a href="/kd/waybill/shipChangeInfo?shipId=${shipId}"  class="at" >改单记录</a>
					</li>
					<li>
						<a href="/kd/waybill/transactionInfo?shipId=${shipId}"  class="at" >异动记录</a>
					</li>
		            <li>
		                <a href="/kd/waybill/receiptInfo?shipId=${shipId}"  class="at" >回单日志</a>
		            </li> 
		            <li>
		                <a href="/kd/waybill/abnormalInfo?shipId=${shipId}"  class="at" >异常记录</a>
		            </li>
	       		 </ul> 
	       	</div>
       
            <div class="banner-right-list">
                   <div class="banner-liu">
		${company.corpname}物流托运单
	</div>
	<div class="banner-wuliu-right">

		托运日期 : <span class="span"><fmt:formatDate value="${kdShip.create_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></span>
	</div>

	<div class="banner-list" >
		<div class="banner-list-left">
			<table class="banner-list-left-table" cellspacing="0">
				<tr>
					<td>开单网点 :
						<input name="kdShip.network_id" id="netWorkId"  value="${kdShip.netWorkName}" />
					</td>
					<td>运单号 : <input type="text" id="shipSn"  value="${kdShip.ship_sn}"  readonly  name="kdShip.ship_sn" /></td>
					<td>出发地 : 
					<input type="text" id="fromAddr" tabindex="-1" value="${kdShip.fromAdd}"/>

					</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>托运方</span>
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
							<input type="text" value="${product.product_name}">
						</td>
						<td>
							<c:if test="${product.product_unit==1}">
								<input type="text" value="散装">
							</c:if>

							<c:if test="${product.product_unit==2}">
								<input type="text" value="捆装">
							</c:if>

							<c:if test="${product.product_unit==3}">
								<input type="text" value="袋装">
							</c:if>

							<c:if test="${product.product_unit==4}">
								<input type="text" value="箱装">
							</c:if>

							<c:if test="${product.product_unit==5}">
								<input type="text" value="桶装">
							</c:if></td>
						<td><input type="text" name="kdProduct[${v.index}].product_amount" value="${product.product_amount}" onblur="getGoodsSn();" id="product_amount${v.index}"  oninput='this.value=this.value.replace(/^[0]+[0-9]*$/gi,"")' maxlength="5"  onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_volume"   value="${product.product_volume}"  onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_weight"  value="${product.product_weight}"   onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
						<td><input type="text"  name="kdProduct[${v.index}].product_price"  value="${product.product_price}" onkeyup="this.value=/^\d+\.?\d{0,3}$/.test(this.value) ? this.value : ''" ></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<div class="banner-list-left" style="margin-left: 16px;">
			<table class="banner-list-left-table" cellspacing="0">
				<tr>
					<td>到达地 : 
					<input type="text" id="toAddr" value="${kdShip.toAdd}" />
					</td>
					<td>到货网点 : 
					<input type="text" id="toNetWorkName" value="${kdShip.toNetWorkName }" tabindex="-1" readonly/>
					</td>
					<td id="tddispatch"><input type="checkbox" id="dispatch" <c:if test="${kdShip.ship_is_dispatch}"> checked</c:if>  onfocus="$('#tddispatch').addClass('tdfocus');" onblur="$('#tddispatch').removeClass('tdfocus')" style="width:12px;height:12px;" name="kdShip.ship_is_dispatch"/> 急件</td>
				</tr>
			</table>
			<div class="banner-list-left2">
				<div class="banner-list-lefttu">
					<span>收货方</span>
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
					<td colspan="5" style="text-align: left;">开单备注 :<input type="text"  style="width:800px;"></td>
					<td>经办个人 : <span>${kdShip.userName}</span></td>
				</tr>
			</table>
		</div> 
	</div>
</div>  
        <div class="banner-list-status">
			<c:if test="${kdShip.state==1}">
        		<img src="../../../static/kd/img/yijie.png">
			</c:if>
			<c:if test="${kdShip.state==2}">
				<img src="../../../static/kd/img/weijie.png">
			</c:if>
			<c:if test="${kdShip.state==3}">
				<img src="../../../static/kd/img/bufenjie.png">
			</c:if>
        </div>
        <div class="banner-right-list2">
          <p>配载信息</p>
       		<table>
       			<tr>
       				
       				<th>配载单号</th>
       				<th>配载网点</th>
       				<th>配载状态</th>
       				<th>车牌号</th>
       				<th>司机</th>
       				<th>司机电话</th>
       				<th>发车时间</th>
       				<th>运输类型</th>
       				<th>到达时间</th>
       			</tr>
       			<tr>
       				<c:forEach items="${trunkLoads}" var="load">
       				<td>${load.load_sn}</td>
       				<td>${load.loadWorkName}</td>
						<c:if test="${load.load_delivery_status==1}">
       					<td>配载中</td>
						</c:if>
						<c:if test="${load.load_delivery_status==2}">
							<td>已发车</td>
						</c:if>
						<c:if test="${load.load_delivery_status==3}">
							<td>已到达</td>
						</c:if>
						<c:if test="${load.load_delivery_status==4}">
						<td>已完成</td>
						</c:if>
       				<td>${load.truck_id_number}</td>
       				<td>${load.truck_driver_name}</td>
       				<td>${load.truck_driver_mobile}</td>
       				<td><fmt:formatDate value="${load.load_depart_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></td>
						<c:if test="${load.load_transport_type==1}">
       				<td> 提货</td>
						</c:if>
						<c:if test="${load.load_transport_type==2}">
							<td>短驳</td>
						</c:if>
						<c:if test="${load.load_transport_type==3}">
							<td>干线</td>
						</c:if>	<c:if test="${load.load_transport_type==4}">
						<td>送货</td>
					</c:if>
       				<td><fmt:formatDate value="${load.load_arrival_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></td>
       			</tr>
				</c:forEach>
       		</table>   
        </div> 
        <div class="banner-right-list3">
          <p>中转信息</p>
       		<table>
       			<tr>       				
       				<th>中转单号</th>
       				<th>中转网点</th>
       				<th>中转方</th>
       				<th>中转联系人</th>
       				<th>联系电话</th>
       				<th>中转日期</th>
       			</tr>
       			<tr>
       				
       				<td>${transfer.ship_transfer_sn}</td>
       				<td>${transfer.tranNetName}</td>
       				<td>${transfer.transferName}</td>
       				<td>${transfer.personName}</td>
       				<td>${transfer.personMobile}</td>
       				<td><fmt:formatDate value="${transfer.ship_transfer_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></td>
       			</tr>
       		</table>   
        </div>
        <div class="banner-right-list4">
        	 <p>签收信息</p>
        	 <span class="span3">签收人: <span style="color: red;">${sign.sign_person}</span></span>
        	 <span class="span4">签收时间:<span style="color: red;"><fmt:formatDate value="${sign.sign_time}" pattern="yyyy-MM-dd HH:mm " type="date" /></span>
			<c:forEach items="${libImages}" var="img">
        	 <div class="banner-list4-pic1">
				 <img src="${img.img}"/>
        	 	<p>签收图片</p>
        	 </div>
			</c:forEach>
        </div>
    </div>
</div>

<%@ include file="../common/loginfoot.jsp" %>
    <script src="${ctx}/static/kd/js/waybill/waybill.js?v=${version}"></script>
<script src="${ctx}/static/common/lodop/LodopFuncs.js"></script>	
 <script src="${ctx}/static/kd/js/print/waybillprint.js"></script>
</body>
</html>