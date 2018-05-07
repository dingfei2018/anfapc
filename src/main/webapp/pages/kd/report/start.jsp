<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发车毛利表</title>
<link rel="stylesheet" href="${ctx}/static/kd/css/start.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
<link rel="stylesheet" href="${ctx}/static/pc/css/dropdown.css" />
<%@ include file="../common/commonhead.jsp"%>
	<script src="${ctx}/static/kd/js/report/start.js?v=${version}"></script>
</head>

<body>
	<!-- 头部文件 -->
	<%@ include file="../common/head2.jsp"%>

	<%@ include file="../common/head.jsp"%>

	<div class="banner">
		<!-- 左边菜单 -->
		<%@ include file="../common/reportleft.jsp"%>
		<script type="text/javascript">
			$(function() {
				var _width = $("body").width();
				var _widths = $(".banner-left").width();
				var _widthd = _width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
                resetDate();
			});
			$(window).resize(function() {
				var Width = $(window).width();
				var _widths = $(".banner-left").width();
				var _widthd = Width - _widths - 80;
				parseInt(_widthd)
				$('.banner-right').css('width', _widthd + 'px');
				console.log(_widthd)
			})
		</script>
		<div class="banner-right">

			<form id="searchForm" onsubmit="return false;">
				<div class="banner-right-list">
					<div class="div">
						<span class="span">配载网点：</span>
						<select name="netWorkId" id="networkId">
							<option value="">请选择</option>
							<c:forEach var="work" items="${netWorkList}">
								<option value="${work.id}">${work.sub_network_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div">
						<span class="span">开单日期：</span> <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" id="startTime" name="startTime" class="Wdate" style="margin-left:-3px;"/>
					</div>
					<div class="div">
						<span class="spanc">至</span> <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" id="endTime" name="endTime" class="Wdate" style="margin-left:30px;"/>
					</div>
					<div class="div">
						<span class="span">车牌号：</span><input type="text" id="truckIdNumber" name="truckIdNumber" />
					</div>
					<div class="div">
						<span class="span">配载单号：</span><input type="text" id="loadSn" name="loadSn" />
					</div>
					<div class="div">
						<button id="search">查询</button>
					    <input class="buttons" type="button" onclick="resetDate();" value="重置"/>
					</div>
					</div>
			</form>
		

		<div class="banner-right-list2">
		<ul class="ul2">
				<li style="border: 1px solid #3974f8;"><a id="excelExport" class="banner-right-a3"
					style="color: #3974f8; background: #fff; border: none;">导出EXCEL</a>
				</li>
			<li style="float: right;margin-right: 10px;">
				<div id="page" style="text-align: center;"></div>
			</li>
			</ul>
			<p class="banner-right-p">发车毛利表</p>
			<div style="overflow: auto; width: 100%;" id="loadingId">
				<table border="0" class="tab_css_1" id="table"
					style="border-collapse: collapse;">
					<thead>
						<th>序号</th>
						<th>配载单号</th>
						<th>配载网点</th>
						<th>到货网点</th>
						<th>发车日期</th>
						<th>车牌号</th>
						<th>司机</th>
						<th>司机电话</th>
						<th>营业额合计</th>
						<th>配载费合计</th>
						<th>发车毛利</th>
						<th>发车毛利率</th>
						<th>出发地</th>
						<th>到达地</th>
						<th>单数</th>
						<th>件数</th>
						<th>体积</th>
						<th>重量</th>
					</thead>
				</table>
			</div>
		</div>
	</div>
	</div>

	<script type="text/javascript">
        //重置
        function resetDate(){
            //前6个月
            var date1 = new Date();
            date1.setMonth(date1.getMonth()-6);
            var year1=date1.getFullYear();
            var month1=date1.getMonth()+1;
            month1 =(month1<10 ? "0"+month1:month1);
            sDate = (year1.toString()+'-'+month1.toString());
            $('#startTime').val(sDate);
            $('#endTime').val(new Date().format("yyyy-MM"));
            $('#networkId option:first').prop("selected", 'selected');
            $('#truckIdNumber').val('');
            $('#loadSn').val('');
        }
	</script>

	<%@ include file="../common/loginfoot.jsp"%>
	
</body>
</html>