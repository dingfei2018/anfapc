<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>未审核</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/examine.css" />
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
		<%@ include file="../../common/fahuoleft.jsp"%>
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
		                <a href="/kd/finance/examine" class="activet at">未审核</a>
		            </li>
		           <li>
		                <a href="/kd/finance/examine?types=1"  class="at" >已审核</a>
		            </li>
	       		 </ul> 
			<form id="searchFrom"  onsubmit="return false;" >
			<!-- <div class="banner-right2">交账汇总</div> -->
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
						<input type="text" class="Wdate" name="startTime" id="startTime" value="${startTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
					</div>
					<div class="div">
					   <span class="span" >至</span>
						<input type="text" class="Wdate"  id="endTime" name="endTime" value="${endTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',isShowClear:false,readOnly:true})"/>
						
					</div>
					<div class="div">
						<span class="span" >运单号：</span>
						<input type="text" id="shipSn" name="shipSn" />
					</div>
					<div class="div">
						 <button id="search">查询</button>
                          <input class="buttons" type="reset" onclick="resetCity();" value="重置">
					</div>
				</div>
			</form>

			<div class="banner-right-list2">
			   <ul>
				   <li><a  class="ats" href="javascript:void(0)" id="tongguo">审核通过</a></li>
			     <li><a  class="ats" href="javascript:void(0)" id="excelExport">导出EXCEL</a></li>
			   </ul>
				<div style="width: 100%;overflow-y:auto; overflow-x:auto; height:551px;" id="loadingId">
					<table border="1" class="tab_css_1" id="loadId">
						<thead>
						<th style="vertical-align:middle;width: 30px">全选 <input id="selectAll" onclick="checkTotal();" type="checkbox" style="vertical-align:middle;"></th>
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
						<th style="width: 80px;">开单人</th>
						</thead>
					</table>
				</div>
				<div style="width: 100%;height: 70px;float: left; margin-top: 30px; background-color: #d6def5;">
					<table border="1" class="tab_css_1" id="table">
						<tr>
							<td style="width: 30px;">已选</td>
							<td style="color: #ff7801;width: 30px;"><span id="checkDan"></span>单</td>
							<td style="color: #ff7801;width: 50px;"></td>
							<td style="color: #d6def5;width: 120px;"></td>
							<td style="color: #d6def5;width: 50px;"></td>
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
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #ff7801;"><span id="nowpayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="pickuppayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="receiptPayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="monthPayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="arrearsPayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="goodsPayTotal">1</span></td>
							<td style="color: #ff7801;"><span id="totalTotal">1</span></td>
							<td style="color: #ff7801;"><span id="shipRebateTotal">1</span></td>
							<td style="color: #ff7801;"><span id="rebateTotal">1</span></td>
							<td style="color: #ff7801;"><span id="receiptsTotal">1</span></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
							<td style="color: #d6def5;"></td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</div>
	<%@ include file="../../common/loginfoot.jsp"%>
	<script src="${ctx}/static/kd/js/examine/unaudited.js?v=${version}"></script>
<script>
	checkTotal();
    //全选事件
    function selectAll() {
        if ($("#selectAll").is(':checked')) {
            $("#loadId input[type='checkbox']").prop("checked", true);
        } else {
            $("#loadId input[type='checkbox']").prop("checked", false)
        }
    }
    function checkTotal(){
        selectAll();
        var tr=$('#loadId tr:gt(0)');
        var checkNowpay=0;
        var checkPickuppay=0;
        var checkReceiptPay=0;
        var checkMonthPay=0;
        var checkArrearsPay=0;
        var checkGoodsPay=0;
        var checkTotal=0;
        var checkShipRebate=0;
        var checkRebate=0;
        var checkReceipts=0;
        var count=0;
        $(tr).each(function(){
            if($(this).children('td').eq(0)[0].children[0].checked==true){
                count++;
                console.log(parseFloat($(this).children('td').eq(8)[0].innerText));
                checkNowpay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(7)[0].innerText))?0:$(this).children('td').eq(7)[0].innerText);
                checkPickuppay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(8)[0].innerText))?0:$(this).children('td').eq(8)[0].innerText);
                checkReceiptPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(9)[0].innerText))?0:$(this).children('td').eq(9)[0].innerText);
                checkMonthPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(10)[0].innerText))?0:$(this).children('td').eq(10)[0].innerText);
                checkArrearsPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(11)[0].innerText))?0:$(this).children('td').eq(11)[0].innerText);
                checkGoodsPay+=parseFloat(isNaN(parseFloat($(this).children('td').eq(12)[0].innerText))?0:$(this).children('td').eq(12)[0].innerText);
                checkTotal+=parseFloat(isNaN(parseFloat($(this).children('td').eq(13)[0].innerText))?0:$(this).children('td').eq(13)[0].innerText);
                checkShipRebate+=parseFloat(isNaN(parseFloat($(this).children('td').eq(14)[0].innerText))?0:$(this).children('td').eq(14)[0].innerText);
                checkRebate+=parseFloat(isNaN(parseFloat($(this).children('td').eq(15)[0].innerText))?0:$(this).children('td').eq(15)[0].innerText);
                checkReceipts+=parseFloat(isNaN(parseFloat($(this).children('td').eq(16)[0].innerText))?0:$(this).children('td').eq(16)[0].innerText);
            }else{
                $("#onall").prop("checked", false)
            }
        });
        $('#checkDan').html(count);
        $('#checkNowpay').html(checkNowpay);
        $('#checkPickuppay').html(checkPickuppay);
        $('#checkReceiptPay').html(checkReceiptPay);
        $('#checkMonthPay').html(checkMonthPay);
        $('#checkArrearsPay').html(checkArrearsPay);
        $('#checkGoodsPay').html(checkGoodsPay);
        $('#checkTotal').html(checkTotal);
        $('#checkShipRebate').html(checkShipRebate);
        $('#checkRebate').html(checkRebate);
        $('#checkReceipts').html(checkReceipts);

    }


</script>
</body>
