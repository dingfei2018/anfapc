<%@ page contentType="text/html; charset=utf-8;" pageEncoding="utf-8"%>
<%@ include file="/resources/includes/tags.jsp"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>毛利汇总表</title>
		<link rel="stylesheet" href="${ctx}/static/kd/css/profit.css?v=${version}">
		<link rel="stylesheet" href="${ctx}/static/pc/css/head2.css" />
		<link rel="stylesheet" href="${ctx}/static/pc/css/footer.css" />
		<%@ include file="../common/commonhead.jsp" %>
		<script type="text/javascript" src="${ctx}/static/common/js/ichart.1.2.1.min.js" ></script>
		<script src="${ctx}/static/kd/js/profitall.js?v=${version}"></script>
	</head>
	<body>
	  
		<%@ include file="../common/head2.jsp" %>
		<%@ include file="../common/head.jsp" %>
		<div class="banner">
			<%@ include file="../common/financialleft.jsp" %>
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
						<a href="${ctx }/kd/report/reSummary" class="at">应收汇总表</a>
					</li>
					<li>
						<a href="${ctx }/kd/report/paySummary" class="at">应付汇总表</a>
					</li>
					<li>
						<a href="${ctx}/kd/report/operationList" class="at">运作明细表</a>
					</li>
					<li>
						<a href="${ctx }/kd/report/preProfit" class="active at">毛利汇总表</a>
					</li>
				</ul>
				<div class="banner-right-list">
				   <ul>
					<form onsubmit="return false;"  id="searchFrom">
						<li>
						<span class="span">网点：</span>
						<select name="networkId" id="networkId">
							<option value="0">请选择网点</option>
							<c:forEach items="${networks}" var="net">
								<option value="${net.id}" ${networkId==net.id?"selected='selected'":""}>${net.sub_network_name}</option>
							</c:forEach>
						</select>
						</li>
						<li>
						<span class="spanc">月份：</span><input type="text" name="startTime" id="startTime" value="${startTime}"  onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM'})"/>
						</li>
						<li>
						<span class="spanc">至</span><input type="text" name="endTime"  id="endTime" value="${endTime}"   onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM'})"/>
						</li>
						<li>
						<button id="search">查询</button>
						</li>
						<li>
						<button id="search">重置</button>
						</li>
					</ul>
					</form>
				</div>
				
				<div style=" float:left; width:99%; height: 400px; border: 1px solid #DCDCDC; margin: auto; margin-top: 40px;">
					<div id='canvasDiv'></div>
				</div>

				<div class="banner-right-list2">
					<p class="banner-right-p">毛利汇总表</p>
					<div style="overflow: auto; width: 100%;">
						<table border="0" class="tab_css_1" style="border-collapse:collapse;" id="loadId">
							<thead>
							<tr>	
								<th rowspan="2">时间</th>
								<th rowspan="2">运单量</th>
								<th rowspan="2">营业额</th>
								<th colspan="6">成本</th>
								
								<th rowspan="2">毛利</th>
								<th rowspan="2">毛利率</th>
							</tr>
							<tr>	
								
								<th>提货费</th>
								<th>短驳费</th>
								<th>干线费</th>
								<th>送货费</th>
								<th>中转费</th>
								<th>合计</th>
							</tr>
							</thead>
							
						</table>
					</div>
					<ul class="ul2">
						<li>
							<a href="javascript:void(0);" class="banner-right-a3" style="color:#3974f8; background: #fff;text-decoration:underline;border: none;">导出EXCEL</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<%@ include file="../common/loginfoot.jsp" %>
	</body>
</html>
