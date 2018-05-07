<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>中转费结算</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/transsettlement.css" />
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
					<a href="${ctx}/kd/finance/payable" class="at">发车汇总</a>
				</li>
				<li>
					<a href="${ctx}/kd/finance/payable/toSum" class="at">到车汇总</a>
				</li>
				<li>
					<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=1" class="at">提货费</a>
				</li>
				<li>
					<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=4" class="at">送货费</a>
				</li>
				<li>
					<a href="${ctx}/kd/finance/payable/loadTransportPage?transportType=2" class="at">短驳费</a>
				</li>
				<li>
					<a href="#" class="actives at">中转费</a>
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
			<form onsubmit="return false;" id="confirmform">
			<div class="banner-right-list">
				<p class="banner-right-p">中转费</p>
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
				   <form id="searchFrom" onsubmit="return false;">
				  <div class="div">
					  <span class="span">中转方：</span>
					  <input id="transferName" name="transferName" value="${search.transferName }" type="text" />

				  </div>

				   <div class="div">
					   <span class="span">到达地：</span>
					   <div class="banner-right-list-liopd">

						   <div class="form-group">
							   <div style="position: relative;">
								   <input type="hidden" id="toAddCode" name="toAddCode">
								   <input id="city-picker2" class="form-control"
										  placeholder="请选择省/市/区" readonly type="text"
										  data-toggle="city-picker">
							   </div>
							   <script>
                                   $(function () {
                                       $(".city-picker-span").css("width", "auto");
                                   })
							   </script>
						   </div>
					   </div>
				   </div>
				   <div class="div">
					   <input type="button" class="button" onclick="search();" value="查询">
					   <input type="button" class="button" onclick="resetCity();" value="重置">
				   </div>
				   </form>
				  </div>
			   <div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
					<table border="1" class="tab_css_1" id="leftTable">
						<thead>
							<th>全选 <input type="checkbox" id="leftonAll" onclick="onallleft();"></th>
							<th>中转单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="lefttransferSn" onkeydown="enterFifterLeftTransferSn(event);" onkeyup="fifterLeftSn()"></div></th>
							<th>中转方</th>
							<th>中转日期</th>
							<th>中转费</th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="leftShipSn" onkeydown="enterFifterLeftSn(event);" onkeyup="fifterLeftSn()"></div></th>
							<th>提付</th>
							<th>代收货款</th>
							<th>到达地</th>
						</thead>
					</table>
				</div>
				<p class="p">合计<span id="leftDan"></span>单，中转费<span id="leftFee"></span>元</p>
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
							<th>中转单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="rightTransferSn" onkeydown="enterfifterRightTransferSn(event);" onkeyup="fifterRightSn()"></div></th>
							<th>中转方</th>
							<th>中转日期</th>
							<th>中转费</th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="rightSn" onkeydown="enterfifterRightSn(event);" onkeyup="fifterRightSn()"></div></th>
							<th>提付</th>
							<th>代收货款</th>
							<th>到达地</th>
						</thead>
					</table>
				</div>
				<p class="p">合计<span id="rightDan"></span>单，中转费<span id="rightFee"></span>元</p>
			</div>

		</div>
	</div>
	<script>
        function resetCity(){
            $("#city-picker2").citypicker("reset");
            $("#transferName").combogrid("clear")
        }
	</script>
	<%@ include file="../../common/loginfoot.jsp"%>
	<script src="${ctx}/static/kd/js/payable/trans-settlement.js"></script>
</body>

</html>