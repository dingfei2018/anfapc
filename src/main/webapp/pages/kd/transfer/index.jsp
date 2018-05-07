<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>建立中转</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/transfer.css" />
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
		<input type="hidden" value="${result.msg }" id="msg" />
		<div class="banner-right">
			
			<ul>
				<li><a href="#" class="activet at">建立中转</a></li>
				<li><a href="${ctx}/kd/transfer/transShipMent" class="at">已转运单</a>
				</li>
			</ul>
			<form onsubmit="return false;" id="transferForm">
			<div class="banner-right-list">
				<div class="div">
					<span class="span" id="y_transfer">中转方：</span>
					<input id="customerCrop" name="customer.customer_corp_name" type="text" />
					<input type="hidden" name="customer.customer_id" id="customerId" />
					<input type="hidden" name="customer.customer_address_id" id="customerAddressId" />
				</div>
				<div class="div">
					<span class="span">中转时间：</span>
					<input type="text" id="time" name="time" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" />
				</div>
				<div class="div">
					<span class="span">联系人：</span>
					<input type="text" id="customerName" name="customer.customer_name" />
				</div>
				<div class="div"> 
					<span class="span">联系电话：</span>
					<input type="text" name="customer.customer_mobile" id="customerMobile" />
				</div>
				<div class="div">
					<span class="span" style="width:120px;">中转费用合计：</span><input disabled="disabled" id="transferTotalFee" value="0" type="text"/>
				</div>
			</div>

			<div class="banner-right-list2">
			   <div class="banner-right-list2-left">
			   <div class="banner-right-list2-lefts" onclick="transferAll();">
			      <img src="${ctx}/static/kd/img/youce.png"/>
			   </div>
			  <div class="div">
					<span class="span">出发地：</span>
					<div class="banner-right-list-liopd">
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="startCode" /> <input id="city-picker2"
									class="form-control" placeholder="请选择省/市/区" readonly
									type="text" data-toggle="city-picker">
							</div>
						</div>
					</div>
				</div> 
				 <div class="div">
					<span class="span">到达地：</span>
					<div class="banner-right-list-liopc">
						<div class="form-group">
							<div style="position: relative;">
								<input type="hidden" id="endCode" /> <input id="city-picker3" class="form-control" placeholder="请选择省/市/区" readonlytype="text" data-toggle="city-picker">
							</div>
						</div>
					</div>
				</div>
				 <div class="div">
					<span class="span">托运方：</span> <input id="shipperName" type="text" />
				</div>
				<div class="div">
					<span class="span">收货方：</span> <input id="receivingName" type="text" />
				</div>
				<div class="div">
					<input type="button" class="button" onclick="search();" value="查询">
				</div>
				<div class="div">
				 <input type="button" class="button" onclick="resetCity();" value="重置">
				 </div>
				  </div>
			   <div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
					<table border="1" class="tab_css_1" id="waybillTable">
						<thead>
							<th>全选 <input type="checkbox" id="waybillonAll" onclick="shiponAll();"></th>
							<th>序号</th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="filterShipSn" onkeydown="enterFifterShipSn(event);" onkeyup="fifterShipSn()"></div></th>
							<th>到达地</th>
							<th>货物名称</th>
							<th>体积</th>
							<th>重量</th>
							<th>件数</th>
							<th>出发地</th>
							<th>开单网点</th>
						</thead>
					</table>
				</div>
				<p class="p">合计<span id="shipDan"></span>单，<span id="shipJian"></span>件，<span id="shipFang"></span>方，<span id="shipGong"></span>公斤</p>
			</div>
            <div class="banner-right-list3">
             <div class="banner-right-list3-left">
                <div class="banner-right-list3-lefts" onclick="delAll();">
                  <img src="${ctx}/static/kd/img/zuoce.png"/>
                </div>
                <input type="button" class="button" onclick="saveTransfer();" value="确认中转">
             </div>
                 <div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId2">
					<table border="1" class="tab_css_1" id="transferTable">
						<thead>
							<th>全选 <input type="checkbox" id="transferonAll" onclick="onAll();"></th>
							<th>序号</th>
							<th>运单号 <div><input type="text" style="width:120px;height:30px;margin-top:5px;" id="filterTranShipSn" onkeydown="enterfifterTranShipSn(event);" onkeyup="fifterTranShipSn();"></div></th>
							<th>中转单号</th>
							<th>中转费</th>
							<th>到达地</th>
							<th>货物名称</th>
							<th>体积</th>
							<th>重量</th>
							<th>件数</th>
							<th>出发地</th>
							<th>开单网点</th>
						</thead>
					</table>
				</div>
				<p class="p">合计<span id="transferDan"></span>单，<span id="transferJian"></span>件，<span id="transferFang"></span>方，<span id="transferGong"></span>公斤</p>
			</div>
			</form>
		</div>
	</div>
	<%@ include file="../common/loginfoot.jsp"%>
</body>
<script src="${ctx}/static/kd/js/transfer/transfer.js"></script>
</html>