<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>应收管理</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/eivable.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
<%@ include file="../../common/commonhead.jsp"%>
<script src="${ctx}/static/kd/js/receivable.js?v=${version}"></script>
</head>

<body>
	<!-- 头部文件 -->
	<%@ include file="../../common/head2.jsp"%>

	<%@ include file="../../common/head.jsp"%>

	<div class="banner">
		<!-- 左边菜单 -->
		<%@ include file="../../common/financialleft.jsp"%>
		<script type="text/javascript">
			$(function() {
				var _width = $("body").width();
				var _widths = $(".banner-left").width();
				var _widthd = _width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
			});
			$(window).resize(function() {
				var Width = $(window).width();
				var _widths = $(".banner-left").width();
				var _widthd = Width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
			});
		</script>
		<div class="banner-right">
			<form id="searchFrom" onsubmit="return false;">
				<div class="banner-right-list">
					<div class="div">
						<span class="span">开单网点：</span> <select name="networkId"
							id="networkId">
							<option value="">请选择</option>
							<c:forEach var="work" items="${networks}">
								<option value="${work.id}"
									<c:if test="${model.networkId==work.id}">selected</c:if>>${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">开单日期：</span><input type="text" class="Wdate"
							name="startTime" id="startTime" value="${model.startTime }"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})" />
					</div>
					<div class="div">
						<span class="spanc">至</span><input type="text" class="Wdate"
							value="${model.endTime }" id="endTime" name="endTime"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})" />
					</div>
					<div class="div">
						<span class="span">出发地：</span>
						<div class="banner-right-list-liopd">

							<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="formCode" id="formCode"> <input
										id="city-picker3" class="form-control" placeholder="请选择省/市/区"
										readonly type="text" data-toggle="city-picker">
								</div>
								<script>
									$(function() {
										$(".city-picker-span").css("width",
												"auto");
									});
								</script>
							</div>
						</div>
					</div>
					<div class="div">
						<span class="span">到达地：</span>
						<div class="banner-right-list-liopc">
							<div class="form-group">
								<div style="position: relative;">
									<input type="hidden" name="toCode" id="toCode"> <input
										id="city-picker2" class="form-control" placeholder="请选择省/市/区"
										readonly type="text" data-toggle="city-picker">
								</div>
								<script>
									$(
											function() {
												$(".city-picker-span").css(
														"width", "auto");
											})
								</script>
							</div>
						</div>
					</div>
					<div class="div">
						<span class="span">收款状态：</span> <select name="feeStatus"
							id="feeStatus">
							<option value="">请选择</option>
							<option value="0"
								<c:if test="${model.feeStatus==0}">selected</c:if>>未收款</option>
							<option value="1"
								<c:if test="${model.feeStatus==1}">selected</c:if>>已收款</option>
						</select>
					</div>					
					<div class="div">
						<span class="span">托运方：</span><input type="text" id="senderId"
							name="senderId" value="${model.senderId}" />

					</div>
					<div class="div">
						<span class="span">收货方：</span><input class="input2" type="text"
							id="receiverId" name="receiverId" value="${model.receiverId}" />
					</div>
					<div class="div">
						<span class="span">客户单号：</span><input type="text"
							id="customerNumber" name="customerNumber"
							value="${model.customerNumber}" />
					</div>
					<div class="div">
						<span class="span">运单号：</span><input type="text" name="shipSn"
							id="shipSn" value="${model.shipSn}" />
					</div>
					<div class="div">
						<button id="search">查询</button>
					</div>
					<div class="div">
					<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
			</form>
		</div>

		<div class="banner-right-list2">
		<ul class="ul2">
				
				<li><a href="#" class="banner-right-a3" id="gun"
					onclick="collectionShipAll()">确认代收</a></li>
				<li><a href="#" class="banner-right-a3" id="gun2"
					onclick="receivablesShipAll()">确认收款</a></li>
				<li><a href="javascript:downExcel();" class="banner-right-a3"
					style="color: #3974f8; background: #fff; text-decoration: underline; border: none;">导出EXCEL</a>
				</li>
				<li>
					<div id="page" style="text-align: center;"></div>
				</li>
			</ul>
			<p class="banner-right-p">应收列表</p>
			<div style="overflow: auto; width: 100%;" id="loadingId">
				<table border="0" class="tab_css_1" id="loadId"
					style="border-collapse: collapse;">
					<thead>
						<th><label>全选</label><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
						<th>序号</th>
						<th>运单号</th>
						<th>开单网点</th>
						<th>收款状态</th>
						<th>付款方式</th>
						<th>合计</th>
						<th>代收网点</th>
						<th>是否已代收</th>
						<th>开单日期</th>
						<th>客户单号</th>
						<th>出发地</th>
						<th>到达地</th>
						<th>托运方</th>
						<th class="banner-right-padding">收货方</th>
						<th class="banner-right-th">操作</th>
					</thead>
					<%--       <c:forEach items="${page.list}" var="ship" varStatus="vs">
                        <tr class="tr_css" align="center">
                            <td><input type="checkbox" value="${ship.ship_id}" data="${ship.ship_total_fee}"/></td>
                            <td>${vs.index+1}</td>
                            <td><a href="#" class="btn" onclick="openDiv(${ship.ship_id})">${ship.ship_sn}</a></td>
                            <td>${ship.netName}</td>
                            <c:if test="${ship.ship_fee_status==1}">
                                <td><b>已收款</b></td>
                            </c:if>
                            <c:if test="${ship.ship_fee_status==0}">
                                <td><b>未收款</b></td>
                            </c:if>

                            <c:if test="${ship.ship_pay_way==1}">
                                <td><b>现付</b></td>
                            </c:if>
                            <c:if test="${ship.ship_pay_way==2}">
                                <td><b>提付</b></td>
                            </c:if>
                            <c:if test="${ship.ship_pay_way==3}">
                                <td><b>到付</b></td>
                            </c:if>
                            <c:if test="${ship.ship_pay_way==4}">
                                <td><b>回单付</b></td>
                            </c:if>
                            <c:if test="${ship.ship_pay_way==5}">
                                <td><b>月结</b></td>
                            </c:if>
                            <td><b>${ship.ship_total_fee}</b></td>
                            
                            <c:choose>
                            <c:when test="${ship.ship_pay_way==3 && (ship.network_id!=ship.load_network_id)}">
                                <td>${ship.endWorkName}</td>
                            </c:when>
                            <c:otherwise>
                                    <td>&nbsp;</td>
                            </c:otherwise>
                            </c:choose>
                            
                            
                            <c:choose>
                                <c:when test="${ship.ship_pay_way==3 && (ship.network_id!=ship.load_network_id)}">
                                    <c:if test="${ship.ship_agency_fund_status==0}">
                                        <Td>否</Td>
                                    </c:if>
                                    <c:if test="${ship.ship_agency_fund_status==1||ship.ship_agency_fund_status==2||ship.ship_agency_fund_status==3}">
                                        <td>是</td>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <td>&nbsp;</td>
                                </c:otherwise>
                            </c:choose>
                            
                            <td><fmt:formatDate value="${ship.create_time}" pattern="yyyy-MM-dd HH:mm"/></td>
                            <td>${ship.ship_customer_number}</td>
                            <td>${ship.fromAdd}</td>
                            <td>${ship.toAdd}</td>
                            <td>${ship.senderName}</td>
                            <td class="banner-right-padding">${ship.receiverName}</td>
                            <c:if test="${ship.ship_fee_status==0}">
                            <td class="banner-right-th" onclick="openUpdate(${ship.ship_id})"><img src="${ctx}/static/kd//img/r_icon1.png"/><span>修改</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach> --%>
				</table>
				
			</div>
			
			
		</div>

	</div>
	</div>

	</div>

	<%@ include file="../../common/loginfoot.jsp"%>
	<script>
	
		//分页
		layui
				.use(
						[ 'laypage' ],
						function() {
							var laypage = layui.laypage;
							//调用分页
							laypage({
								cont : 'page',
								pages : '${page.totalPage}' //得到总页数
								,
								curr : '${page.pageNumber}',
								skip : true,
								jump : function(obj, first) {
									if (!first) {
										window.location.href = "${ctx}/kd/finance/receivable?pageNo="
												+ obj.curr
												+ "&networkId="
												+ $("#networkId").val()
												+ "&startTime="
												+ $("#startTime").val()
												+ "&endTime="
												+ $("#endTime").val()
												+ "&feeStatus="
												+ $("#feeStatus").val()
												+ "&shipSn="
												+ $("#shipSn").val()
												+ "&senderId="
												+ $("#senderId").val()
												+ "&receiverId="
												+ $("#receiverId").val()
												+ "&formCode="
												+ $("#formCode").val()
												+ "&toCode="
												+ $("#toCode").val()
												+ "&customerNumber="
												+ $("#customerNumber").val();
									}
								},
								skin : '#1E9FFF'
							});
						});

		function selectAll() {
			if ($("#selectAll").is(':checked')) {
				$(".tab_css_1  input[type='checkbox']").prop("checked", true);
			} else {
				$(".tab_css_1 input[type='checkbox']").prop("checked", false)
			}
		}
		//代收
		function collectionShipAll() {
			var array = new Array();
			var feetotal = 0;
			$(".tr_css input[type='checkbox']").each(function(i) {

				if ($(this).prop("checked")) {
					feetotal += parseFloat(($(this).attr("data")));
					array.push($(this).val());
				}

			});
			collectionShip(array, feetotal);
		}
		function collectionShip(objs, feetotal) {
			if (objs == null || objs == "") {
				layer.msg("请选择要代收的运单");
				return;
			}
			layer
					.confirm(
							'共' + objs.length + '单，代收货款总计' + feetotal
									+ '元，确认已代收？',
							{
								title : '确认代收',
								btn : [ '确认', '取消' ]
							},
							function() {
								$
										.ajax({
											type : "post",
											dataType : "json",
											url : "${ctx }/kd/finance/receivable/updateAgencyState?shipId="
													+ objs,
											success : function(data) {
												if (data.success == true) {
													window.location.href = "${ctx}/kd/finance/receivable";
												} else {
													layer.msg(obj.msg);
												}
											}
										});
							}, function() {
							});
		}

		//收款
		function receivablesShipAll() {
			var array = new Array();
			var feetotal = 0;

			$(".tr_css input[type='checkbox']").each(function(i) {

				if ($(this).prop("checked")) {
					feetotal += parseFloat(($(this).attr("data")));
					array.push($(this).val());
				}

			});
			receivablesShip(array, feetotal);
		}
		function receivablesShip(objs, feetotal) {
			if (objs == null || objs == "") {
				layer.msg("请选择要收款的运单");
				return;
			}
			layer
					.confirm(
							'共' + objs.length + '单，应收金额总计' + feetotal
									+ '元，确认已收款？',
							{
								title : '确认收款',
								btn : [ '确认', '取消' ]
							},
							function() {
								$
										.ajax({
											type : "post",
											dataType : "json",
											url : "${ctx }/kd/finance/receivable/updateFeeStatus?shipId="
													+ objs,
											success : function(data) {
												if (data.success == true) {
													window.location.href = "${ctx}/kd/finance/receivable";
												} else {
													layer.msg(obj.msg);
												}
											}
										});
							}, function() {

							});
		}

		function openDiv(shipid) {
			//页面层
			layer.open({
				type : 2,
				area : [ '850px', '700px' ], //宽高
				content : [ '${ctx}/kd/waybill/viewDetail?shipId=' + shipid,
						'yes' ]
			});
		}

		function openUpdate(shipid) {
			//页面层
			layer.open({
				type : 2,
				area : [ '900px', '750px' ], //宽高
				content : [
						'${ctx}/kd/finance/receivable/findShip?shipId='
								+ shipid, 'yes' ],
				end : function() {
					location.reload();
				}
			});

		}

		function downExcel() {

			layer
					.confirm(
							'确定导出应收汇总表Excel吗',
							{
								btn : [ '确认', '取消' ]
							},
							function() {
								window.location.href = "${ctx}/kd/finance/receivable/downLoad?networkId="
										+ $("#networkId").val()
										+ "&startTime="
										+ $("#startTime").val()
										+ "&endTime="
										+ $("#endTime").val()
										+ "&feeStatus="
										+ $("#feeStatus").val()
										+ "&shipSn="
										+ $("#shipSn").val()
										+ "&senderId="
										+ $("#senderId").val()
										+ "&receiverId="
										+ $("#receiverId").val()
										+ "&formCode="
										+ $("#formCode").val()
										+ "&toCode="
										+ $("#toCode").val()
										+ "&customerNumber="
										+ $("#customerNumber").val();
							}, function() {
							});

		}
	</script>
</body>
</html>