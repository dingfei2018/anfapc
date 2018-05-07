<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>交账汇总</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/accounts.css" />
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
	<%@ include file="../common/head2.jsp"%>
	<%@ include file="../common/head.jsp"%>
	<div class="banner">
		<%@ include file="../common/fahuoleft.jsp"%>
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
		                <a href="/kd/waybill" class="activet at">运单列表</a>
		            </li>
					<li>
					<a href="/kd/waybill/updateChangeIndex"  class="at" >改单记录</a>
					</li>
					<li>
					<a href="/kd/waybill/deleteChangeIndex"  class="at" >删除记录</a>
					</li>
	       		 </ul> 
			<form onsubmit="return false;" id="confirmform">
			<div class="banner-right2">交账汇总</div>
				<div class="banner-right-list">
					<div class="div">
						<span class="span">开单网点：</span>
						<select name="netWorkId" id="netWorkId">
							<option value="">请选择 </option>
							<c:forEach var="work" items="${networks}">
								<option value="${work.id}">${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span" >开单日期：</span>
						<input type="text" class="Wdate" name="startTime" id="startTime" value="${time}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
					    <input type="checkbox" style="width:20px;height:26px;vertical-align:middle;  " id="flag">
						<span class="span" style="vertical-align:middle;width:140px;" >查询网点全部数据汇总</span>
					</div>
					<div class="div">
						 <button onclick="search();">查询</button>
                          <input class="buttons" type="reset" onclick="resetCity();" value="重置">
					</div>
				</div>
			</form>

			<div class="banner-right-list2">
			   <ul>
			     <li><a  class="ats" href="#">导出EXCEL</a></li>
			   </ul>
				<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
					<table border="1" class="tab_css_1" id="table">
						<thead>
						<th style="vertical-align:middle;width: 30px">全选 <input id="onall" onclick="onall();checkTotal();" type="checkbox" style="vertical-align:middle;"></th>
						<th style="width: 30px;">序号</th>
						<th style="width: 50px;">运单号</th>
						<th style="width: 50px;">开单网点</th>
						<th style="width: 120px;">开单日期</th>
						<th style="width: 50px;">托运方</th>
						<th style="width: 80px;">到达地</th>
						<th style="width: 50px;">现付</th>
						<th style="width: 50px;">提付</th>
						<th style="width: 50px;">回单付</th>
						<th style="width: 50px;">月结</th>
						<th style="width: 50px;">短欠</th>
						<th style="width: 50px;">贷款扣</th>
						<th style="width: 50px;">应收合计</th>
						<th style="width: 50px;">回扣</th>
						<th style="width: 50px;">回扣已付</th>
						<th style="width: 50px;">实收</th>
						<th style="width: 80px;">货号</th>
						<th style="width: 80px;">货物名称</th>
						<th style="width: 80px;">开单</th>
						</thead>
					</table>
				</div>
				<div style="width: 100%;height: 70px;float: left; margin-top: 30px; background-color: #d6def5;">
				   <table border="1" class="tab_css_1" id="leftTable">
						<tr>
							<td style="width: 30px;">已选</td>
							<td style="color: #ff7801;width: 30px;"><span id="checkDan"></span>单</td>
							<td style="color: #ff7801;width: 50px;"></td>
							<td style="color: #d6def5;width: 50px;"></td>
							<td style="color: #d6def5;width: 120px;"></td>
							<td style="color: #d6def5;width: 50px;"></td>
							<td style="color: #d6def5;width: 80px;"></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkNowpay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkPickuppay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkReceiptPay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkMonthPay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkArrearsPay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkGoodsPay"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkTotal"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkShipRebate"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkRebate"></span></td>
							<td style="color: #ff7801;width: 50px;"><span id="checkReceipts"></span></td>
							<td style="color: #d6def5;width: 80px;"></td>
							<td style="color: #d6def5;width: 80px;"></td>
							<td style="color: #d6def5;width: 80px;"></td>
						</tr>
						<tr style="background-color: #d6def5 !important;">
						   <td>合计</td>
							<td style="color: #ff7801;"><span id="dan"></span>单</td>
						   <td style="color: #ff7801;"></td>
						   <td style="color: #d6def5;"></td>
						   <td style="color: #d6def5;"></td>
						   <td style="color: #d6def5;"></td>
						   <td style="color: #d6def5;"></td>
							<td style="color: #ff7801;"><span id="nowpayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="pickuppayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="receiptPayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="monthPayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="arrearsPayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="goodsPayTotal"></span></td>
						   <td style="color: #ff7801;"><span id="totalTotal"></span></td>
						   <td style="color: #ff7801;"><span id="shipRebateTotal"></span></td>
						   <td style="color: #ff7801;"><span id="rebateTotal"></span></td>
						   <td style="color: #ff7801;"><span id="receiptsTotal"></span></td>
						   <td style="color: #d6def5;"></td>
						   <td style="color: #d6def5;"></td>
						   <td style="color: #d6def5;"></td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>
	<script src="${ctx}/static/kd/js/waybill/shipaccount.js"></script>
</body>
