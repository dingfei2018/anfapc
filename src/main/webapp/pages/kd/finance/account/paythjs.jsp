<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>提付对账</title>
	<link rel="stylesheet" href="${ctx}/static/kd/css/payoperation.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
	<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
	<link href="${ctx}/static/pc/study/css/city-picker.css" rel="stylesheet">
	<link href="${ctx}/static/pc/study/css/main.css" rel="stylesheet">
	<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/demo/demo.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/kd/js/easyui/themes/default/easyui.css" />
	<script src="${ctx}/static/kd/js/easyui/jquery.min.js"></script>
	<script src="${ctx}/static/kd/js/easyui/jquery.easyui.min.js"></script>
	<script src="${ctx}/static/pc/study/js/city-picker.data.js"></script>
	<script src="${ctx}/static/pc/study/js/city-picker.js"></script>
	<script src="${ctx}/static/component/My97DatePicker/WdatePicker.js"></script>
	<script src="${ctx}/static/pc/js/common.js?v=${version}"></script>
	<script src="${ctx}/static/pc/layer/layer/layer.js"></script>
	<script src="${ctx}/static/pc/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctx}/static/pc/layui/css/layui.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/pc/src2/src/jquery.mloading.css"/>
	<script src="${ctx}/static/pc/src2/src/jquery.mloading.js"></script>
</head>
<body>
<%@ include file="../../common/head2.jsp"%>
<%@ include file="../../common/head.jsp"%>
<div class="banner">
	<%@ include file="../../common/financialleft.jsp" %>
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

		<ul>
			<li>
				<a href="${ctx }/kd/finance/account" class="at">提付应收</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/reDetailedIndex" class="at">操作应收</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/payIndex" class="activet at">提付应付</a>
			</li>
			<li>
				<a href="${ctx }/kd/finance/account/payDetailedIndex" class="at">操作应付</a>
			</li>
		</ul>
		<form onsubmit="return false;" id="confirmform">
			<div class="banner-right-list">
				<p class="banner-right-p">提付对账</p>
				<div class="div">
					<span class="span">收支方式：</span>
					<select id="payType" name="payType">
						<option value="0">请选择</option>
						<option value="1">现金</option>
						<option value="2">油卡</option>
						<option value="3">支票</option>
						<option value="4">银行卡</option>
						<option value="5">微信</option>
						<option value="6">支付宝</option>
					</select>
				</div>
				<div class="div">
					<span class="span" >收支账号：</span>
					<input type="text" name="flowNo" />
				</div>
				<div class="div">
					<span class="span" >交易凭证号：</span>
					<input type="text" name="voucherNo" />
				</div>
				<div class="div">
					<span class="span">备注：</span>
					<input type="text" name="remark"  />
				</div>

			</div>
		</form>

		<div class="banner-right-list2">
			<div class="banner-right-list2-left">
				<div class="banner-right-list2-lefts" onclick="confirmAll();">
					<img src="${ctx}/static/kd/img/youce.png"/>
				</div>
				<div class="div">
					<span class="span">到货网点：</span>
					<select id="reNetWorkId">
						<option value="">请选择 </option>
						<c:forEach var="work" items="${userNetworks}">
							<option value="${work.id}">${work.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="div">
					<span class="span">开单网点：</span>
					<select id="payNetWorkId">
						<option value="">请选择 </option>
						<c:forEach var="work" items="${comNetworks}">
							<option value="${work.id}">${work.sub_network_name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="div">
					<span class="span">收货方：</span> <input id="receivingName" type="text" />
				</div>
				<div class="div">
					<input type="button" class="button" onclick="search();" value="查询">
					<%--<input type="button" class="button" onclick="resetCity();" value="重置">--%>
				</div>

			</div>
			<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
				<table border="1" class="tab_css_1" id="leftTable">
					<thead>
					<th>全选 <input type="checkbox" id="leftonAll" onclick="onallleft();"></th>
					<th>序号</th>
					<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="leftShipSn" onkeydown="enterFifterLeftSn(event);" onkeyup="fifterLeftSn()"></div></th>
					<th>到货网点</th>
					<th>货号</th>
					<th>开单日期</th>
					<th>提付</th>
					<th>异动增加</th>
					<th>异动减款</th>
					<th>运单状态</th>
					<th>开单网点</th>
					<th>收货方</th>
					</thead>
				</table>
			</div>
			<p class="p">合计<span id="leftDan"></span>单，提付<span id="leftFee"></span>元,异动增加<span id="leftPlus"></span>元,异动减款<span id="leftMinus"></span>元,异动合计<span id="leftTotal"></span>元</p>
		</div>
		<div class="banner-right-list3">
			<div class="banner-right-list3-left">
				<div class="banner-right-list3-lefts" onclick="delAll();">
					<img src="${ctx}/static/kd/img/zuoce.png"/>
				</div>
				<input type="button" class="button" onclick="confirm();" value="确认结算">
			</div>
			<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId2">
				<table border="1" class="tab_css_1" id="rightTable">
					<thead>
					<th>全选 <input type="checkbox" id="rightonAll" onclick="onallright();"></th>
					<th>序号</th>
					<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="rightSn" onkeydown="enterfifterRightSn(event);" onkeyup="fifterRightSn()"></div></th>
					<th>开单网点</th>
					<th>货号</th>
					<th>开单日期</th>
					<th>提付</th>
					<th>异动增加</th>
					<th>异动减款</th>
					<th>运单状态</th>
					<th>到货网点</th>
					<th>收货方</th>
					</thead>
				</table>
			</div>
			<p class="p">合计<span id="rightDan"></span>单，提付<span id="rightFee"></span>元,异动增加<span id="rightPlus"></span>元,异动减款<span id="rightMinus"></span>元,异动合计<span id="rightTotal"></span>元</p>
		</div>

	</div>
</div>
<%@ include file="../../common/loginfoot.jsp"%>
</body>
<script src="${ctx}/static/kd/js/account/paythjs.js"></script>
</html>