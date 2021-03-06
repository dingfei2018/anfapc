<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>送货费</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/handle.css?v=${version}">
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
	<%@ include file="../../common/commonhead.jsp" %>
	<script src="${ctx}/static/kd/js/payable/transportTypeList.js?v=${version}"></script>
</head>
<body>
<%@ include file="../../common/head2.jsp" %>

<%@ include file="../../common/head.jsp" %>
<div class="banner">
	<%@ include file="../../common/financialleft.jsp" %>
	<script type="text/javascript">
        $(function(){
            var _width=$("body").width();
            var _widths = $(".banner-left").width();
            var _widthd = _width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width',_widthd+'px');
        });
        $(window).resize(function(){
            var Width = $(window).width();
            var _widths = $(".banner-left").width();
            var _widthd = Width - _widths - 80;
            parseInt(_widthd)
            $('.banner-right').css('width',_widthd+'px');
        })
	</script>
	<div class="banner-right">
		<ul>
			<li>
				<a href="${ctx}/kd/finance/payable" class="at">发车汇总</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/toSum" class="at">到车汇总</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=1" class="at">提货费</a>
			</li>
			<li>
				<a href="#" class="actives at">送货费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=2" class="at">短驳费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/transfer" class="at">中转费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/rebate" class="at">回扣</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=1" class="at">现付运输费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=2" class="at">现付油卡费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=3" class="at">回付运输费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=4" class="at">整车保险费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=5" class="at">发站装车费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=6" class="at">发站其他费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=3&trunkLineType=7" class="at">到付运输费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?loadAtFeeFlag=0" class="at">到站卸车费</a>
			</li>
			<li>
				<a href="${ctx}/kd/finance/payable/loadTransportPage?loadAtFeeFlag=1" class="at">到站其他费</a>
			</li>
		</ul>
		<div class="banner-right-list">
			<form id="searchFrom" onsubmit="return false;">
				<div class="div">
					<span class="span">配载网点：</span>
					<select name="networkId">
						<option value="0">请选择网点</option>
						<c:forEach items="${networks}" var="net">
							<option value="${net.id}">${net.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="div">
					<span class="span">发车日期：</span><input type="text" name="startTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
				<div class="div">
					<span class="span"> 至 </span><input type="text" name="endTime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</div>
				<div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopc">

						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" name="deliveryFrom">
								<input id="city-picker3" class="form-control"
									   placeholder="请选择省/市/区" readonly type="text"
									   data-toggle="city-picker">
							</div>
							<script>
                                $(function(){
                                    $(".city-picker-span").css("width","auto");
                                })
							</script>
						</div>
					</div>
				</div>
				<div class="div">
					<span class="span">到达地：</span>
					<div class="banner-right-list-liopd">

						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" name="deliveryTo">
								<input id="city-picker2" class="form-control"
									   placeholder="请选择省/市/区" readonly type="text"
									   data-toggle="city-picker">
							</div>
							<script>
                                $(function(){
                                    $(".city-picker-span").css("width","auto");
                                })
							</script>
						</div>
					</div>
				</div>
				<div class="div">
					<span class="span">车牌号：</span><input type="text" name="truckNumber"/>
				</div>
				<div class="div">
					<span class="span">司机：</span><input  type="text" name="driverName"/>
				</div>
				<div class="div">
					<span class="span">配载单号：</span><input  type="text" name="loadSn"/>
				</div>
				<div class="div">
					<span class="span">结算状态：</span>
					<select name="fillStatus">
						<option value="">请选择</option>
						<option value="0">未结算</option>
						<option value="1">已结算</option>
					</select>
				</div>


				<div class="div">
					<button id="search">查询</button>
					<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
				</div>
			</form>
		</div>

		<div class="banner-right-list2">
			<ul class="ul2">
				<li>
					<a class="banner-right-a3" onclick="window.location.href='/kd/finance/payable/settlement?transportType=4'">结算</a>
				</li>
				<li>
					<a href="#" class="banner-right-a3" id="excelExport" transportType="4" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
				</li>
				<li>
					<div id="page" style="text-align: center;"></div>
				</li>
			</ul>
			<p class="banner-right-p">送货费列表</p>
			<div style="overflow: auto; width: 100%;  " id="loadingId">
				<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
					<thead>
					<th>序号</th>
					<th>配载单号</th>
					<th>配载网点</th>
					<th>发车日期</th>
					<th>车牌号</th>
					<th>司机</th>
					<th>司机电话</th>
					<th>出发地</th>
					<th>到达地</th>
					<th>配载状态</th>
					<th>结算状态</th>
					<th>送货费</th>
					<th>未结送货费</th>
					<th>送货运单数</th>
					<th>送货体积</th>
					<th>送货重量</th>
					<th>送货件数</th>
					</thead>
				</table>


			</div>

		</div>

	</div>
</div>

<%@ include file="../../common/loginfoot.jsp" %>
</body>
</html>
