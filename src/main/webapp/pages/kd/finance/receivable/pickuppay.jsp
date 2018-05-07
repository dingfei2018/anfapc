<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>现付</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/payment.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
<%@ include file="../../common/commonhead.jsp"%>
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
			})
		</script>
		<div class="banner-right">
			<ul>
				<li><a href="/kd/finance/receivable" class="at">全部</a></li>
				<li><a href="/kd/finance/receivable/nowpayIndex" class="at">现付</a>
				<li><a href="/kd/finance/receivable/pickupPayIndex" class="activet at">提付</a>
				<li><a href="/kd/finance/receivable/receiptPayIndex" class="at">回单付</a>
				<li><a href="/kd/finance/receivable/monthPayIndex" class="at">月结</a></li>
				<li><a href="/kd/finance/receivable/arrearsPayIndex" class="at">短欠</a></li>
				<li><a href="/kd/finance/receivable/goodsPayIndex" class="at">贷款扣</a></li>
			</ul>
			<form id="searchForm" onsubmit="return false;">
				<div class="banner-right-list">
					<div class="div">
						<span class="span">到货网点：</span>
						<select name="networkId">
							<option value="">请选择</option>
							<c:forEach var="work" items="${userNetworks}">
								<option value="${work.id}">${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">开单网点：</span>
						<select name="networkId">
							<option value="">请选择</option>
							<c:forEach var="work" items="${userNetworks}">
								<option value="${work.id}">${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">开单日期：</span> <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" name="startTime" class="Wdate" style="margin-left:-3px;"/>
					</div>
					<div class="div">
						<span class="spanc">至</span> <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" name="endTime" class="Wdate" style="margin-left:30px;"/>
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
						<span class="span" style="text-align: center;vertical-align: middle;width:60px; margin-left: 20px;">提付结算状态：</span>
						<select name="feeStatus">
							<option value="0">请选择</option>
							<option value="1">未结算</option>
							<option value="2" >已结算</option>
						</select>
					</div>
					<div class="div">
						<span class="span">托运方：</span><input id="shipperName" name="senderId" type="text"/>
					</div>
					<div class="div">
						<span class="span">收货方：</span><input id="receivingName" name="receiverId" class="input2" type="text"/>
					</div>

					<div class="div">
						<span class="span">运单号：</span><input name="shipSn" type="text"/>
					</div>
					<div class="div">
						<button id="search">查询</button>
						<input class="buttons" type="reset" onclick="resetCity();" value="重置"/>
					</div>
			</form>
		</div>

		<div class="banner-right-list2">
		<ul class="ul2">
			<li style="border: 1px solid #3974f8;"><a class="banner-right-a3" style="color: #3974f8; background: #fff; border: none;"  onclick="window.location.href='/kd/finance/receivable/goPickupPayJS'">结算</a></li>
				<li style="border: 1px solid #3974f8;"><a id="excelExport" class="banner-right-a3"
					style="color: #3974f8; background: #fff; border: none;">导出EXCEL</a>
				</li>
				<li><div id="page" style="text-align: center;"></div></li>
			</ul>
			<p class="banner-right-p">提付列表</p>
			<div style="overflow: auto; width: 100%;" id="loadingId">
				<table border="0" class="tab_css_1" id="table"
					style="border-collapse: collapse;">
					<thead>
						<th>序号</th>
						<th>运单号</th>
						<th>到货网点</th>
						<th>开单网点</th>
						<th>货号</th>
						<th>开单日期</th>
						<th>出发地</th>
						<th>到达地</th>
						<th>托运方</th>
						<th>收货方</th>
						<th>运单状态</th>
						<th>提付</th>
						<th>结算状态</th>
						<th>客户单号</th>
						<th>货物名称</th>
						<th>体积</th>
						<th>重量</th>
						<th>件数</th>
					</thead>
				</table>
			</div>
		</div>
	</div>
	</div>
	</div>
	<%@ include file="../../common/loginfoot.jsp"%>
	<script src="${ctx}/static/kd/js/receivable/re_pickuppay.js"></script>
	<script>
        $('#shipperName').combogrid({
            url : '/kd/customer/searchCustomer?type=2',
            idField : 'customer_id',
            textField : 'customer_name',
            value:'${search.shipperName}',
            height : 30,
            panelWidth : 320,
            pagination: true,
            columns: [[
                {field:'customer_corp_name',title:'公司名',width:200},
                {field:'customer_name',title:'姓名',width:150},
                {field:'customer_mobile',title:'电话',width:200}
            ]],
            keyHandler:{
                up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                    //动态搜索
                    $('#shipperName').combogrid("grid").datagrid("reload", {'queryName': q});
                    $('#shipperName').combogrid("setValue", q);
                }
            },
            fitColumns: true
        });
        //收货方信息
        $('#receivingName').combogrid({
            url : '/kd/customer/searchCustomer?type=1',
            idField : 'customer_id',
            textField : 'customer_name',
            value:'${search.receivingName}',
            height : 30,
            panelWidth : 320,
            pagination: true,
            columns: [[
                {field:'customer_corp_name',title:'公司名',width:200},
                {field:'customer_name',title:'姓名',width:150},
                {field:'customer_mobile',title:'电话',width:200}
            ]],
            keyHandler:{
                up: function() {},
                down: function() {},
                enter: function() {},
                query: function(q) {
                    //动态搜索
                    $('#receivingName').combogrid("grid").datagrid("reload", {'queryName': q});
                    $('#receivingName').combogrid("setValue", q);
                }
            },
            fitColumns: true
        });
	</script>
</body>
</html>