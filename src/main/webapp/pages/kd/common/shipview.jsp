<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/kd/css/waybills.css" />
<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}"/>
</head>
<body>
	<!-- <div id="fade" class="black_overlay"></div>
	<div id="MyDiv" class="white_content">
		<div class="white_contents">
               新增收货方
               <span class="white_contents-span"></span>
           </div>
		<div class="tab-list">
			<p>运单信息</p>
			<ul>
				<li><a href="#">未发车</a></li>
				<li><a href="#">在途中</a></li>
				<li><a href="#">已签收</a></li>
			</ul>
			<table>
				<tr>
					<td>运单号 :YD00001</td>
					<td>开单网点 :广州白云区</td>
					<td>运单状态 :未发车</td>
				</tr>
				<tr>
					<td>客户单号 :E23121423</td>
					<td>送货方式 :自提</td>
					<td>代收货款 :2500</td>
				</tr>
				<tr>
					<td colspan="3">备注 :</td>
				</tr>
			</table>
		</div>

		<div class="tab-list2">
			<table>
				<tr>
					<th>托运方</th>
				</tr>
				<tr>
					<td>张三</td>
				</tr>
				<tr>
					<td>联系电话 : 13812345678</td>
				</tr>
				<tr>
					<td>出发地 : 广州市白云区</td>
				</tr>
				<tr>
					<td>详细地址 : 石牌桥丰兴广场1804号</td>
				</tr>
			</table>
		</div>
	</div> -->
	<div id="fade3" class="black_overlay3"></div>
	<div id="MyDiv3" class="white_content3">
		<!--<div class="white_contents">
               新增收货方
               <span class="white_contents-span"></span>
           </div>-->
		<div class="banner-right-list5">
			<%--<h1>运单详情</h1>--%>
			<p class="banner-list5-type">
				运单状态：
				<c:if test="${kdShip.ship_status==1}">
				<span>已入库</span>
				</c:if>
				<c:if test="${kdShip.ship_status==2}">
				<span>短驳中</span>
				</c:if>
				<c:if test="${kdShip.ship_status==3}">
					<span>短驳到达</span>
				</c:if>
				<c:if test="${kdShip.ship_status==4}">
				<span>已发车</span>
				</c:if>
				<c:if test="${kdShip.ship_status==5}">
					<span>已到达</span>
				</c:if>
				<c:if test="${kdShip.ship_status==6}">
					<span>收货中转中</span>
				</c:if>
				<c:if test="${kdShip.ship_status==7}">
					<span>到货中转中</span>
				</c:if>
				<c:if test="${kdShip.ship_status==8}">
					<span>送货中</span>
				</c:if>
				<c:if test="${kdShip.ship_status==9}">
					<span>已签收</span>
				</c:if>
			</p>
			<script>
	        document.onkeyup=function(event){
	      	  var e=event||window.event;
	      	  var keyCode=e.keyCode||e.which;
	      	  if(keyCode == 120){
	      		  alert("ddd");
	      	  }
	        }
	        </script>
			<div class="banner-list5-div1">
				<p class="banner-div-title">运单信息</p>
				<ul>
					<li>
						<p>
							运单号：<span>${kdShip.ship_sn}</span>
						</p>
					</li>
					<li>
						<p>
							开单网点：<span>${kdShip.netWorkName}</span>
						</p>
					</li>
					<li>
						<p>
							开单时间：<span><fmt:formatDate value="${kdShip.create_time}" pattern="yyyy-MM-dd HH:mm"/></span>
						</p>
					</li>
					<li>
						<p>
							开单人：<span>${kdShip.userName}</span>
						</p>
					</li>
					<li>
						<p>
							客户单号：<span>${kdShip.ship_customer_number}</span>
						</p>
					</li>
					<li>
						<p>
							送货方式：
							<c:if test="${kdShip.ship_delivery_method==1}">
							<span>自提</span>
							</c:if>
							<c:if test="${kdShip.ship_delivery_method==2}">
							<span>送货上门</span>
							</c:if>
							<c:if test="${kdShip.ship_delivery_method==3}">
							<span>送货上楼</span>
							</c:if>
							<c:if test="${kdShip.ship_delivery_method==4}">
							<span>送货卸货</span>
							</c:if>
						</p>
					</li>
					<li>
						<p>
							代收货款：<span>${kdShip.ship_agency_fund}</span>元
						</p>
					</li>
					<li>
						<p>
							备注：<span>${kdShip.remark}</span>
						</p>
					</li>
				</ul>
			</div>
			<div class="banner-list5-div2">
				<ul class="banner-div2-title">
					<li>托运方：</li>
					<li>收货方：</li>
				</ul>
				<ul class="banner-div2-ul">
					<li>
						<p>
							姓名：<span>${senderCustomer.customer_name}</span>

						</p>
					</li>
					<li>
						<p>
							公司：<span>${senderCustomer.customer_corp_name}</span>

						</p>
					</li>
					<li>
						<p>
							联系电话：<span>${senderCustomer.customer_mobile}</span>
						</p>
					</li>
					<li>
						<p>
							出发地：<span>${senderCustomer.to_addr}</span>
						</p>
					</li>
					<li>
						<p>
							详细地址：<span>${senderCustomer.tail_address}</span>
						</p>
					</li>
				</ul>
				<ul class="banner-div2-ul2">
					<li>
						<p>
							姓名：<span>${receiverCustomer.customer_name}</span>

						</p>
					</li>
					<li>
						<p>
							公司：<span>${receiverCustomer.customer_corp_name}</span>

						</p>
					</li>
					<li>
						<p>
							联系电话：<span>${receiverCustomer.customer_mobile}</span>
						</p>
					</li>
					<li>
						<p>
							到达地：<span>${receiverCustomer.to_addr}</span>
						</p>
					</li>
					<li>
						<p>
							详细地址：<span>${receiverCustomer.tail_address}</span>
						</p>
					</li>
				</ul>

			</div>
			<div class="banner-list5-div3">
				<p class="banner-div-title">货物清单</p>
				<div class="banner-div3-table">
					<table border="0">
						<thead>
							<th>货物名称</th>
							<th>包装单位</th>
							<th>件数</th>
							<th>体积</th>
							<th>重量</th>
							<th>货值</th>
							<th>货物编码</th>
						</thead>
						<c:forEach items="${kdProducts}" var="kdProduct">
						<tr>

							<td>${kdProduct.product_name}</td>
							<c:if test="${kdProduct.product_unit==1}">
								<td>散装</td>
							</c:if>
							<c:if test="${kdProduct.product_unit==2}">
								<td>捆装</td>
							</c:if>
							<c:if test="${kdProduct.product_unit==3}">
								<td>袋装</td>
							</c:if>
							<c:if test="${kdProduct.product_unit==4}">
								<td>箱装</td>
							</c:if>
							<c:if test="${kdProduct.product_unit==5}">
								<td>桶装</td>
							</c:if>
							<td>${kdProduct.product_amount}</td>
							<td>${kdProduct.product_volume}立方</td>
							<td>${kdProduct.product_weight}公斤</td>
							<td>${kdProduct.product_price}</td>
							<c:choose>
								<c:when test="${kdProduct.product_sn==null}">
									<td>-</td>
								</c:when>
								<c:otherwise>
									<td>${kdProduct.product_sn}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="banner-list5-div4">
				<p class="banner-div-title">运费</p>
				<ul>
					<li>
						<p>
							运费：<span>${kdShip.ship_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							保价费：<span>${kdShip.ship_insurance_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							提货费：<span>${kdShip.ship_pickup_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							包装费：<span>${kdShip.ship_package_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							送货费：<span>${kdShip.ship_delivery_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							其他费用：<span>${kdShip.ship_addon_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							合计：<span>${kdShip.ship_total_fee}</span>元
						</p>
					</li>
					<li>
						<p>
							付款方式：
							<c:if test="${kdShip.ship_pay_way==1}">
							<span>现付</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==2}">
							<span>提付</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==3}">
							<span>回单付</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==4}">
							<span>月结</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==5}">
							<span>短欠</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==6}">
							<span>货款扣</span>
							</c:if>
							<c:if test="${kdShip.ship_pay_way==7}">
								<span>多笔付</span>
							</c:if>
						</p>
					</li>
				</ul>
			</div>
			<br />
		</div>
	</div>
</body>
</html>